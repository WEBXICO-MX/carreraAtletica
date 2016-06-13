<%-- 
    Document   : reporte
    Created on : 07-jun-2016, 13:44:37
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="carrera.atletica.comun.Utilerias"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    boolean excel = false;
    StringBuilder sql = new StringBuilder("SELECT id, (nombre + ' '+ ap_paterno+' '+ap_materno) AS nombre_completo, fecha_nacimiento, sexo,");
    sql.append("\"categoria\" = CASE WHEN categoria =  1 THEN '3KM CARRERA' WHEN categoria = 2 THEN '3KM CAMINATA' ELSE 'No definido' END, numero_competidor, email,fecha_registro,activo ");
    sql.append("FROM participantes ORDER BY fecha_registro DESC");

    Resultados rst = UtilDB.ejecutaConsulta(sql.toString());
    if (rst.recordCount() > 0) {
        excel = true;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Carrera atlética XXV aniversario CGUTyP</title>
        <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="../bower_components/datatables.net-dt/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="row" >
                <div class="col-md-12">&nbsp;</div>
                <div class="col-md-12"><h1 class="text-center">Reporte carrera atlética XXV aniversario CGUTyP</h1></div>
                <div class="col-md-12">                  
                    <table id="registros_carrera_atletica" class="display" cellspacing="0" width="100%">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Número de competidor</th>
                                <th>Categoría</th>
                                <th>Nombre completo</th>
                                <th>Fecha de nacimiento</th>
                                <th>Sexo</th>
                                <th>Email</th>
                                <th>Fecha de registro</th>
                                <th>PDF</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% while (rst.next()) {%>
                            <tr>
                                <th><%= rst.getInt("id")%></th>
                                <th><%= rst.getString("numero_competidor")%></th>
                                <th><%= rst.getString("categoria")%></th>                                
                                <th><%= rst.getString("nombre_completo")%></th>
                                <th><%= Utilerias.getCadenaFecha(rst.getCalendar("fecha_nacimiento"))%></th>
                                <th><%= rst.getString("sexo")%></th>
                                <th><%= rst.getString("email")%></th>
                                <th><%= Utilerias.getCadenaFechaHora(rst.getCalendar("fecha_registro"))%></th>
                                <th><a href="prn_registro_pdf.jsp?id=<%= rst.getInt("id")%>" target="_blank">Imprimir</a></th>
                            </tr> 
                            <%}
                                rst.close();
                            %>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 text-center">
                    <%
                        if (excel) {
                    %>
                    <a href="prn_registro_excel.jsp" target="_blank">Descargar Excel <img src="../img/rsz_excel.png" alt="Excel" class="img-responsive" style="margin:0 auto;"/></a>
                        <%
                            }
                        %>
                </div>
            </div>
        </div>
        <script src="../bower_components/jquery/dist/jquery.min.js"></script>
        <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="../bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('#registros_carrera_atletica').DataTable({"pageLength": 25}).order([0, 'desc']).draw();
            });
        </script>
    </body>
</html>