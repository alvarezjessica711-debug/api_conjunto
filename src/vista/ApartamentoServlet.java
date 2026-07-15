package vista;

import dao.ApartamentoDAO;
import modelo.Apartamento;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ApartamentoServlet extends HttpServlet {

    private final ApartamentoDAO apartamentoDAO = new ApartamentoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("nuevo".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/formulario.jsp").forward(request, response);
            return;
        }

        if ("editar".equals(action) && idParam != null) {
            try {
                Apartamento apartamento = apartamentoDAO.obtenerPorId(Integer.parseInt(idParam));
                request.setAttribute("apartamento", apartamento);
                request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/formulario.jsp").forward(request, response);
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/lista.jsp").forward(request, response);
            }
            return;
        }

        if ("eliminar".equals(action) && idParam != null) {
            try {
                apartamentoDAO.eliminar(Integer.parseInt(idParam));
                response.sendRedirect(request.getContextPath() + "/apartamentos");
            } catch (SQLException e) {
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/lista.jsp").forward(request, response);
            }
            return;
        }

        try {
            List<Apartamento> apartamentos = apartamentoDAO.listar();
            request.setAttribute("apartamentos", apartamentos);
            request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/lista.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/lista.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Apartamento apartamento = new Apartamento();
            String idParam = request.getParameter("idApartamento");
            if (idParam != null && !idParam.isBlank()) {
                apartamento.setIdApartamento(Integer.parseInt(idParam));
            }
            apartamento.setTorre(request.getParameter("torre"));
            apartamento.setNumero(request.getParameter("numero"));

            if (apartamento.getIdApartamento() > 0) {
                apartamentoDAO.actualizar(apartamento);
                response.sendRedirect(request.getContextPath() + "/apartamentos");
            } else {
                int id = apartamentoDAO.insertar(apartamento);
                request.getSession().setAttribute("mensaje", "Apartamento registrado correctamente con ID " + id);
                response.sendRedirect(request.getContextPath() + "/apartamentos");
            }
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/formulario.jsp").forward(request, response);
        }
    }
}
