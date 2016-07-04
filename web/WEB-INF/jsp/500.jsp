<%-- 
    Document   : 500
    Created on : 04/07/2016, 09:24:44 AM
    Author     : Eder Weiss
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>UTTAB &#124; Home</title>
        <script>
            function redireccionar() {
                location.href='${pageContext.request.contextPath}/index.jsp';
            }
        </script>
    </head>
    <body onLoad="redireccionar();">
    </body>
</html>
