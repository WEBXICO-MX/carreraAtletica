<%-- 
    Document   : prn_constancia_participacion
    Created on : 27-jun-2016, 13:35:00
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="java.awt.Color"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.IOException"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="java.nio.file.Files"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.itextpdf.text.Image"%>
<%@page import="com.itextpdf.text.pdf.BarcodeQRCode"%>
<%@page import="com.itextpdf.text.pdf.PdfContentByte"%>
<%@page import="com.itextpdf.text.pdf.BaseFont"%>
<%@page import="com.itextpdf.text.pdf.AcroFields"%>
<%@page import="com.itextpdf.text.pdf.PdfStamper"%>
<%@page import="com.itextpdf.text.pdf.PdfReader"%>
<%@page contentType="application/pdf" pageEncoding="UTF-8" session="false"%>
<%
    int id = request.getParameter("id") != null ? Integer.parseInt(request.getParameter("id")) : 0;
    String file = request.getSession().getServletContext().getRealPath("WEB-INF/participantes.txt");
    String nombre_completo = "";
    String[] mtz = new String[3];
    List<String> list = new ArrayList<>();

    try (BufferedReader reader = Files.newBufferedReader(Paths.get(file))) {
        list = reader.lines().collect(Collectors.toList());
        
        for (String line : list) {
            mtz = line.split(",");

            if (Integer.parseInt(mtz[0]) == id) {
                nombre_completo = mtz[1];
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    String archivoPlantilla = "";
    archivoPlantilla = "/plantillas/constancia2.pdf";
    PdfReader reader = new PdfReader(application.getRealPath(archivoPlantilla));
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PdfStamper stamp = new PdfStamper(reader, buffer);
    AcroFields form = stamp.getAcroFields();

    BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
    PdfContentByte over = stamp.getOverContent(1);

    if (!nombre_completo.equals("")) {
        form.setField("xNombreCompleto", nombre_completo);
        BarcodeQRCode qrcode = new BarcodeQRCode("http://www.uttab.edu.mx/carreraAtletica/jsp/prn_constancia_participacion.jsp?id=" + id, 100, 100, null);
        Image qrcodeImage = qrcode.getImage();
        qrcodeImage.setAbsolutePosition(25, 25);
        qrcodeImage.scalePercent(100);
        over.addImage(qrcodeImage);
    } else {
        over.beginText();
        over.setFontAndSize(bf, 24);
        over.setTextMatrix(250, 325);
        over.showText("No existe información de este participante");
        over.endText();

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
