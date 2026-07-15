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
        try {
            List<Apartamento> apartamentos = apartamentoDAO.listar();
            request.setAttribute("apartamentos", apartamentos);
            request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/lista.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/jsp/apartamentos/lista.jsp").forward(request, response);
        }
    }
}
