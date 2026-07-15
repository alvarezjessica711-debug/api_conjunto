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
        String idParam = request.getParameter("id");

        if ("nuevo".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/formulario.jsp").forward(request, response);
            return;
        }

        if ("editar".equals(action) && idParam != null) {
            try {
                Usuario usuario = usuarioDAO.obtenerPorId(Integer.parseInt(idParam));
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/WEB-INF/jsp/usuarios/formulario.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/usuarios/lista.jsp").forward(request, response);
            }
            return;
        }

        if ("eliminar".equals(action) && idParam != null) {
            try {
                usuarioDAO.eliminar(Integer.parseInt(idParam));
                response.sendRedirect(request.getContextPath() + "/usuarios");
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/usuarios/lista.jsp").forward(request, response);
            }
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
            String idParam = request.getParameter("idUsuario");
            if (idParam != null && !idParam.isBlank()) {
                usuario.setIdUsuario(Integer.parseInt(idParam));
            }
            usuario.setIdRol(1);
            usuario.setDocumento(request.getParameter("documento"));
            usuario.setNombreCompleto(request.getParameter("nombreCompleto"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setTelefono(request.getParameter("telefono"));
            usuario.setEstado(request.getParameter("estado"));

            if (usuario.getIdUsuario() > 0) {
                usuarioDAO.actualizar(usuario);
                response.sendRedirect(request.getContextPath() + "/usuarios");
            } else {
                int id = usuarioDAO.insertar(usuario);
                request.getSession().setAttribute("mensaje", "Usuario registrado correctamente con ID " + id);
                response.sendRedirect(request.getContextPath() + "/usuarios");
            }
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/usuarios/formulario.jsp").forward(request, response);
        }
    }
}
