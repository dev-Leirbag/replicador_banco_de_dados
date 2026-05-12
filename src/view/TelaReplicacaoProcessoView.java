package view;

import database.dao.ReplicacaoProcessoDAO;
import database.model.TB_REPLICACAO_PROCESSO;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaReplicacaoProcessoView extends JFrame {

    private enum modoTela {NENHUM, INSERT, UPDATE}

    private modoTela modoTelaAtual = modoTela.NENHUM;

    private final Connection conn;
    private final ReplicacaoProcessoDAO dao;

    private JTextField txfId;
    private JTextField txfProcesso;
    private JTextField txfDescricao;
    private JCheckBox chkHabilitado;

    private JButton btnSalvar;
    private JButton btnAdicionar;
    private JButton btnBuscar;
    private JButton btnExcluir;

    public TelaReplicacaoProcessoView(Connection conn) throws SQLException {

        this.conn = conn;
        this.dao = new ReplicacaoProcessoDAO(conn);


        setTitle("Cadastro de Processos");
        setSize(620, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        btnBuscar = new JButton("BUSCAR");
        btnAdicionar = new JButton("ADICIONAR");
        btnSalvar = new JButton("SALVAR");
        btnExcluir = new JButton("EXCLUIR");

        btnBuscar.setBounds(10, 10, 130, 30);
        //? Calculo para posicionar os botões = width + x + folga = 130 + 10 + 10 = 150
        btnAdicionar.setBounds(150, 10, 130, 30);
        btnSalvar.setBounds(290, 10, 130, 30);
        btnExcluir.setBounds(430, 10, 130, 30);

        getContentPane().add(btnBuscar);
        getContentPane().add(btnAdicionar);
        getContentPane().add(btnSalvar);
        getContentPane().add(btnExcluir);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(10, 70, 120, 25);
        getContentPane().add(lblId);

        txfId = new JTextField();
        txfId.setBounds(140, 70, 200, 25);
        getContentPane().add(txfId);

        JLabel lblProcesso = new JLabel("PROCESSO:");
        lblProcesso.setBounds(10, 105, 120, 25);
        ;
        getContentPane().add(lblProcesso);

        txfProcesso = new JTextField();
        txfProcesso.setBounds(140, 105, 420, 25);
        getContentPane().add(txfProcesso);

        JLabel lblDescricao = new JLabel("DESCRIÇÃO:");
        lblDescricao.setBounds(10, 140, 120, 25);
        getContentPane().add(lblDescricao);

        txfDescricao = new JTextField();
        txfDescricao.setBounds(140, 140, 420, 25);
        getContentPane().add(txfDescricao);

        chkHabilitado = new JCheckBox("HABILITADO");
        chkHabilitado.setBounds(10, 175, 120, 25);
        ;
        getContentPane().add(chkHabilitado);

        txfId.setEnabled(false);
        txfProcesso.setEnabled(false);
        txfDescricao.setEnabled(false);
        chkHabilitado.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnExcluir.setEnabled(false);

        btnAdicionar.addActionListener(e -> {
            modoTelaAtual = modoTela.INSERT;

            txfId.setText("");
            txfProcesso.setText("");
            txfDescricao.setText("");
            chkHabilitado.setSelected(true);

            txfId.setEnabled(false);
            txfProcesso.setEnabled(true);
            txfDescricao.setEnabled(true);
            chkHabilitado.setEnabled(true);
            btnSalvar.setEnabled(true);
            btnExcluir.setEnabled(true);
        });

        btnSalvar.addActionListener(e -> {
            try {
                if (txfProcesso.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o PROCESSO!");
                    return;
                }

                TB_REPLICACAO_PROCESSO p = new TB_REPLICACAO_PROCESSO();
                p.setProcesso(txfProcesso.getText().trim());
                p.setDescricao(txfDescricao.getText().trim());
                p.setHabilitado(chkHabilitado.isSelected());

                if (modoTelaAtual == modoTela.INSERT) {
                    dao.insert(p);
                    JOptionPane.showMessageDialog(this, "Processo cadastrado!");
                } else if (modoTelaAtual == modoTela.UPDATE) {
                    if (txfId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Informe o ID para atualizar!");
                        return;
                    }
                    p.setId(Long.parseLong(txfId.getText()));
                    dao.update(p);
                    JOptionPane.showMessageDialog(this, "Processo atualizado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Clique em ADICIONAR ou BUSCAR antes de salvar.");
                    return;
                }

                modoTelaAtual = modoTela.NENHUM;
                txfProcesso.setEnabled(false);
                txfDescricao.setEnabled(false);
                chkHabilitado.setEnabled(false);
                btnSalvar.setEnabled(false);

                txfId.setText("");
                txfProcesso.setText("");
                txfDescricao.setText("");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                if (txfProcesso.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o PROCESSO!");
                    return;
                }

                int op = JOptionPane.showConfirmDialog(this, "Deseja Realmente excluir o registro ?", "Excluir", JOptionPane.YES_NO_OPTION);

                if (op != JOptionPane.YES_OPTION) return;

                Long id = Long.parseLong(txfId.getText());
                dao.delete(id);
                JOptionPane.showMessageDialog(this, "Processo excluído!");

                modoTelaAtual = modoTela.NENHUM;
                txfId.setText("");
                txfProcesso.setText("");
                txfDescricao.setText("");
                chkHabilitado.setSelected(true);
                txfProcesso.setEnabled(false);
                txfDescricao.setEnabled(false);
                chkHabilitado.setEnabled(false);
                btnSalvar.setEnabled(false);
                btnExcluir.setEnabled(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(b -> {

            try{
                ConsultaProcessoDialog dlg = new ConsultaProcessoDialog(this, dao);
                dlg.setVisible(true);

                TB_REPLICACAO_PROCESSO selecionado = dlg.getSelecionado();
                if (selecionado == null) return;

                modoTelaAtual = modoTela.UPDATE;

                txfId.setText(String.valueOf(selecionado.getId()));
                txfId.setEnabled(false);
                txfProcesso.setText(selecionado.getProcesso());
                txfDescricao.setText(selecionado.getDescricao());
                chkHabilitado.setSelected(selecionado.getHabilitado());

                txfProcesso.setEnabled(true);
                txfDescricao.setEnabled(true);
                chkHabilitado.setEnabled(true);
                btnSalvar.setEnabled(true);
                btnExcluir.setEnabled(true);

            }catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao buscar: " + ex.getMessage());
            }
        });

    }
}
