package view;

import database.dao.DirecaoDAO;
import database.model.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaReplicacaoDirecaoDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnCancelar;

    private TB_REPLICACAO_DIRECAO selecionado;

    public ConsultaReplicacaoDirecaoDialog(JFrame parent, DirecaoDAO dao) throws SQLException {
        super(parent, "Consulta de Tabelas");
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("DIREÇÃO_ORIGEM");
        model.addColumn("DIREÇÃO_DESTINO");
        model.addColumn("USUARIO_ORIGEM");
        model.addColumn("USUARIO_DESTINO");
        model.addColumn("SENHA_ORIGEM");
        model.addColumn("SENHA_DESTINO");
        model.addColumn("PROCESSO_ID");
        model.addColumn("HABILITADO");

        ArrayList<TB_REPLICACAO_DIRECAO> lista = dao.selectAll();

        for (TB_REPLICACAO_DIRECAO p : lista) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getDirecao_origem(),
                    p.getDirecao_destino(),
                    p.getUsuario_origem(),
                    p.getUsuario_destino(),
                    p.getSenha_origem(),
                    p.getSenha_destino(),
                    p.getProcesso_id(),
                    p.getHabilitado()
            });
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 680, 300);
        add(scrollPane);

        btnSelecionar = new JButton("SELECIONAR");
        btnSelecionar.setBounds(10, 320, 140, 30);
        add(btnSelecionar);

        btnCancelar = new JButton("CANCELAR");
        btnCancelar.setBounds(170, 320, 140, 30);
        add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            selecionado = null;
            dispose();
        });

        btnSelecionar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um registro");
                return;
            }

            TB_REPLICACAO_DIRECAO p = new TB_REPLICACAO_DIRECAO();
            p.setId(Long.parseLong(table.getValueAt(row, 0).toString()));
            p.setDirecao_origem(table.getValueAt(row, 1).toString());
            p.setDirecao_destino(table.getValueAt(row, 2).toString());
            p.setUsuario_origem(table.getValueAt(row, 3).toString());
            p.setUsuario_destino(table.getValueAt(row, 4).toString());
            p.setSenha_origem(table.getValueAt(row, 5).toString());
            p.setSenha_destino(table.getValueAt(row, 6).toString());
            p.setProcesso_id(Long.parseLong(table.getValueAt(row, 7).toString()));
            p.setHabilitado(Boolean.parseBoolean(table.getValueAt(row, 8).toString()));
            selecionado = p;
            dispose();
        });

        table.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    btnSelecionar.doClick();
                }
            }
        });
    }

    public TB_REPLICACAO_DIRECAO getSelecionado() {
        return selecionado;
    }

}
