package vista;

import dao.RolDAO;
import modelo.Rol;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RolServlet extends HttpServlet {

    private final RolDAO rolDAO = new RolDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("nuevo".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/jsp/roles/formulario.jsp").forward(request, response);
            return;
        }

        if ("editar".equals(action) && idParam != null) {
            try {
                Rol rol = rolDAO.obtenerPorId(Integer.parseInt(idParam));
                request.setAttribute("rol", rol);
                request.getRequestDispatcher("/WEB-INF/jsp/roles/formulario.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/roles/lista.jsp").forward(request, response);
            }
            return;
        }

        if ("eliminar".equals(action) && idParam != null) {
            try {
                rolDAO.eliminar(Integer.parseInt(idParam));
                response.sendRedirect(request.getContextPath() + "/roles");
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/roles/lista.jsp").forward(request, response);
            }
            return;
        }

        try {
            List<Rol> roles = rolDAO.listar();
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/jsp/roles/lista.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/roles/lista.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Rol rol = new Rol();
            String idParam = request.getParameter("idRol");
            if (idParam != null && !idParam.isBlank()) {
                rol.setIdRol(Integer.parseInt(idParam));
            }
            rol.setNombreRol(request.getParameter("nombreRol"));

            if (rol.getIdRol() > 0) {
                rolDAO.actualizar(rol);
            } else {
                rolDAO.insertar(rol);
            }
            response.sendRedirect(request.getContextPath() + "/roles");
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/roles/formulario.jsp").forward(request, response);
        }
    }
}
