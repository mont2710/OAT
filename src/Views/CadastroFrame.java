package Views;

import auth.AuthenticationService;
import models.Usuario;
import javax.swing.*;
import java.awt.*;

public class CadastroFrame extends JFrame {
    private JTextField txtNome;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    private LoginFrame loginFrame;

    public CadastroFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Novo Usuário", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // Formulário
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Usuário"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos do formulário
        addFormField(panel, gbc, "Nome Completo:", txtNome = new JTextField(20), 0);
        addFormField(panel, gbc, "Username:", txtUsername = new JTextField(20), 1);
        addFormField(panel, gbc, "Email:", txtEmail = new JTextField(20), 2);
        addFormField(panel, gbc, "Senha:", txtSenha = new JPasswordField(20), 3);
        addFormField(panel, gbc, "Confirmar Senha:", txtConfirmarSenha = new JPasswordField(20), 4);

        return panel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");

        btnCadastrar.setBackground(new Color(34, 139, 34));
        btnCadastrar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);

        btnCadastrar.addActionListener(e -> cadastrarUsuario());
        btnCancelar.addActionListener(e -> cancelar());

        panel.add(btnCadastrar);
        panel.add(btnCancelar);

        return panel;
    }

    private void cadastrarUsuario() {
        String nome = txtNome.getText().trim();
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarSenha.getPassword());

        // Validações
        if (nome.isEmpty() || username.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!");
            return;
        }

        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres!");
            return;
        }

        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Email inválido!");
            return;
        }

        // Criar e cadastrar usuário
        Usuario novoUsuario = new Usuario(nome, username, senha, email);
        AuthenticationService auth = AuthenticationService.getInstance();

        if (auth.cadastrarUsuario(novoUsuario)) {
            JOptionPane.showMessageDialog(this,
                    "Usuário cadastrado com sucesso!\n\n" +
                            "Nome: " + nome + "\n" +
                            "Username: " + username + "\n" +
                            "Email: " + email,
                    "Cadastro Realizado",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Fechar cadastro e limpar login
            this.dispose();
            if (loginFrame != null) {
                loginFrame.limparCampos();
                loginFrame.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username já existe! Escolha outro.",
                    "Erro no Cadastro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cancelar() {
        this.dispose();
        if (loginFrame != null) {
            loginFrame.setVisible(true);
        }
    }
}
