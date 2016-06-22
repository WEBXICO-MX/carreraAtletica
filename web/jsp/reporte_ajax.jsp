<%-- 
    Document   : reporte_ajax
    Created on : 22-jun-2016, 15:28:04
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="carrera.atletica.comun.ErrorSistema"%>
<%@page import="carrera.atletica.proceso.Participante"%>
<%@page contentType="application/json" pageEncoding="UTF-8" session="false"%>
<%
    int xCveParticipante = request.getParameter("xCveParticipante") != null ? (Integer.parseInt(request.getParameter("xCveParticipante"))) : 0;
    Participante p = new Participante(xCveParticipante);
    boolean asistencia = request.getParameter("xAsistio") != null ? (Boolean.parseBoolean(request.getParameter("xAsistio"))) : false;
    String xAccion = request.getParameter("xAccion") != null ? request.getParameter("xAccion") : "";
    String resultado = "";

    if (!xAccion.equals("")) {

        if (xAccion.equals("setAsistio")) {
            p.setAsistio(asistencia);
            ErrorSistema err = p.grabar();

            if (err.getNumeroError() != 0) {
                resultado = "{\"resultado\":0,\"mensaje\":\"Asistencia no actualizada\"}";
            } else {
                resultado = "{\"resultado\":1,\"mensaje\":\"Asistencia actualizada con éxito\",\"participante_id\":" + p.getId() + "}";
            }
        }

    } else {
        resultado = "{\"resultado\":0,\"mensaje\":\"Acción no valida\"}";
    }
    out.println(resultado);
    out.flush();
%>