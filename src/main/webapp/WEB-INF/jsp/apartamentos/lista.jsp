<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Apartamentos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    </style>
</head>
<body>
    <h1>Apartamentos</h1>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>
    <table>
        <tr>
            <th>ID</th>
            <th>Torre</th>
            <th>Número</th>
        </tr>
        <c:forEach var="apartamento" items="${apartamentos}">
            <tr>
                <td>${apartamento.idApartamento}</td>
                <td>${apartamento.torre}</td>
                <td>${apartamento.numero}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
