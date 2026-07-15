package dao;

import conexion.ConexionBD;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id_usuario, id_rol, documento, nombre_completo, email, telefono, estado FROM usuarios ORDER BY id_usuario";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapear(rs));
            }
        }
        return usuarios;
    }

    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT id_usuario, id_rol, documento, nombre_completo, email, telefono, estado FROM usuarios WHERE id_usuario = ?";

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

    public int insertar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id_rol, documento, nombre_completo, email, password_hash, telefono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection()) {
            int idRol = ensureDefaultRole(conn, usuario.getIdRol());

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, idRol);
                stmt.setString(2, usuario.getDocumento());
                stmt.setString(3, usuario.getNombreCompleto());
                stmt.setString(4, usuario.getEmail());
                stmt.setString(5, "temp123");
                stmt.setString(6, usuario.getTelefono());
                stmt.setString(7, usuario.getEstado() != null ? usuario.getEstado() : "Activo");
                stmt.executeUpdate();

                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    private int ensureDefaultRole(Connection conn, int requestedRoleId) throws SQLException {
        int roleId = requestedRoleId > 0 ? requestedRoleId : 1;

        try (PreparedStatement check = conn.prepareStatement("SELECT id_rol FROM roles WHERE id_rol = ?")) {
            check.setInt(1, roleId);
            try (ResultSet rs = check.executeQuery()) {
                if (rs.next()) {
                    return roleId;
                }
            }
        }

        try (PreparedStatement insert = conn.prepareStatement("INSERT INTO roles (nombre_rol) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            insert.setString(1, "Residente");
            insert.executeUpdate();
            try (ResultSet keys = insert.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }

        return 1;
    }

    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET id_rol = ?, documento = ?, nombre_completo = ?, email = ?, telefono = ?, estado = ? WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection()) {
            int idRol = ensureDefaultRole(conn, usuario.getIdRol());

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idRol);
                stmt.setString(2, usuario.getDocumento());
                stmt.setString(3, usuario.getNombreCompleto());
                stmt.setString(4, usuario.getEmail());
                stmt.setString(5, usuario.getTelefono());
                stmt.setString(6, usuario.getEstado());
                stmt.setInt(7, usuario.getIdUsuario());
                return stmt.executeUpdate() > 0;
            }
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setIdRol(rs.getInt("id_rol"));
        usuario.setDocumento(rs.getString("documento"));
        usuario.setNombreCompleto(rs.getString("nombre_completo"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setEstado(rs.getString("estado"));
        return usuario;
    }
}
