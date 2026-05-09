package view;

import database.dao.ProcessoTabelaDAO;
import database.model.TB_REPLICACAO_PROCESSO_TABELA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConsultaProcessoTabelaDialog extends JDialog {

    private JTable table;
    private JButton btnSelecionar;
    private JButton btnCancelar;

    private TB_REPLICACAO_PROCESSO_TABELA selecionado;

    public ConsultaProcessoTabelaDialog(JFrame parent, ProcessoTabelaDAO dao) throws SQLException {
        super(parent, "Consulta - Processo Tabela", true);
        setSize(1000, 400);
        setLocationRelativeTo(parent);
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO_ID");
        model.addColumn("TABELA ORIGEM");
        model.addColumn("TABELA DESTINO");
        model.addColumn("ORDEM");
        model.addColumn("HABILITADO");

        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = dao.selectAll();

        for (TB_REPLICACAO_PROCESSO_TABELA p : lista) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getProcesso_id(),
                    p.getTabela_origem(),
                    p.getTabela_destino(),
                    p.getOrdem(), p.isHabilitado()});
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 10, 960, 300);
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

            Long id = Long.parseLong(table.getValueAt(row, 0).toString());
            TB_REPLICACAO_PROCESSO_TABELA p = null;

            try {
                p = dao.selectById(id);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

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

    public TB_REPLICACAO_PROCESSO_TABELA getSelecionado() {
        return selecionado;
    }

}
