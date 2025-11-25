package Views;

import auth.AuthenticationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtSenha;
    private JButton btnLogin;
    private JButton btnCadastrar;
    private JLabel lblStatus;

    public LoginFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sistema - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Login do Sistema", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // Painel de formulário
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Painel de status
        lblStatus = new JLabel(" ", JLabel.CENTER);
        lblStatus.setForeground(Color.RED);
        mainPanel.add(lblStatus, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Credenciais"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        JLabel lblUsername = new JLabel("Usuário:");
        txtUsername = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblUsername, gbc);

        gbc.gridx = 1;
        panel.add(txtUsername, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(15);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblSenha, gbc);

        gbc.gridx = 1;
        panel.add(txtSenha, gbc);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnLogin = new JButton("Entrar");
        btnCadastrar = new JButton("Cadastrar");

        // Estilizar botões
        btnLogin.setBackground(new Color(34, 139, 34));
        btnLogin.setForeground(Color.WHITE);
        btnCadastrar.setBackground(new Color(70, 130, 180));
        btnCadastrar.setForeground(Color.WHITE);

        // Ações dos botões
        btnLogin.addActionListener(this::realizarLogin);
        btnCadastrar.addActionListener(e -> abrirCadastro());

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCadastrar);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        // Enter para login
        getRootPane().setDefaultButton(btnLogin);

        return panel;
    }

    private void realizarLogin(ActionEvent e) {
        String username = txtUsername.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (username.isEmpty() || senha.isEmpty()) {
            lblStatus.setText("Preencha todos os campos!");
            return;
        }

        AuthenticationService auth = AuthenticationService.getInstance();
        if (auth.login(username, senha)) {
            lblStatus.setText("Login realizado com sucesso!");

            // Fechar login e abrir home
            SwingUtilities.invokeLater(() -> {
                this.dispose();
                new HomeFrame().setVisible(true);
            });
        } else {
            lblStatus.setText("Usuário ou senha inválidos!");
            txtSenha.setText("");
        }
    }

    private void abrirCadastro() {
        // Abrir tela de cadastro
        SwingUtilities.invokeLater(() -> {
            new CadastroFrame(this).setVisible(true);
        });
    }

    public void limparCampos() {
        txtUsername.setText("");
        txtSenha.setText("");
        lblStatus.setText(" ");
    }
}