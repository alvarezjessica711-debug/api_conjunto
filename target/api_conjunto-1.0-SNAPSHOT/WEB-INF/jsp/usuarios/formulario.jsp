<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Usuario</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 400px; display: grid; gap: 10px; }
        input, select, button { padding: 8px; }
    </style>
</head>
<body>
    <h1>Registrar Usuario</h1>
    <p><a href="${pageContext.request.contextPath}/usuarios">Volver</a></p>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/usuarios">
        <label>Documento</label>
        <input type="text" name="documento" required>
        <label>Nombre completo</label>
        <input type="text" name="nombreCompleto" required>
        <label>Email</label>
        <input type="email" name="email" required>
        <label>Teléfono</label>
        <input type="text" name="telefono" required>
        <label>Estado</label>
        <select name="estado">
            <option value="Activo">Activo</option>
            <option value="Inactivo">Inactivo</option>
        </select>
        <button type="submit">Guardar</button>
    </form>
</body>
</html>
