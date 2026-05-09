package view;

import database.dao.DirecaoDAO;
import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_DIRECAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaReplicacaoDirecaoView extends JFrame {

    private enum modoTela {NENHUM, INSERT, UPDATE}

    private modoTela modoTelaAtual = modoTela.NENHUM;

    private final Connection conn;
    private final DirecaoDAO dao;

    private JTextField txfId;
    private JTextField txfDirecaoOrigem;
    private JTextField txfDirecaoDestino;
    private JTextField txfUsuarioOrigem;
    private JTextField txfUsuarioDestino;
    private JTextField txfSenhaOrigem;
    private JTextField txfSenhaDestino;
    private JCheckBox chkHabilitado;
    private JComboBox<TB_REPLICACAO_PROCESSO> cbProcesso;

    private JButton btnSalvar;
    private JButton btnAdicionar;
    private JButton btnBuscar;
    private JButton btnExcluir;

    public TelaReplicacaoDirecaoView(Connection conn) throws SQLException {

        this.conn = conn;
        this.dao = new DirecaoDAO(conn);

        setTitle("Cadastro de Tabela");
        setSize(760, 480);
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
        lblProcesso.setBounds(10, 105, 140, 25);;
        getContentPane().add(lblProcesso);

        cbProcesso = new JComboBox<>();
        cbProcesso.setBounds(160, 105, 520, 25);
        getContentPane().add(cbProcesso);

        JLabel lblOrigem = new JLabel("ORIGEM:");
        lblOrigem.setBounds(10, 150, 140, 25);
        lblOrigem.setFont(lblOrigem.getFont().deriveFont(Font.BOLD));
        getContentPane().add(lblOrigem);

        JLabel lblDirecaoOrigem = new JLabel("DIREÇÃO ORIGEM:");
        lblDirecaoOrigem.setBounds(10, 180, 140, 25);
        getContentPane().add(lblDirecaoOrigem);

        txfDirecaoOrigem = new JTextField();
        txfDirecaoOrigem.setBounds(160, 180, 560, 25);
        getContentPane().add(txfDirecaoOrigem);

        JLabel lblUsuarioOrigem = new JLabel("USUARIO ORIGEM:");
        lblUsuarioOrigem.setBounds(10, 220, 140, 25);
        getContentPane().add(lblUsuarioOrigem);

        txfUsuarioOrigem = new JTextField();
        txfUsuarioOrigem.setBounds(160, 220, 560, 25);
        getContentPane().add(txfUsuarioOrigem);

        JLabel lblSenhaOrigem = new JLabel("SENHA ORIGEM:");
        lblSenhaOrigem.setBounds(10, 255, 140, 25);
        getContentPane().add(lblSenhaOrigem);

        txfSenhaOrigem = new JTextField();
        txfSenhaOrigem.setBounds(160, 255, 560, 25);
        getContentPane().add(txfSenhaOrigem);

        JLabel lblDestino = new JLabel("DESTINO:");
        lblDestino.setBounds(10, 300, 200, 25);
        lblDestino.setFont(lblDestino.getFont().deriveFont(Font.BOLD));
        getContentPane().add(lblDestino);

        JLabel lblDirecaoDestino = new JLabel("DIREÇÃO DESTINO:");
        lblDirecaoDestino.setBounds(10, 330, 200, 25);
        getContentPane().add(lblDirecaoDestino);

        txfDirecaoDestino = new JTextField();
        txfDirecaoDestino.setBounds(160, 330, 560, 25);
        getContentPane().add(txfDirecaoDestino);

        JLabel lblUsuarioDestino = new JLabel("USUARIO DESTINO:");
        lblUsuarioDestino.setBounds(10, 370, 140, 25);
        getContentPane().add(lblUsuarioDestino);

        txfUsuarioDestino = new JTextField();
        txfUsuarioDestino.setBounds(160, 370, 280, 25);
        getContentPane().add(txfUsuarioDestino);

        JLabel lblSenhaDestino = new JLabel("SENHA DESTINO:");
        lblSenhaDestino.setBounds(450, 370, 120, 25);
        getContentPane().add(lblSenhaDestino);

        txfSenhaDestino = new JTextField();
        txfSenhaDestino.setBounds(570, 370, 150, 25);
        getContentPane().add(txfSenhaDestino);

        chkHabilitado = new JCheckBox("HABILITADO");
        chkHabilitado.setBounds(10, 405, 140, 25);
        getContentPane().add(chkHabilitado);

        txfId.setEnabled(false);
        txfDirecaoOrigem.setEnabled(false);
        txfDirecaoDestino.setEnabled(false);
        txfUsuarioOrigem.setEnabled(false);
        txfUsuarioDestino.setEnabled(false);
        txfSenhaOrigem.setEnabled(false);
        txfSenhaDestino.setEnabled(false);
        chkHabilitado.setEnabled(false);
        cbProcesso.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);


        btnAdicionar.addActionListener(e -> {
            modoTelaAtual = modoTela.INSERT;

            txfId.setText("");

            if(cbProcesso.getItemCount() > 0) {
                cbProcesso.setSelectedIndex(0);
            }
            chkHabilitado.setSelected(true);

            txfDirecaoOrigem.setText("");
            txfUsuarioOrigem.setText("");
            txfSenhaOrigem.setText("");

            txfDirecaoDestino.setText("");
            txfUsuarioDestino.setText("");
            txfSenhaDestino.setText("");

            cbProcesso.setEnabled(true);
            chkHabilitado.setEnabled(true);
            btnSalvar.setEnabled(true);

            txfDirecaoOrigem.setEnabled(true);
            txfUsuarioOrigem.setEnabled(true);
            txfSenhaOrigem.setEnabled(true);

            txfDirecaoDestino.setEnabled(true);
            txfUsuarioDestino.setEnabled(true);
            txfSenhaDestino.setEnabled(true);

            btnExcluir.setEnabled(true);
            btnSalvar.setEnabled(true);

        });

        btnSalvar.addActionListener(e -> {
            try{

                if (cbProcesso.getSelectedItem() == null ){
                    JOptionPane.showMessageDialog(this, "Informe o PROCESSO.");
                    return;
                }

                if (txfDirecaoOrigem.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a DIREÇÃO ORIGEM.");
                    return;
                }

                if (txfDirecaoDestino.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a DIREÇÃO DESTINO.");
                    return;
                }

                if (txfSenhaOrigem.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a SENHA DE ORIGEM.");
                    return;
                }

                if (txfSenhaDestino.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe a SENHA DE DESTINO.");
                    return;
                }

                if (txfUsuarioOrigem.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe o USUARIO DE ORIGEM.");
                    return;
                }

                if (txfUsuarioDestino.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Informe o USUARIO DE DESTINO.");
                    return;
                }


                TB_REPLICACAO_PROCESSO pSel = (TB_REPLICACAO_PROCESSO) cbProcesso.getSelectedItem();

                TB_REPLICACAO_DIRECAO d = new TB_REPLICACAO_DIRECAO();
                d.setProcesso_id(pSel.getId());
                d.setHabilitado(chkHabilitado.isSelected());
                d.setDirecao_origem(txfDirecaoOrigem.getText().trim());
                d.setUsuario_origem(txfUsuarioOrigem.getText().trim());
                d.setSenha_origem(txfSenhaOrigem.getText().trim());
                d.setDirecao_destino(txfDirecaoDestino.getText().trim());
                d.setUsuario_destino(txfUsuarioDestino.getText().trim());
                d.setSenha_destino(txfSenhaDestino.getText().trim());

                if (modoTelaAtual == modoTela.INSERT) {
                    dao.insert(d);
                    JOptionPane.showMessageDialog(this, "Direcao cadastrada!");
                }else if (modoTelaAtual == modoTela.UPDATE) {
                    if(txfId.getText().trim().isEmpty()){
                        JOptionPane.showMessageDialog(this, "ID não carregado para atualização.");
                        return;
                    }
                    d.setId(Long.parseLong(txfId.getText()));
                    dao.update(d);
                    JOptionPane.showMessageDialog(this, "Direcao atualizada!");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

                modoTelaAtual = modoTela.NENHUM;
                txfId.setEnabled(false);
                txfDirecaoOrigem.setEnabled(false);
                txfDirecaoDestino.setEnabled(false);
                txfUsuarioOrigem.setEnabled(false);
                txfUsuarioDestino.setEnabled(false);
                txfSenhaOrigem.setEnabled(false);
                txfSenhaDestino.setEnabled(false);
                chkHabilitado.setEnabled(false);
                cbProcesso.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);

            }catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try{
                if(txfId.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(this, "ID não carregado para exclusão.");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Deseja Realmente excluir o registro ?", "Excluir", JOptionPane.YES_NO_OPTION);

                if (op != JOptionPane.YES_OPTION) return;

                Long id = Long.parseLong(txfId.getText());
                dao.delete(id);
                JOptionPane.showMessageDialog(this, "Direção excluída!");

                modoTelaAtual = modoTela.NENHUM;
                txfId.setText("");

                if(cbProcesso.getItemCount() > 0) {
                    cbProcesso.setSelectedIndex(0);
                }
                chkHabilitado.setSelected(false);

                txfDirecaoOrigem.setText("");
                txfUsuarioOrigem.setText("");
                txfSenhaOrigem.setText("");

                txfDirecaoDestino.setText("");
                txfUsuarioDestino.setText("");
                txfSenhaDestino.setText("");

                cbProcesso.setEnabled(false);
                chkHabilitado.setEnabled(false);
                btnSalvar.setEnabled(false);

                txfDirecaoOrigem.setEnabled(false);
                txfUsuarioOrigem.setEnabled(false);
                txfSenhaOrigem.setEnabled(false);

                txfDirecaoDestino.setEnabled(false);
                txfUsuarioDestino.setEnabled(false);
                txfSenhaDestino.setEnabled(false);

                btnExcluir.setEnabled(false);
                btnSalvar.setEnabled(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(b -> {
            try{

                ConsultaReplicacaoDirecaoDialog dlg = new ConsultaReplicacaoDirecaoDialog(this, dao);
                dlg.setVisible(true);

                TB_REPLICACAO_DIRECAO sel = dlg.getSelecionado();

                if (sel == null) return;

                modoTelaAtual = modoTela.UPDATE;

                txfId.setText(sel.getId().toString());
                chkHabilitado.setSelected(sel.getHabilitado());

                txfDirecaoOrigem.setText(sel.getDirecao_origem());
                txfUsuarioOrigem.setText(sel.getUsuario_origem());
                txfSenhaOrigem.setText(sel.getSenha_origem());

                txfDirecaoDestino.setText(sel.getDirecao_destino());
                txfUsuarioDestino.setText(sel.getUsuario_destino());
                txfSenhaDestino.setText(sel.getSenha_destino());

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
                txfDirecaoOrigem.setEnabled(true);
                txfUsuarioOrigem.setEnabled(true);
                txfSenhaOrigem.setEnabled(true);
                txfDirecaoDestino.setEnabled(true);
                txfUsuarioDestino.setEnabled(true);
                txfSenhaDestino.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnExcluir.setEnabled(true);


            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao Consultar: " + e.getMessage());
            }
        });

    }
}
