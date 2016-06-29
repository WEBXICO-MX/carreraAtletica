<%-- 
    Document   : emergente
    Created on : 08-jun-2016, 11:13:11
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%
int xCvePersona = request.getParameter("xCve") != null? Integer.parseInt(request.getParameter("xCve")):0;
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Carrera atlética XXV aniversario CGUTyP</title>
        <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
        <style>
            body { 
                font-family: 'Open Sans', sans-serif;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row" >
                <div class="col-md-12">
                    <h1 class="text-center"><a href="index.jsp?xCve=<%=xCvePersona%>" target="_blank">Regístrate aquí</a></h1>
                    <img src="img/convocatoria_1.jpg" alt="" style="margin:0 auto;" class="img-responsive"/>
                    <h1 class="text-center"><a href="index.jsp?xCve=<%=xCvePersona%>" target="_blank">Regístrate aquí</a></h1>
                    <img src="img/convocatoria_2.jpg" alt="" style="margin:0 auto;" class="img-responsive"/>
                </div>
            </div>
        </div>
    </body>
</html>