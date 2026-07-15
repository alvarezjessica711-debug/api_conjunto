<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Paquete</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 500px; }
        label, input, select, textarea { display: block; width: 100%; margin-bottom: 12px; }
        button { padding: 8px 14px; }
    </style>
</head>
<body>
    <h1>Registrar paquete</h1>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/paquetes" method="post">
        <label>Empresa transportadora</label>
        <input type="text" name="empresaTransportadora" required>

        <label>Descripción</label>
        <textarea name="descripcion"></textarea>

        <label>Estado</label>
        <select name="estado">
            <option value="Recibido en Porteria">Recibido en Porteria</option>
            <option value="Entregado al Residente">Entregado al Residente</option>
        </select>

        <label>ID usuario destinatario</label>
        <input type="number" name="idUsuarioDestinatario" required>

        <label>ID apartamento</label>
        <input type="number" name="idApartamento" required>

        <label>ID usuario registra</label>
        <input type="number" name="idUsuarioRegistra" required>

        <button type="submit">Guardar</button>
    </form>
</body>
</html>
