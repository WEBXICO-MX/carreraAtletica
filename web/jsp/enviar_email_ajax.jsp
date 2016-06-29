<%-- 
    Document   : enviar_email_ajax
    Created on : 24-jun-2016, 12:43:38
    Author     : Roberto Eder Weiss Juárez
--%>
<%@page import="carrera.atletica.comun.ErrorSistema"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.proceso.MailSender"%>
<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%
    Resultados rst = null;
    StringBuilder sql, msg;
    MailSender ms = new MailSender();
    int countEmail = 0;
    boolean result = false;

    String smtp_host = request.getParameter("xHost") != null ? request.getParameter("xHost") : "";
    String smtp_user = request.getParameter("xUser") != null ? request.getParameter("xUser") : "";
    String smtp_password = request.getParameter("xPasswd") != null ? request.getParameter("xPasswd") : "";

    String asunto = "Constancia de participación en la carrera atlética XXV aniversario CGUTyP";

    if (request.getParameter("xAccion") != null) {
        if (request.getParameter("xAccion").equals("enviarEmailConstanciaParticipacion")) {

            //sql = new StringBuilder("﻿SELECT id,UPPER(nombre+' '+ap_paterno+' '+ap_materno) AS nombre_completo, email FROM participantes WHERE asistio = 1 ORDER BY id");
            sql = new StringBuilder("SELECT 39 AS id,'JORGE JOSÉ JIMÉNEZ DEL CUETO' AS nombre_completo,'sistemas@uttab.edu.mx' AS email");
            //sql = new StringBuilder("SELECT 38 AS id,'Roberto Eder Weiss Juárez' AS nombre_completo,'weiss.uttab@gmail.com' AS email");
            rst = UtilDB.ejecutaConsulta(sql.toString());

            if (rst.recordCount() > 0) {
                while (rst.next()) {
                    msg = new StringBuilder("<strong>").append("Estimad@: ").append(rst.getString("nombre_completo")).append("</strong><br/>");
                    msg.append("<p>Gracias por participar en la \"Carrera atlética XXV aniversario CGUTyP\", te hacemos entrega de tu constancia de participación:</p>");
                    msg.append("<p><a href=\"http://www.uttab.edu.mx/carreraAtletica/jsp/prn_constancia_participacion.jsp?id=").append(rst.getInt("id")).append("\" target=\"_blank\">Descárgala aquí</a></p><br/>");
                    msg.append("<p><strong>Atentamente</strong></p>");
                    msg.append("<p><strong>Comité organizador</strong></p><br/><br/>");
                    msg.append("<p><img src=\"http://www.uttab.edu.mx/carreraAtletica/img/micrositio-carrera_02.png\" alt=\"Carrera atlética XXV aniversario CGUTyP\"/></p>");
                    msg.append("<p><img src=\"http://www.uttab.edu.mx/carreraAtletica/img/micrositio-carrera_01.png\" alt=\"Carrera atlética XXV aniversario CGUTyP\"/></p>");
                    result = ms.send(smtp_host, smtp_user, smtp_password, rst.getString("email"), asunto, true, msg.toString(), true);
                    if (result) {
                        countEmail++;
                    }
                }
                out.println("Se ha(n) enviado " + countEmail + " Email(s)");
            } else {
                out.println("No hay participantes que hayan asistido a la carrera para enviar constancia de participación");
            }

            rst.close();
        }
    } else {
        out.println("Acción no valida");
    }

%>
