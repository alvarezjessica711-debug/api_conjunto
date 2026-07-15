package vista;

import dao.PaqueteDAO;
import modelo.Paquete;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PaqueteServlet extends HttpServlet {

    private final PaqueteDAO paqueteDAO = new PaqueteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("nuevo".equals(action)) {
            request.setAttribute("mensaje", "Complete el formulario para registrar un paquete");
            request.getRequestDispatcher("/WEB-INF/jsp/paquetes/formulario.jsp").forward(request, response);
            return;
        }

        try {
            request.setAttribute("paquetes", paqueteDAO.listar());
            request.getRequestDispatcher("/WEB-INF/jsp/paquetes/lista.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/paquetes/lista.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Paquete paquete = new Paquete();
            paquete.setEmpresaTransportadora(request.getParameter("empresaTransportadora"));
            paquete.setDescripcion(request.getParameter("descripcion"));
            paquete.setEstado(request.getParameter("estado"));
            paquete.setFechaRecepcion(new Timestamp(System.currentTimeMillis()));
            paquete.setIdUsuarioDestinatario(parseInt(request.getParameter("idUsuarioDestinatario")));
            paquete.setIdApartamento(parseInt(request.getParameter("idApartamento")));
            paquete.setIdUsuarioRegistra(parseInt(request.getParameter("idUsuarioRegistra")));

            int id = paqueteDAO.insertar(paquete);
            request.getSession().setAttribute("mensaje", "Paquete registrado correctamente con ID " + id);
            response.sendRedirect(request.getContextPath() + "/paquetes");
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/paquetes/formulario.jsp").forward(request, response);
        }
    }

    private int parseInt(String value) {
        if (value == null || value.isBlank()) {
            return 0;
        }
        return Integer.parseInt(value);
    }
}
