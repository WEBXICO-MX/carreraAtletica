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
    archivoPlantilla = "/plantillas/plantilla2.pdf";
    PdfReader reader = new PdfReader(application.getRealPath(archivoPlantilla));
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PdfStamper stamp = new PdfStamper(reader, buffer);
    AcroFields form = stamp.getAcroFields();

    BaseFont bf = BaseFont.createFont(
            BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

    if (rst.next()) {
        form.setField("txtNombreCompleto", rst.getString("nombre_completo"));
    }
    rst.close();

    for (int i = 1; i <= reader.getNumberOfPages(); i++) {

        PdfContentByte over = stamp.getOverContent(i);

        /*over.beginText();
         over.setFontAndSize(bf, 10);
         over.setTextMatrix(107, 740);
         over.showText("I can write at page " + i);
         over.endText();*/

        /*over.setRGBColorStroke(0xFF, 0x00, 0x00);
         over.setLineWidth(5f);
         over.ellipse(250, 450, 350, 550);
         over.stroke();*/
        BarcodeQRCode qrcode = new BarcodeQRCode("http://www.uttab.edu.mx/carreraAtletica/jsp/prn_constancia_participacion?id="+id, 100, 100, null);
        Image qrcodeImage = qrcode.getImage();
        qrcodeImage.setAbsolutePosition(100, 100);
        qrcodeImage.scalePercent(100);
        over.addImage(qrcodeImage);

    }

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