package auth;

import models.Usuario;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private static AuthenticationService instance;
    private Map<String, Usuario> usuarios;
    private Usuario usuarioLogado;

    private AuthenticationService() {
        usuarios = new HashMap<>();
        // Usuários de exemplo
        usuarios.put("admin", new Usuario("Administrador", "admin", "admin123", "admin@email.com"));
        usuarios.put("usuario", new Usuario("Usuário Teste", "usuario", "123456", "usuario@email.com"));
    }

    public static AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public boolean login(String username, String senha) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            usuarioLogado = usuario;
            return true;
        }
        return false;
    }

    public void logout() {
        usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean cadastrarUsuario(Usuario novoUsuario) {
        if (usuarios.containsKey(novoUsuario.getUsername())) {
            return false; // Usuário já existe
        }
        usuarios.put(novoUsuario.getUsername(), novoUsuario);
        return true;
    }
}