package dao;

import conexion.ConexionBD;
import modelo.Apartamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private Apartamento mapear(ResultSet rs) throws SQLException {
        Apartamento apartamento = new Apartamento();
        apartamento.setIdApartamento(rs.getInt("id_apartamento"));
        apartamento.setTorre(rs.getString("torre"));
        apartamento.setNumero(rs.getString("numero"));
        return apartamento;
    }
}
