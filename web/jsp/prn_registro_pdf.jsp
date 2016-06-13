<%-- 
    Document   : prn_registro_pdf
    Created on : 07-jun-2016, 12:43:13
    Author     : Roberto Eder Weiss Juárez
--%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.comun.Utilerias"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="com.lowagie.text.pdf.AcroFields"%>
<%@page import="com.lowagie.text.pdf.PdfStamper"%>
<%@page import="com.lowagie.text.pdf.PdfReader"%>
<%@page contentType="application/pdf" pageEncoding="UTF-8"%>
<%
    int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;

    StringBuilder sql = new StringBuilder("SELECT p.id,(nombre + ' '+ ap_paterno+' '+ap_materno) AS nombre_completo, fecha_nacimiento, sexo,");
    sql.append("\"categoria\" = CASE WHEN categoria =  1 THEN '3KM CARRERA' WHEN categoria = 2 THEN '3KM CAMINATA' ELSE 'No definido' END, numero_competidor, email,fecha_registro,activo ");
    sql.append("FROM participantes AS p ");
    sql.append("WHERE p.id = ").append(id);

    Resultados rst = UtilDB.ejecutaConsulta(sql.toString());
    
    String archivoPlantilla = "";
    archivoPlantilla = "/plantillas/plantilla.pdf";
    PdfReader reader = new PdfReader(application.getRealPath(archivoPlantilla));
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PdfStamper stamp = new PdfStamper(reader, buffer);
    AcroFields form = stamp.getAcroFields();

    if (rst.next()) {
        form.setField("txtNumeroCompetidor", rst.getString("numero_competidor"));
        form.setField("txtNumeroCompetidor2", rst.getString("numero_competidor"));
        form.setField("txtNumeroCompetidor3", rst.getString("numero_competidor"));
        form.setField("txtNombre", rst.getString("nombre_completo"));
        form.setField("txtFechaNacimiento", Utilerias.getCadenaFecha(rst.getCalendar("fecha_nacimiento")));
        form.setField("txtSexo", rst.getString("sexo"));
        form.setField("txtCategoria", rst.getString("categoria"));
        form.setField("txtCodigo2", rst.getString("numero_competidor"));
    }
    rst.close();

    stamp.setFormFlattening(true);
    stamp.close();
    ServletOutputStream salida = response.getOutputStream();
    response.setContentType("application/pdf");
    response.setContentLength(buffer.size());
    buffer.writeTo(salida);
    salida.flush();
    response.flushBuffer();
    System.gc();
%>