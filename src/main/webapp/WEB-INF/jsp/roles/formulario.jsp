<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Rol</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        form { max-width: 300px; display: grid; gap: 10px; }
        input, button { padding: 8px; }
    </style>
</head>
<body>
    <h1>Registrar Rol</h1>
    <p><a href="${pageContext.request.contextPath}/roles">Volver</a></p>
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/roles">
        <input type="hidden" name="idRol" value="${rol != null ? rol.idRol : ''}">
        <label>Nombre del rol</label>
        <input type="text" name="nombreRol" value="${rol != null ? rol.nombreRol : ''}" required>
        <button type="submit">Guardar</button>
    </form>
</body>
</html>
