<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Roles</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    </style>
</head>
<body>
    <h1>Roles</h1>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a> | <a href="${pageContext.request.contextPath}/roles?action=nuevo">Registrar rol</a></p>
    <table>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Acciones</th>
        </tr>
        <c:forEach var="rol" items="${roles}">
            <tr>
                <td>${rol.idRol}</td>
                <td>${rol.nombreRol}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/roles?action=editar&id=${rol.idRol}">Editar</a>
                    <a href="${pageContext.request.contextPath}/roles?action=eliminar&id=${rol.idRol}" onclick="return confirm('¿Eliminar este rol?')">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
