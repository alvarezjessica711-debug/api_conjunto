<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Paquetes</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        a { color: #0b5fff; }
    </style>
</head>
<body>
    <h1>Paquetes registrados</h1>
    <p><a href="${pageContext.request.contextPath}/">Volver al inicio</a></p>
    <p><a href="${pageContext.request.contextPath}/paquetes?action=nuevo">Registrar nuevo paquete</a></p>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <table>
        <tr>
            <th>ID</th>
            <th>Empresa</th>
            <th>Descripción</th>
            <th>Estado</th>
            <th>Fecha recepción</th>
            <th>Destinatario</th>
            <th>Apartamento</th>
        </tr>
        <c:forEach var="paquete" items="${paquetes}">
            <tr>
                <td>${paquete.idPaquete}</td>
                <td>${paquete.empresaTransportadora}</td>
                <td>${paquete.descripcion}</td>
                <td>${paquete.estado}</td>
                <td>${paquete.fechaRecepcion}</td>
                <td>${paquete.idUsuarioDestinatario}</td>
                <td>${paquete.idApartamento}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
