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
        String action = request.getParameter("action");

        if ("nuevo".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/formulario.jsp").forward(request, response);
            return;
        }

        try {
            List<Usuario> usuarios = usuarioDAO.listar();
            request.setAttribute("usuarios", usuarios);
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/lista.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/lista.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = new Usuario();
            usuario.setIdRol(1);
            usuario.setDocumento(request.getParameter("documento"));
            usuario.setNombreCompleto(request.getParameter("nombreCompleto"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setTelefono(request.getParameter("telefono"));
            usuario.setEstado(request.getParameter("estado"));

            int id = usuarioDAO.insertar(usuario);
            request.getSession().setAttribute("mensaje", "Usuario registrado correctamente con ID " + id);
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/formulario.jsp").forward(request, response);
        }
    }
}
