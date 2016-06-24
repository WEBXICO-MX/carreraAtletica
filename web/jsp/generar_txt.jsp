<%-- 
    Document   : generar_txt
    Created on : 24-jun-2016, 15:37:13
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="java.io.BufferedWriter"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Files"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    ServletContext servletContext = request.getSession().getServletContext();
    String relativeWebPath = "WEB-INF/participantes.txt";
    String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

    StringBuilder sql = new StringBuilder("SELECT id,UPPER(nombre+' '+ap_paterno+' '+ap_materno) AS nombre_completo, email FROM participantes WHERE asistio = 1 ORDER BY id");
    Resultados rst = UtilDB.ejecutaConsulta(sql.toString());
    int size = rst.recordCount();
    int count = 0;

    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(absoluteDiskPath))) {

        while (rst.next()) {
            if (++count < size) {
                writer.write(rst.getInt("id") + "," + rst.getString("nombre_completo") + "," + rst.getString("email") + "\n");
            } else {
                writer.write(rst.getInt("id") + "," + rst.getString("nombre_completo") + "," + rst.getString("email"));
            }

        }
        rst.close();
        out.println("Archivo txt generado con éxito");

    } // the file will be automatically closed

    rst.close();
    
%>