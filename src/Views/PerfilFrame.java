package Views;

import auth.AuthenticationService;
import models.Usuario;
import javax.swing.*;
import java.awt.*;

public class PerfilFrame extends JFrame {
    private HomeFrame homeFrame;
    private AuthenticationService auth;
    private JTextField txtNome;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public PerfilFrame(HomeFrame homeFrame) {
        this.homeFrame = homeFrame;
        this.auth = AuthenticationService.getInstance();
        initializeUI();
        carregarDadosUsuario();
    }

    private void initializeUI() {
        setTitle("Meu Perfil");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(homeFrame);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel("Meu Perfil", JLabel.CENTER);
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
        panel.setBorder(BorderFactory.createTitledBorder("Informações Pessoais"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos (apenas para visualização - em um sistema real permitiria edição)
        addFormField(panel, gbc, "Nome:", txtNome = new JTextField(20), 0);
        addFormField(panel, gbc, "Username:", txtUsername = new JTextField(20), 1);
        addFormField(panel, gbc, "Email:", txtEmail = new JTextField(20), 2);

        // Tornar campos não editáveis (apenas visualização)
        txtNome.setEditable(false);
        txtUsername.setEditable(false);
        txtEmail.setEditable(false);

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

        btnSalvar = new JButton("Editar");
        btnCancelar = new JButton("Fechar");

        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.WHITE);

        btnSalvar.addActionListener(e -> editarPerfil());
        btnCancelar.addActionListener(e -> fechar());

        panel.add(btnSalvar);
        panel.add(btnCancelar);

        return panel;
    }

    private void carregarDadosUsuario() {
        Usuario usuario = auth.getUsuarioLogado();
        if (usuario != null) {
            txtNome.setText(usuario.getNome());
            txtUsername.setText(usuario.getUsername());
            txtEmail.setText(usuario.getEmail());
        }
    }

    private void editarPerfil() {
        JOptionPane.showMessageDialog(this,
                "Funcionalidade de edição em desenvolvimento!\n\n" +
                        "Em um sistema real, aqui você poderia:\n" +
                        "- Alterar nome e email\n" +
                        "- Mudar senha\n" +
                        "- Atualizar foto de perfil",
                "Editar Perfil",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void fechar() {
        this.dispose();
    }
}