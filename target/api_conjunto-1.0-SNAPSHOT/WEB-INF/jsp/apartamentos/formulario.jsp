<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Apartamento</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 300px; display: grid; gap: 10px; }
        input, button { padding: 8px; }
    </style>
</head>
<body>
    <h1>Registrar Apartamento</h1>
    <p><a href="${pageContext.request.contextPath}/apartamentos">Volver</a></p>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/apartamentos">
        <label>Torre</label>
        <input type="text" name="torre" required>
        <label>Número</label>
        <input type="text" name="numero" required>
        <button type="submit">Guardar</button>
    </form>
</body>
</html>
