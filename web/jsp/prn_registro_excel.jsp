<%-- 
    Document   : prn_registro_excel
    Created on : 07-jun-2016, 15:25:56
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="carrera.atletica.comun.Resultados"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.comun.Utilerias"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.apache.poi.ss.usermodel.Workbook"%>
<%@page import="org.apache.poi.ss.usermodel.Cell"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import="org.apache.poi.hssf.util.CellRangeAddress"%>
<%@page import="org.apache.poi.ss.usermodel.CellStyle"%>
<%@page import="org.apache.poi.ss.usermodel.Row"%>
<%@page import="org.apache.poi.ss.usermodel.Font"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@ page contentType="application/vnd.ms-excelt" pageEncoding="UTF-8" session="false"%>
<%

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    try {

        HSSFWorkbook libro = getExcel();
        libro.write(buffer);
        response.setContentType("application/vnd.ms-excel");
        response.setContentLength(buffer.size());
        buffer.writeTo(response.getOutputStream());
        response.flushBuffer();
        buffer.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<%!
    public HSSFWorkbook getExcel() {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Carrera atlética XXV aniversario CGUTyP");
        int num_row = 0;

        try {

            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 24);
            font.setFontName("Trebuchet MS");

            Row row = sheet.createRow(num_row);
            num_row++;
            num_row++;
            createCell(wb, row, "Reporte carrera atlética XXV aniversario CGUTyP", font, (short) 0, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);

            sheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    7 //last column  (0-based)
            ));

            Font font2 = wb.createFont();
            font2.setFontHeightInPoints((short) 14);
            font2.setFontName("Trebuchet MS");

            Row row2 = sheet.createRow(num_row);
            num_row++;

            createCell(wb, row2, "Fecha de impresión: " + Utilerias.getCadenaFechaHora(Calendar.getInstance()), font2, (short) 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);

            sheet.addMergedRegion(new CellRangeAddress(
                    2, //first row (0-based)
                    2, //last row  (0-based)
                    0, //first column (0-based)
                    7 //last column  (0-based)
            ));

            StringBuilder sql = new StringBuilder("SELECT id, (nombre + ' '+ ap_paterno+' '+ap_materno) AS nombre_completo, fecha_nacimiento, sexo,");
            sql.append("\"categoria\" = CASE WHEN categoria =  1 THEN '3KM CARRERA' WHEN categoria = 2 THEN '3KM CAMINATA' ELSE 'No definido' END, numero_competidor, email,fecha_registro,asistio,activo ");
            sql.append("FROM participantes ORDER BY fecha_registro DESC");

            Resultados rst = UtilDB.ejecutaConsulta(sql.toString());

            setInfo(sheet, rst, font2, ++num_row);

            rst.close();

        } catch (Exception e) {
        }
        return wb;
    }

    private static void createCell(Workbook wb, Row row, String value, Font font, short column, short halign, short valign) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    private void setInfo(HSSFSheet sheet, Resultados rst, Font font, int num_row) {
        Font f1 = sheet.getWorkbook().createFont();
        f1.setFontHeightInPoints((short) 14);
        f1.setFontName("Trebuchet MS");
        Font f2 = sheet.getWorkbook().createFont();
        f2.setFontHeightInPoints((short) 14);
        f2.setFontName("Trebuchet MS");

        HSSFRow myRow = null;
        int count = 0;
        final String[] errorSource = {"Id", "Nombre completo", "Fecha de nacimiento", "Sexo", "Categoría", "Número de competidor", "Email", "Asistió"};
        try {
            Row header = sheet.createRow(num_row);
            num_row++;
            for (int i = 0; i < errorSource.length; i++) {
                //Cell monthCell = header.createCell(i);
                //monthCell.setCellValue(errorSource[i]);
                createCell(sheet.getWorkbook(), header, errorSource[i], f1, (short) i, CellStyle.ALIGN_CENTER, CellStyle.VERTICAL_CENTER);
            }
            while (rst.next()) {
                myRow = sheet.createRow(num_row);
                num_row++;
                createCell(sheet.getWorkbook(), myRow, String.valueOf(rst.getInt("id")), f2, (short) 0, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, rst.getString("nombre_completo"), f2, (short) 1, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, Utilerias.getCadenaFecha(rst.getCalendar("fecha_nacimiento")), f2, (short) 2, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, rst.getString("sexo"), f2, (short) 3, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, rst.getString("categoria"), f2, (short) 4, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, rst.getString("numero_competidor"), f2, (short) 5, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, rst.getString("email"), f2, (short) 6, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                //createCell(sheet.getWorkbook(), myRow, Utilerias.getCadenaFecha(rst.getCalendar("fecha_registro")), f2, (short) 7, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
                createCell(sheet.getWorkbook(), myRow, (rst.getBoolean("asistio") ? "Si":"No"), f2, (short) 7, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_CENTER);
            }

            HSSFRow row = sheet.getWorkbook().getSheetAt(0).getRow(4);
            for (int colNum = 0; colNum < row.getLastCellNum(); colNum++) {
                sheet.getWorkbook().getSheetAt(0).autoSizeColumn(colNum);
            }

        } catch (Exception e) {
        }

    }
%>


