package view;

import database.model.TB_REPLICACAO_DIRECAO;

import javax.swing.*;
import java.awt.*;

public class TelaReplicacaoDirecaoView extends JFrame {

    private JTextField txfId;
    private JTextField txfDirecaoOrigem;
    private JTextField txfDirecaoDestino;
    private JTextField txfUsuarioOrigem;
    private JTextField txfUsuarioDestino;
    private JTextField txfSenhaOrigem;
    private JTextField txfSenhaDestino;
    private JCheckBox chkHabilitado;
    private JComboBox<TB_REPLICACAO_DIRECAO> cbProcesso;

    private JButton btnSalvar;
    private JButton btnAdicionar;
    private JButton btnBuscar;
    private JButton btnExcluir;

    public TelaReplicacaoDirecaoView() {
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {TelaReplicacaoDirecaoView tela = new TelaReplicacaoDirecaoView();tela.setVisible(true);});
    }

}
