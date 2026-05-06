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
        super(parent, "Consulta de Tabelas");
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(null);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("PROCESSO_ID");
        model.addColumn("TABELA ORIGEM");
        model.addColumn("TABELA DESTINO");
        model.addColumn("ORDEM");
        model.addColumn("HABILITADO");
        model.addColumn("WHERE");

        ArrayList<TB_REPLICACAO_PROCESSO_TABELA> lista = dao.selectAll();

        for (TB_REPLICACAO_PROCESSO_TABELA p : lista) {
            model.addRow(new Object[]{
                    p.getId(),
                    p.getProcesso_id(),
                    p.getTabela_origem(),
                    p.getTabela_destino(),
                    p.getOrdem(), p.isHabilitado(),
                    p.getDs_where()});
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

            TB_REPLICACAO_PROCESSO_TABELA p = new TB_REPLICACAO_PROCESSO_TABELA();
            p.setId(Long.parseLong(table.getValueAt(row, 0).toString()));
            p.setProcesso_id(Long.parseLong(table.getValueAt(row, 1).toString()));
            p.setTabela_origem(table.getValueAt(row, 2).toString());
            p.setTabela_destino(table.getValueAt(row, 3).toString());
            p.setOrdem(Integer.parseInt(table.getValueAt(row, 4).toString()));
            p.setHabilitado(Boolean.parseBoolean(table.getValueAt(row, 5).toString()));
            p.setDs_where(table.getValueAt(row, 6).toString());
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
