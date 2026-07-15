package vista;

import dao.UsuarioDAO;
import modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Usuario> usuarios = usuarioDAO.listar();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/lista.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/lista.jsp").forward(request, response);
        }
    }
}
