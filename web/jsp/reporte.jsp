<%-- 
    Document   : reporte
    Created on : 07-jun-2016, 13:44:37
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="carrera.atletica.comun.Utilerias"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%
    boolean excel = false;
    StringBuilder sql = new StringBuilder("SELECT id, UPPER(nombre + ' '+ ap_paterno+' '+ap_materno) AS nombre_completo, fecha_nacimiento, sexo,");
    sql.append("\"categoria\" = CASE WHEN categoria =  1 THEN '3KM CARRERA' WHEN categoria = 2 THEN '3KM CAMINATA' ELSE 'No definido' END, numero_competidor, email,fecha_registro,asistio,activo ");
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
        <style>
            input[type=checkbox][name*=cbxAsistio] { cursor: pointer;}
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row" >
                <div class="col-md-12">&nbsp;</div>
                <div class="col-md-12"><h1 class="text-center">Reporte carrera atlética XXV aniversario CGUTyP</h1></div>
                <div class="col-sm-6 col-sm-offset-3">
                    <div class="alert fade in" id="div_mensaje" style="display:none; margin-top: 25px;">
                        <a href="#" class="close" onclick="$('.alert').hide()" aria-label="close">&times;</a> 
                        <!--<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>-->
                        <span id="mensaje"></span>
                    </div>
                </div>
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
                                <!--<th>Fecha de registro</th>-->
                                <th>Asistió</th>
                                <th>Hoja de confirmación</th>
                                <th>Constancia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% while (rst.next()) {%>
                            <tr>
                                <td><%= rst.getInt("id")%></td>
                                <td><%= rst.getString("numero_competidor")%></td>
                                <td><%= rst.getString("categoria")%></td>                                
                                <td><%= rst.getString("nombre_completo")%></td>
                                <td><%= Utilerias.getCadenaFecha(rst.getCalendar("fecha_nacimiento"))%></td>
                                <td><%= rst.getString("sexo")%></td>
                                <td><%= rst.getString("email")%></td>
                                <!--<td><%= Utilerias.getCadenaFechaHora(rst.getCalendar("fecha_registro"))%></td>-->
                                <td>
                                    <form name="frmAsitio<%= rst.getInt("id")%>" id="frmAsitio<%= rst.getInt("id")%>" method="post">
                                        <input type="checkbox" name="cbxAsistio<%= rst.getInt("id")%>" id="cbxAsistio<%= rst.getInt("id")%>" <%= rst.getBoolean("asistio") ? "checked" : ""%> onclick="setAsistencia(this);"/>
                                    </form>
                                </td>
                                <td><a href="prn_registro_pdf.jsp?id=<%= rst.getInt("id")%>" target="_blank">Descargar</a></td>
                                <td id="td_asistio<%= rst.getInt("id")%>">
                                    <% if(rst.getBoolean("asistio")){ %>
                                    <a href="prn_constancia_participacion.jsp?id=<%= rst.getInt("id")%>" target="_blank">Descargar</a>
                                    <%}else{%>
                                    No disponible
                                    <%}%>
                                </td>
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
                    <a href="javascript:void(0);" onclick="descargarExcel();">Descargar Excel <img src="../img/rsz_excel.png" alt="Excel" class="img-responsive" style="margin:0 auto;"/></a>
                        <%
                            }
                        %>
                </div>
            </div>
            <div class="row" >
                <div class="col-sm-12">
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Carrera atlética XXV aniversario CGUTyP</h4>
                                </div>
                                <div class="modal-body">
                                    <img src="../img/ajax-loading.gif" alt="Loading"/> procesando ...
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="../bower_components/jquery/dist/jquery.min.js"></script>
        <script src="../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="../bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
        <script src="../js/jsp/reporte.js"></script>
    </body>
</html>