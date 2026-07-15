package vista;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import dao.ApartamentoDAO;
import dao.PaqueteDAO;
import dao.UsuarioDAO;
import modelo.Apartamento;
import modelo.Paquete;
import modelo.Usuario;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class Principal {
    private static final PaqueteDAO PAQUETE_DAO = new PaqueteDAO();
    private static final UsuarioDAO USUARIO_DAO = new UsuarioDAO();
    private static final ApartamentoDAO APARTAMENTO_DAO = new ApartamentoDAO();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/paquetes", Principal::handlePaquetes);
        server.createContext("/api/usuarios", Principal::handleUsuarios);
        server.createContext("/api/apartamentos", Principal::handleApartamentos);
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor REST iniciado en http://localhost:8080");
    }

    private static void handlePaquetes(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            if ("GET".equals(method) && "/api/paquetes".equals(path)) {
                List<Paquete> paquetes = PAQUETE_DAO.listar();
                sendJson(exchange, 200, toJson(paquetes));
                return;
            }

            if ("GET".equals(method) && path.startsWith("/api/paquetes/")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                Paquete paquete = PAQUETE_DAO.obtenerPorId(id);
                if (paquete != null) {
                    sendJson(exchange, 200, toJson(paquete));
                } else {
                    sendJson(exchange, 404, "{\"error\":\"Paquete no encontrado\"}");
                }
                return;
            }

            if ("POST".equals(method) && "/api/paquetes".equals(path)) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Paquete paquete = parsePaquete(body);
                int id = PAQUETE_DAO.insertar(paquete);
                sendJson(exchange, 201, "{\"id\":" + id + "}");
                return;
            }

            if ("PUT".equals(method) && path.startsWith("/api/paquetes/")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Paquete paquete = parsePaquete(body);
                paquete.setIdPaquete(id);
                boolean ok = PAQUETE_DAO.actualizar(paquete);
                sendJson(exchange, ok ? 200 : 404, ok ? "{\"ok\":true}" : "{\"error\":\"Paquete no encontrado\"}");
                return;
            }

            if ("DELETE".equals(method) && path.startsWith("/api/paquetes/")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                boolean ok = PAQUETE_DAO.eliminar(id);
                sendJson(exchange, ok ? 200 : 404, ok ? "{\"ok\":true}" : "{\"error\":\"Paquete no encontrado\"}");
                return;
            }

            sendJson(exchange, 404, "{\"error\":\"Ruta no encontrada\"}");
        } catch (SQLException e) {
            sendJson(exchange, 500, "{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"Solicitud inválida\"}");
        }
    }

    private static void handleUsuarios(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<Usuario> usuarios = USUARIO_DAO.listar();
                sendJson(exchange, 200, toJson(usuarios));
            } else {
                sendJson(exchange, 405, "{\"error\":\"Método no permitido\"}");
            }
        } catch (SQLException e) {
            sendJson(exchange, 500, "{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }

    private static void handleApartamentos(HttpExchange exchange) throws IOException {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<Apartamento> apartamentos = APARTAMENTO_DAO.listar();
                sendJson(exchange, 200, toJson(apartamentos));
            } else {
                sendJson(exchange, 405, "{\"error\":\"Método no permitido\"}");
            }
        } catch (SQLException e) {
            sendJson(exchange, 500, "{\"error\":\"" + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }

    private static void sendJson(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        }
    }

    private static String toJson(Object data) {
        if (data instanceof List<?> list) {
            if (list.isEmpty()) {
                return "[]";
            }

            StringBuilder builder = new StringBuilder("[");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(toSimpleJson(list.get(i)));
            }
            builder.append("]");
            return builder.toString();
        }
        return toSimpleJson(data);
    }

    private static String toSimpleJson(Object data) {
        if (data instanceof Paquete paquete) {
            return "{\"idPaquete\":" + paquete.getIdPaquete() + ",\"empresaTransportadora\":\"" + escape(paquete.getEmpresaTransportadora()) + "\",\"descripcion\":\"" + escape(paquete.getDescripcion()) + "\",\"estado\":\"" + escape(paquete.getEstado()) + "\",\"fechaRecepcion\":\"" + (paquete.getFechaRecepcion() != null ? paquete.getFechaRecepcion().toString() : "") + "\",\"fechaEntrega\":\"" + (paquete.getFechaEntrega() != null ? paquete.getFechaEntrega().toString() : "") + "\",\"idUsuarioDestinatario\":" + paquete.getIdUsuarioDestinatario() + ",\"idApartamento\":" + paquete.getIdApartamento() + ",\"idUsuarioRegistra\":" + paquete.getIdUsuarioRegistra() + "}";
        }
        if (data instanceof Usuario usuario) {
            return "{\"idUsuario\":" + usuario.getIdUsuario() + ",\"idRol\":" + usuario.getIdRol() + ",\"documento\":\"" + escape(usuario.getDocumento()) + "\",\"nombreCompleto\":\"" + escape(usuario.getNombreCompleto()) + "\",\"email\":\"" + escape(usuario.getEmail()) + "\",\"telefono\":\"" + escape(usuario.getTelefono()) + "\",\"estado\":\"" + escape(usuario.getEstado()) + "\"}";
        }
        if (data instanceof Apartamento apartamento) {
            return "{\"idApartamento\":" + apartamento.getIdApartamento() + ",\"torre\":\"" + escape(apartamento.getTorre()) + "\",\"numero\":\"" + escape(apartamento.getNumero()) + "\"}";
        }
        return "{}";
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static Paquete parsePaquete(String body) {
        Paquete paquete = new Paquete();
        if (body == null || body.isBlank()) {
            return paquete;
        }

        String empresa = extractValue(body, "empresaTransportadora");
        String descripcion = extractValue(body, "descripcion");
        String estado = extractValue(body, "estado");
        String idDestino = extractValue(body, "idUsuarioDestinatario");
        String idApartamento = extractValue(body, "idApartamento");
        String idRegistra = extractValue(body, "idUsuarioRegistra");

        paquete.setEmpresaTransportadora(empresa);
        paquete.setDescripcion(descripcion);
        paquete.setEstado(estado);
        paquete.setIdUsuarioDestinatario(idDestino != null ? Integer.parseInt(idDestino) : 0);
        paquete.setIdApartamento(idApartamento != null ? Integer.parseInt(idApartamento) : 0);
        paquete.setIdUsuarioRegistra(idRegistra != null ? Integer.parseInt(idRegistra) : 0);
        paquete.setFechaRecepcion(new Timestamp(System.currentTimeMillis()));
        return paquete;
    }

    private static String extractValue(String body, String key) {
        String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]*)\"";
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(pattern).matcher(body);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
