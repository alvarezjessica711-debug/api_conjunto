<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Condominio Valle San Remo</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .card { border: 1px solid #ccc; padding: 20px; border-radius: 8px; margin-bottom: 20px; }
        a { display: inline-block; margin-right: 12px; text-decoration: none; color: #0b5fff; }
    </style>
</head>
<body>
    <h1>Portal del Condominio Valle San Remo</h1>
    <p>Gestión de paquetes, usuarios y apartamentos.</p>

    <div class="card">
        <h2>Paquetes</h2>
        <a href="${pageContext.request.contextPath}/paquetes">Listar paquetes</a>
        <a href="${pageContext.request.contextPath}/paquetes?action=nuevo">Registrar paquete</a>
    </div>

    <div class="card">
        <h2>Usuarios</h2>
        <a href="${pageContext.request.contextPath}/usuarios">Ver usuarios</a>
    </div>

    <div class="card">
        <h2>Apartamentos</h2>
        <a href="${pageContext.request.contextPath}/apartamentos">Ver apartamentos</a>
    </div>
</body>
</html>
