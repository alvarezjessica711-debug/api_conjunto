package dao;

import conexion.ConexionBD;
import modelo.Apartamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoDAO {

    public List<Apartamento> listar() throws SQLException {
        List<Apartamento> apartamentos = new ArrayList<>();
        String sql = "SELECT id_apartamento, torre, numero FROM apartamentos ORDER BY id_apartamento";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                apartamentos.add(mapear(rs));
            }
        }
        return apartamentos;
    }

    public Apartamento obtenerPorId(int id) throws SQLException {
        String sql = "SELECT id_apartamento, torre, numero FROM apartamentos WHERE id_apartamento = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    public int insertar(Apartamento apartamento) throws SQLException {
        String sql = "INSERT INTO apartamentos (torre, numero) VALUES (?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, apartamento.getTorre());
            stmt.setString(2, apartamento.getNumero());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }

    private Apartamento mapear(ResultSet rs) throws SQLException {
        Apartamento apartamento = new Apartamento();
        apartamento.setIdApartamento(rs.getInt("id_apartamento"));
        apartamento.setTorre(rs.getString("torre"));
        apartamento.setNumero(rs.getString("numero"));
        return apartamento;
    }
}
