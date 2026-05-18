package service;

import database.dao.DirecaoDAO;
import database.dao.OrigemDAO;
import database.dao.ProcessoTabelaDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_DIRECAO;
import database.model.TB_REPLICACAO_PROCESSO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import java.sql.*;
import java.util.ArrayList;

public class ReplicacaoExecutar {

    private Connection connControle;
    private Connection connOrigem;
    private Connection connDestino;

    public ReplicacaoExecutar(Connection connControle) {
        this.connControle = connControle;
        System.out.println("Replicacao Iniciada. Acompanhe pelo console ou log");
        ReplicacaoIniciar();
        System.out.println("Replicacao Finalizada. Aguardando inicio da proxima replicacao");
    }


    private void ReplicacaoIniciar() {

        ReplicacaoProcessoDAO processo = null;

        try {
            processo = new ReplicacaoProcessoDAO(connControle);

            ArrayList<TB_REPLICACAO_PROCESSO> arlProcesso = processo.selectAll();

            if (arlProcesso != null && !arlProcesso.isEmpty()) {
                for (TB_REPLICACAO_PROCESSO p : arlProcesso) {
                    if (p != null && p.getHabilitado()) {

                        DirecaoDAO direcaoDAO = new DirecaoDAO(connControle);
                        ArrayList<TB_REPLICACAO_DIRECAO> arlDirecao = direcaoDAO.selectByProcessoHabilitado(p.getId());

                        for (TB_REPLICACAO_DIRECAO d : arlDirecao) {
                            if (d == null || !d.getHabilitado()) {
                                System.out.println("Nenhuma direção habilitada para replicar...");
                                continue;
                            }

                            // Tentar conectar na origem
                            try {
                                connOrigem = DriverManager.getConnection(d.getDirecao_origem(), d.getUsuario_origem(), d.getSenha_origem());
                            } catch (SQLException ex) {
                                System.out.println("Falha ao conectar no banco origem: " + ex.getMessage());
                                // não prossegue para esta direção
                                continue;
                            }

                            if (connOrigem == null) {
                                System.out.println("Falha ao conectar no banco origem: conexão nula");
                                continue;
                            }

                            // Tentar conectar no destino
                            try {
                                connDestino = DriverManager.getConnection(d.getDirecao_destino(), d.getUsuario_destino(), d.getSenha_destino());
                            } catch (SQLException ex) {
                                System.out.println("Falha ao conectar no banco destino: " + ex.getMessage());
                                // fechar origem se foi aberta
                                try { if (connOrigem != null && !connOrigem.isClosed()) connOrigem.close(); } catch (SQLException ignore) {}
                                connOrigem = null;
                                continue;
                            }

                            if (connDestino == null) {
                                System.out.println("Falha ao conectar no banco destino: conexão nula");
                                try { if (connOrigem != null && !connOrigem.isClosed()) connOrigem.close(); } catch (SQLException ignore) {}
                                connOrigem = null;
                                continue;
                            }

                            ProcessoTabelaDAO tabela = new ProcessoTabelaDAO(connControle);
                            OrigemDAO daoOrigem = new OrigemDAO(connOrigem);

                            ArrayList<TB_REPLICACAO_PROCESSO_TABELA> arlTabelas = tabela.selectByProcessoHabilitado(p.getId());
                            for (TB_REPLICACAO_PROCESSO_TABELA t : arlTabelas) {
                                if(t == null || !t.isHabilitado()){
                                    System.out.println("Nenhuma tabela habilitada para replicar...");
                                    continue;
                                }

                                System.out.println("Origem: " + d.getDirecao_origem()+ " <--> " + d.getDirecao_destino() + " - Tabela: " + t.getTabela_origem());
                                ResultSet resultado = daoOrigem.selectComandoOrigem(t.getTabela_origem(), t.getDs_where());
                                if(resultado != null) {
                                    ResultSetMetaData metaData = resultado.getMetaData();
                                    int ln_columns = metaData.getColumnCount();
                                    String insertSql = insertGet(t.getTabela_destino(), metaData);

                                    connDestino.setAutoCommit(false);
                                    try(PreparedStatement pstInsert = connDestino.prepareStatement(insertSql)){
                                        while(resultado.next()){
                                            for(int i = 1; i <= ln_columns; i++){
                                                pstInsert.setObject(i, resultado.getObject(i));
                                            }
                                            pstInsert.addBatch();
                                        }
                                        pstInsert.executeBatch();
                                        System.out.println("Dados replicados com sucesso!");
                                        connDestino.commit();
                                    }catch (Exception e){
                                        System.out.println("DEU PRIMARY KEY. IGNORANDO!!!");
                                    }
                                    finally {
                                        connDestino.setAutoCommit(true);
                                        resultado.close();
                                    }

                                }
                            }

                            // Fechar conexões de origem/destino após processar esta direção
                            try { if (connOrigem != null && !connOrigem.isClosed()) connOrigem.close(); } catch (SQLException ignore) {}
                            try { if (connDestino != null && !connDestino.isClosed()) connDestino.close(); } catch (SQLException ignore) {}
                            connOrigem = null;
                            connDestino = null;

                        }

                    } else {
                        System.out.println("Nenhum processo habilitado");
                    }
                }

            } else {
                System.out.println("Nenhum processo encontrado...");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String insertGet(String tabelaDestino, ResultSetMetaData metaData) throws SQLException {

        String ls_column = "", ls_value = "";

        for(int ln_1 = 0; ln_1 < metaData.getColumnCount(); ls_column += metaData.getColumnName(++ln_1)+ " ,"
        +"\n", ls_value += " ? ," + "\n");

        return "INSERT INTO " + tabelaDestino + " (" + ls_column.substring(0, ls_column.length() - 2) + ") VALUES (" + ls_value.substring(0, ls_value.length() - 2) + ");";

    }

}
