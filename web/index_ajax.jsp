<%-- 
    Document   : index_ajax
    Created on : 06-jun-2016, 13:18:31
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page import="carrera.atletica.comun.ErrorSistema"%>
<%@page import="carrera.atletica.proceso.Codigo"%>
<%@page import="carrera.atletica.proceso.Participante"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Vector"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="carrera.atletica.comun.UtilDB"%>
<%@page import="carrera.atletica.comun.Utilerias"%>
<%@page import="carrera.atletica.comun.Resultados"%>
<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%

    String xAccion = request.getParameter("xAccion") != null ? request.getParameter("xAccion") : "";
    //String codigo = request.getParameter("xCodigo") != null ? request.getParameter("xCodigo") : "";
    String sql = "";
    String resultado = "";
    int xCvePersona = 0;
    Resultados rst = null;

    if (!xAccion.equals("")) {

        /*if (xAccion.equals("busqueda")) {
         sql = new StringBuilder("SELECT c.id, c.codigo,(p.nombre + ' ' + p.ap_paterno + ' '+ p.ap_materno) AS nombre FROM codigos AS c ");
         sql.append("LEFT JOIN participantes AS p ON p.CODIGO_ID = c.ID ");
         sql.append("WHERE c.CODIGO = ").append(Utilerias.CadenaEncomillada(codigo));

         rst = UtilDB.ejecutaConsulta(sql.toString());
         if (rst.recordCount() > 0) {

         if (rst.next()) {
         if (rst.getString("nombre").equals("")) {
         resultado = "{\"resultado\":1,\"mensaje\":\"Código valido\",\"codigoId\":" + rst.getInt("id") + "}";
         } else {
         resultado = "{\"resultado\":0,\"mensaje\":\"Ya se registro anteriormente, reimprimir ficha\"}";
         }
         }

         } else {
         resultado = "{\"resultado\":0,\"mensaje\":\"Código inválido\"}";
         }

         rst.close();
         out.println(resultado);
         out.flush();
         return;
         } else*/ if (xAccion.equals("grabar")) {
            JSONObject jsonObj;
            JSONParser parser = new JSONParser();

            Object obj = parser.parse(request.getParameter("datos"));
            jsonObj = (JSONObject) obj;

            xCvePersona = Integer.parseInt((String) jsonObj.get("xCvePersona"));
            sql = "SELECT TOP 1 * FROM participantes WHERE cve_persona= " + xCvePersona+" ORDER BY FECHA_REGISTRO DESC";
            rst = UtilDB.ejecutaConsulta(sql);

            if (rst.recordCount() == 0 || xCvePersona == 0) {
                Calendar xFechaNacimiento = Calendar.getInstance();

                Participante p = new Participante();

                p.setCvePersona(xCvePersona);
                p.setNombre((String) jsonObj.get("txtNombre"));
                p.setApPaterno((String) jsonObj.get("txtPaterno"));
                p.setApMaterno((String) jsonObj.get("txtMaterno"));

                Vector fn = Utilerias.split("/", (String) jsonObj.get("txtFechaNacimiento"));
                xFechaNacimiento.set(Integer.parseInt((String) fn.elementAt(2)),
                        Integer.parseInt((String) fn.elementAt(1)) - 1,
                        Integer.parseInt((String) fn.elementAt(0)));

                p.setFechaNacimiento(xFechaNacimiento);
                p.setSexo((String) jsonObj.get("rdSexo"));
                p.setCategoria(Integer.parseInt((String) jsonObj.get("cmbCategoria")));
                //p.setNumeroCompetidor();
                p.setEmail((String) jsonObj.get("txtEmail"));
                p.setActivo(true);

                ErrorSistema err = p.grabar();

                if (err.getNumeroError() != 0) {
                    resultado = "{\"resultado\":0,\"mensaje\":\"Registro no realizado\"}";
                } else {
                    resultado = "{\"resultado\":1,\"mensaje\":\"Registro realizado con éxito\",\"participante_id\":" + p.getId() + "}";
                }
            } else {
                if(rst.next())
                { resultado = "{\"resultado\":0,\"mensaje\":\"Lo sentimos, ya se ha registrado anteriormente el "+Utilerias.getCadenaFechaHora(rst.getCalendar("fecha_registro"))+", puede reimprimir su hoja de confirmación <a href='jsp/prn_registro_pdf.jsp?id="+rst.getInt("id")+"' target='_blank'>aquí</a>\"}";}
                
            }
            rst.close();
            out.println(resultado);
            out.flush();
            return;

        } else {
            resultado = "{\"resultado\":0,\"mensaje\":\"Acción no valida\"}";
            out.println(resultado);
            out.flush();
            return;
        }
    }
%>