package Views;

import auth.AuthenticationService;
import models.Usuario;
import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
    private AuthenticationService auth;

    public HomeFrame() {
        this.auth = AuthenticationService.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sistema - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        createMenuBar();
        createMainPanel();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemPerfil = new JMenuItem("Meu Perfil");
        JMenuItem itemSair = new JMenuItem("Sair");

        itemPerfil.addActionListener(e -> abrirPerfil());
        itemSair.addActionListener(e -> sair());

        menuSistema.add(itemPerfil);
        menuSistema.addSeparator();
        menuSistema.add(itemSair);

        // Menu Ajuda
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemSobre = new JMenuItem("Sobre");

        itemSobre.addActionListener(e -> mostrarSobre());

        menuAjuda.add(itemSobre);

        menuBar.add(menuSistema);
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);
    }

    private void createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header com informações do usuário
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Conteúdo central
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Bem-vindo"));

        Usuario usuario = auth.getUsuarioLogado();

        JLabel lblUsuario = new JLabel("Usuário: " + usuario.getNome());
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblInfo = new JLabel("Username: " + usuario.getUsername() + " | Email: " + usuario.getEmail());

        panel.add(lblUsuario, BorderLayout.NORTH);
        panel.add(lblInfo, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Funcionalidades"));

        // Botões de funcionalidades
        JButton btnPerfil = createFeatureButton("Meu Perfil", "👤", new Color(70, 130, 180));
        JButton btnConfig = createFeatureButton("Configurações", "⚙️", new Color(50, 168, 82));
        JButton btnRelatorios = createFeatureButton("Relatórios", "📊", new Color(220, 20, 60));
        JButton btnAjuda = createFeatureButton("Ajuda", "❓", new Color(255, 140, 0));

        btnPerfil.addActionListener(e -> abrirPerfil());
        btnConfig.addActionListener(e -> mostrarConfiguracoes());
        btnRelatorios.addActionListener(e -> mostrarRelatorios());
        btnAjuda.addActionListener(e -> mostrarAjuda());

        panel.add(btnPerfil);
        panel.add(btnConfig);
        panel.add(btnRelatorios);
        panel.add(btnAjuda);

        return panel;
    }

    private JButton createFeatureButton(String texto, String emoji, Color cor) {
        JButton button = new JButton("<html><center>" + emoji + "<br>" + texto + "</center></html>");
        button.setBackground(cor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 80));
        return button;
    }

    private void abrirPerfil() {
        new PerfilFrame(this).setVisible(true);
    }

    private void sair() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente sair do sistema?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            auth.logout();
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }

    private void mostrarSobre() {
        JOptionPane.showMessageDialog(
                this,
                "Sistema Completo v1.0\n\n" +
                        "Desenvolvido no IntelliJ IDEA\n" +
                        "Tecnologias: Java Swing\n" +
                        "Ano: 2024",
                "Sobre o Sistema",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarConfiguracoes() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento...");
    }

    private void mostrarRelatorios() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento...");
    }

    private void mostrarAjuda() {
        JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento...");
    }
}