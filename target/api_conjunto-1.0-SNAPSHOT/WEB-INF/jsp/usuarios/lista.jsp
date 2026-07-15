<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Usuarios</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    </style>
</head>
<body>
    <h1>Usuarios</h1>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>
    <table>
        <tr>
            <th>ID</th>
            <th>Documento</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Estado</th>
        </tr>
        <c:forEach var="usuario" items="${usuarios}">
            <tr>
                <td>${usuario.idUsuario}</td>
                <td>${usuario.documento}</td>
                <td>${usuario.nombreCompleto}</td>
                <td>${usuario.email}</td>
                <td>${usuario.telefono}</td>
                <td>${usuario.estado}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
