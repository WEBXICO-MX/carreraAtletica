<%-- 
    Document   : prn_constancia_participacion
    Created on : 27-jun-2016, 13:35:00
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="java.awt.Color"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.pdf.BarcodeQRCode"%>
<%@page import="com.itextpdf.text.pdf.PdfContentByte"%>
<%@page import="com.itextpdf.text.pdf.BaseFont"%>
<%@page import="com.itextpdf.text.pdf.AcroFields"%>
<%@page import="com.itextpdf.text.pdf.PdfStamper"%>
<%@page import="com.itextpdf.text.pdf.PdfReader"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.comun.Utilerias"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page contentType="application/pdf" pageEncoding="UTF-8"%>
<%
    int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;

    StringBuilder sql = new StringBuilder("SELECT id,UPPER(nombre+ ' '+ap_paterno+' '+ap_materno) AS nombre_completo,email ");
    sql.append("FROM participantes WHERE asistio = 1 ");
    sql.append("AND id = ").append(id);

    Resultados rst = UtilDB.ejecutaConsulta(sql.toString());

    String archivoPlantilla = "";
    archivoPlantilla = "/plantillas/constancia.pdf";
    PdfReader reader = new PdfReader(application.getRealPath(archivoPlantilla));
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PdfStamper stamp = new PdfStamper(reader, buffer);
    AcroFields form = stamp.getAcroFields();

    BaseFont bf = BaseFont.createFont(
    BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
    PdfContentByte over = stamp.getOverContent(1);

    if (rst.next()) {
        form.setField("xNombreCompleto", rst.getString("nombre_completo"));
        BarcodeQRCode qrcode = new BarcodeQRCode("http://www.uttab.edu.mx/carreraAtletica/jsp/prn_constancia_participacion.jsp?id="+id, 100, 100, null);
        Image qrcodeImage = qrcode.getImage();
        qrcodeImage.setAbsolutePosition(25, 25);
        qrcodeImage.scalePercent(100);
        over.addImage(qrcodeImage);
    } else {
        over.beginText();
        over.setFontAndSize(bf, 24);
        over.setTextMatrix(100, 500);
        over.showText("No existe información de este participante");
        over.endText();

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