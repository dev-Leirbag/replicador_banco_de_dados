import view.TelaReplicacaoDirecaoView;
import view.TelaReplicacaoProcessoTabelaView;
import view.TelaReplicacaoProcessoView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JFrame {

    private JDesktopPane desktop;
    private static Connection conn;

    public Main() {
        setTitle("Sistema de Replicação de Dados");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        desktop = new JDesktopPane();
        setContentPane(desktop);

        JMenu menuSistema = new JMenu("Sistema");

        JMenuItem itemExecutar = new JMenuItem("Executar Replicação");
        itemExecutar.addActionListener(e -> {});
        menuSistema.add(itemExecutar);

        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.exit(0);
        });
        menuSistema.add(itemSair);

        JMenu menuCadastro = new JMenu("Cadastro");

        JMenuItem itemProcesso = new JMenuItem("Processo");
        itemProcesso.addActionListener(e -> {
            abrirTelaInternaProcessos();
        });
        menuCadastro.add(itemProcesso);

        JMenuItem itemProcessoTabela = new JMenuItem("Processo X Tabelas");
        itemProcessoTabela.addActionListener(e -> {
            abrirInteraProcessoTabela();
        });
        menuCadastro.add(itemProcessoTabela);

        JMenuItem itemDirecao = new JMenuItem("Direções");
        itemDirecao.addActionListener(e -> {
            abrirTelaInternaDirecoes();
        });
        menuCadastro.add(itemDirecao);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuSistema);
        menuBar.add(menuCadastro);
        setJMenuBar(menuBar);
    }

    private void abrirTelaInternaProcessos(){
        try {
            TelaReplicacaoProcessoView tela = new TelaReplicacaoProcessoView(conn);

            JInternalFrame internalFrame = new JInternalFrame("Processos", true, true, true, true);
            internalFrame.setSize(650, 360);
            internalFrame.setLayout(new BorderLayout());
            internalFrame.add(tela.getContentPane(), BorderLayout.CENTER);
            internalFrame.setVisible(true);
            desktop.add(internalFrame);
            internalFrame.setSelected(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir a tela: " + e.getMessage());
        }
    }

    private void abrirTelaInternaDirecoes(){
        try{
            TelaReplicacaoDirecaoView tela = new TelaReplicacaoDirecaoView(conn);

            JInternalFrame internalFrame = new JInternalFrame("Cadastro de Direções", true, true, true, true);
            internalFrame.setSize(820, 520);
            internalFrame.setLayout(new BorderLayout());
            internalFrame.add(tela.getContentPane(), BorderLayout.CENTER);
            internalFrame.setVisible(true);
            desktop.add(internalFrame);
            internalFrame.setSelected(true);

        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir a tela: " + ex.getMessage());
        }
    }

    private void abrirInteraProcessoTabela(){
        try{
            TelaReplicacaoProcessoTabelaView tela = new TelaReplicacaoProcessoTabelaView(conn);

            JInternalFrame internalFrame = new JInternalFrame("Cadastro de Processos x Tabelas", true, true, true, true);
            internalFrame.setSize(720, 500);
            internalFrame.setLayout(new BorderLayout());
            internalFrame.add(tela.getContentPane(), BorderLayout.CENTER);
            internalFrame.setVisible(true);
            desktop.add(internalFrame);
            internalFrame.setSelected(true);

        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao abrir a tela: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {

        try{
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/controle", "postgres", "admin000");
           SwingUtilities.invokeLater(() -> new Main().setVisible(true));
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar: " + e.getMessage());
            System.exit(0);
        }


    }
}