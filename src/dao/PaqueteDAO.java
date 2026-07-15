package dao;

import conexion.ConexionBD;
import modelo.Paquete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PaqueteDAO {

    public List<Paquete> listar() throws SQLException {
        List<Paquete> paquetes = new ArrayList<>();
        String sql = "SELECT id_paquete, empresa_transportadora, descripcion, estado, fecha_recepcion, fecha_entrega, id_usuario_destinatario, id_apartamento, id_usuario_registra FROM paquetes ORDER BY id_paquete DESC";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                paquetes.add(mapear(rs));
            }
        }
        return paquetes;
    }

    public Paquete obtenerPorId(int id) throws SQLException {
        String sql = "SELECT id_paquete, empresa_transportadora, descripcion, estado, fecha_recepcion, fecha_entrega, id_usuario_destinatario, id_apartamento, id_usuario_registra FROM paquetes WHERE id_paquete = ?";

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

    public int insertar(Paquete paquete) throws SQLException {
        String sql = "INSERT INTO paquetes (empresa_transportadora, descripcion, estado, fecha_recepcion, fecha_entrega, id_usuario_destinatario, id_apartamento, id_usuario_registra) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, paquete.getEmpresaTransportadora());
            stmt.setString(2, paquete.getDescripcion());
            stmt.setString(3, paquete.getEstado() != null ? paquete.getEstado() : "Recibido en Porteria");
            stmt.setTimestamp(4, paquete.getFechaRecepcion() != null ? paquete.getFechaRecepcion() : new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(5, paquete.getFechaEntrega());
            stmt.setInt(6, paquete.getIdUsuarioDestinatario());
            stmt.setInt(7, paquete.getIdApartamento());
            stmt.setInt(8, paquete.getIdUsuarioRegistra());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }

    public boolean actualizar(Paquete paquete) throws SQLException {
        String sql = "UPDATE paquetes SET empresa_transportadora = ?, descripcion = ?, estado = ?, fecha_entrega = ?, id_usuario_destinatario = ?, id_apartamento = ?, id_usuario_registra = ? WHERE id_paquete = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paquete.getEmpresaTransportadora());
            stmt.setString(2, paquete.getDescripcion());
            stmt.setString(3, paquete.getEstado());
            stmt.setTimestamp(4, paquete.getFechaEntrega());
            stmt.setInt(5, paquete.getIdUsuarioDestinatario());
            stmt.setInt(6, paquete.getIdApartamento());
            stmt.setInt(7, paquete.getIdUsuarioRegistra());
            stmt.setInt(8, paquete.getIdPaquete());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM paquetes WHERE id_paquete = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Paquete mapear(ResultSet rs) throws SQLException {
        Paquete paquete = new Paquete();
        paquete.setIdPaquete(rs.getInt("id_paquete"));
        paquete.setEmpresaTransportadora(rs.getString("empresa_transportadora"));
        paquete.setDescripcion(rs.getString("descripcion"));
        paquete.setEstado(rs.getString("estado"));
        paquete.setFechaRecepcion(rs.getTimestamp("fecha_recepcion"));
        paquete.setFechaEntrega(rs.getTimestamp("fecha_entrega"));
        paquete.setIdUsuarioDestinatario(rs.getInt("id_usuario_destinatario"));
        paquete.setIdApartamento(rs.getInt("id_apartamento"));
        paquete.setIdUsuarioRegistra(rs.getInt("id_usuario_registra"));
        return paquete;
    }
}
