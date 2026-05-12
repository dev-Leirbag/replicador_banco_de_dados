package view;

import database.dao.ProcessoTabelaDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_PROCESSO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TelaReplicacaoProcessoTabelaView extends JFrame {

    private enum modoTela {NENHUM, INSERT, UPDATE}

    private modoTela modoTelaAtual = modoTela.NENHUM;

    private final Connection conn;
    private final ProcessoTabelaDAO dao;
    private final ReplicacaoProcessoDAO daoProcesso;

    private JTextField txfId;
    private JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;
    private JTextField txfTabelaOrigem;
    private JTextField txfTabelaDestino;
    private JTextField txfOrdem;
    private JCheckBox chkHabilitado;
    private JTextArea txtWhere;

    private JButton btnSalvar;
    private JButton btnAdicionar;
    private JButton btnBuscar;
    private JButton btnExcluir;

    public TelaReplicacaoProcessoTabelaView(Connection conn) throws SQLException {

        this.conn = conn;
        this.dao = new ProcessoTabelaDAO(conn);
        this.daoProcesso = new ReplicacaoProcessoDAO(conn);

        setTitle("Cadastro de Tabelas");
        setSize(720, 420);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        btnBuscar = new JButton("BUSCAR");
        btnAdicionar = new JButton("ADICIONAR");
        btnSalvar = new JButton("SALVAR");
        btnExcluir = new JButton("EXCLUIR");

        btnBuscar.setBounds(10, 10, 130, 30);
        btnAdicionar.setBounds(150, 10, 130, 30);
        btnSalvar.setBounds(290, 10, 130, 30);
        btnExcluir.setBounds(430, 10, 130, 30);

        getContentPane().add(btnBuscar);
        getContentPane().add(btnAdicionar);
        getContentPane().add(btnSalvar);
        getContentPane().add(btnExcluir);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 140, 25);
        getContentPane().add(lblId);

        txfId = new JTextField();
        txfId.setBounds(160, 70, 220, 25);
        getContentPane().add(txfId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 140, 25);
        ;
        getContentPane().add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(160, 105, 520, 25);
        getContentPane().add(cbProcesso);

        JLabel lblTabelaOrigem = new JLabel("TABELA ORIGEM:");
        lblTabelaOrigem.setBounds(10, 140, 140, 25);
        getContentPane().add(lblTabelaOrigem);

        txfTabelaOrigem = new JTextField();
        txfTabelaOrigem.setBounds(160, 140, 520, 25);
        getContentPane().add(txfTabelaOrigem);

        JLabel lblTabelaDestino = new JLabel("TABELA DESTINO:");
        lblTabelaDestino.setBounds(10, 175, 140, 25);
        getContentPane().add(lblTabelaDestino);

        txfTabelaDestino = new JTextField();
        txfTabelaDestino.setBounds(160, 175, 520, 25);
        getContentPane().add(txfTabelaDestino);

        JLabel lblOrdem = new JLabel("ORDEM:");
        lblOrdem.setBounds(10, 210, 140, 25);
        getContentPane().add(lblOrdem);

        txfOrdem = new JTextField();
        txfOrdem.setBounds(160, 210, 220, 25);
        getContentPane().add(txfOrdem);

        chkHabilitado = new JCheckBox("HABILITADO");
        chkHabilitado.setBounds(10, 245, 140, 25);
        ;
        getContentPane().add(chkHabilitado);

        JLabel lblWhere = new JLabel("WHERE:");
        lblWhere.setBounds(10, 270, 140, 25);
        getContentPane().add(lblWhere);

        txtWhere = new JTextArea();
        txtWhere.setBounds(160, 280, 520, 80);
        getContentPane().add(txtWhere);


        cbProcesso.removeAllItems();
        ArrayList<TB_REPLICACAO_PROCESSO> processos = daoProcesso.selectAll();
        for (TB_REPLICACAO_PROCESSO p : processos){
            cbProcesso.addItem(p);
        }

        txfId.setEnabled(false);
        cbProcesso.setEnabled(false);
        txfTabelaOrigem.setEnabled(false);
        txfTabelaDestino.setEnabled(false);
        txfOrdem.setEnabled(false);
        chkHabilitado.setEnabled(false);
        txtWhere.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        btnAdicionar.addActionListener(e -> {
            modoTelaAtual = modoTela.INSERT;

            txfId.setText("");

            if (cbProcesso.getItemCount() > 0) {
                cbProcesso.setSelectedIndex(0);
            }

            chkHabilitado.setSelected(true);
            txfTabelaOrigem.setText("");
            txfTabelaDestino.setText("");
            txfOrdem.setText("");
            txtWhere.setText("");

            cbProcesso.setEnabled(true);
            chkHabilitado.setEnabled(true);
            txfTabelaOrigem.setEnabled(true);
            txfTabelaDestino.setEnabled(true);
            txfOrdem.setEnabled(true);
            txtWhere.setEnabled(true);
            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(true);
        });

        btnSalvar.addActionListener(e -> {
            try {

                if (cbProcesso.getSelectedItem() == null){
                    JOptionPane.showMessageDialog(this, "Informe o PROCESSO.");
                    return;
                }

                if (txfTabelaOrigem.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a TABELA ORIGEM.");
                    return;
                }
                 if (txfTabelaDestino.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a TABELA DESTINO.");
                    return;
                }

                if (txfOrdem.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a ORDEM.");
                    return;
                }

                int ordem;
                try {
                    ordem = Integer.parseInt(txfOrdem.getText().trim());
                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ORDEM deve ser numero.");
                    return;
                }

                TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();

                TB_REPLICACAO_PROCESSO_TABELA p = new TB_REPLICACAO_PROCESSO_TABELA();
                p.setProcesso_id(pSel.getId());
                p.setTabela_origem(txfTabelaOrigem.getText().trim());
                p.setTabela_destino(txfTabelaDestino.getText().trim());
                p.setOrdem(Integer.parseInt(txfOrdem.getText().trim()));
                p.setHabilitado(chkHabilitado.isSelected());
                p.setDs_where(txtWhere.getText().trim());

                if (modoTelaAtual == modoTela.INSERT) {
                    dao.insert(p);
                    JOptionPane.showMessageDialog(this, "Inserido com sucesso!");
                }else if (modoTelaAtual == modoTela.UPDATE) {
                    if (txfId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Informe o ID para atualizar!");
                        return;
                    }
                    p.setId(Long.parseLong(txfId.getText()));
                    dao.update(p);
                    JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
                }else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

                modoTelaAtual = modoTela.NENHUM;
                txfId.setEnabled(false);
                cbProcesso.setEnabled(false);
                txfTabelaOrigem.setEnabled(false);
                txfTabelaDestino.setEnabled(false);
                txfOrdem.setEnabled(false);
                chkHabilitado.setEnabled(false);
                txtWhere.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);

            } catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try{
                if (txfId.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o ID para excluir!");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o processo?", "Excluir", JOptionPane.YES_NO_OPTION);
                if (op != JOptionPane.YES_OPTION) return;

                Long id = Long.parseLong(txfId.getText());
                dao.delete(id);
                JOptionPane.showMessageDialog(this, "Excluido com sucesso!");

                modoTelaAtual = modoTela.NENHUM;
                txfId.setText("");

                if (cbProcesso.getItemCount() > 0) {
                    cbProcesso.setSelectedIndex(0);
                }
                chkHabilitado.setSelected(false);
                txfTabelaOrigem.setText("");
                txfTabelaDestino.setText("");
                txfOrdem.setText("");
                txtWhere.setText("");

                cbProcesso.setEnabled(false);
                chkHabilitado.setEnabled(false);
                txfTabelaOrigem.setEnabled(false);
                txfTabelaDestino.setEnabled(false);
                txfOrdem.setEnabled(false);
                txtWhere.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);
            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(b -> {
           try{

               ConsultaProcessoTabelaDialog dlg = new ConsultaProcessoTabelaDialog(this, dao);
               dlg.setVisible(true);

               TB_REPLICACAO_PROCESSO_TABELA sel = dlg.getSelecionado();

               if (sel == null) return;

               modoTelaAtual = modoTela.UPDATE;

               txfId.setText(sel.getId().toString());
               chkHabilitado.setSelected(sel.isHabilitado());

               txfTabelaOrigem.setText(sel.getTabela_origem());
               txfTabelaDestino.setText(sel.getTabela_destino());
               txfOrdem.setText(sel.getOrdem().toString());
               txtWhere.setText(sel.getDs_where());

               long id = sel.getProcesso_id();
               for(int i = 0; i < cbProcesso.getItemCount(); i++){
                   TB_REPLICACAO_PROCESSO item = cbProcesso.getItemAt(i);
                   if(item.getId() == id){
                       cbProcesso.setSelectedIndex(i);
                       break;
                   }
               }

               cbProcesso.setEnabled(true);
               chkHabilitado.setEnabled(true);
               txfTabelaOrigem.setEnabled(true);
               txfTabelaDestino.setEnabled(true);
               txfOrdem.setEnabled(true);
               txtWhere.setEnabled(true);
               btnSalvar.setEnabled(true);
               btnExcluir.setEnabled(true);



           }catch (Exception ex){
               ex.printStackTrace();
               JOptionPane.showMessageDialog(null, "Erro ao Consultar: " + ex.getMessage());
           }
        });

    }

}
