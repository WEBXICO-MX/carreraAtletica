package carrera.atletica.comun;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

public final class Utilerias {

    static final char ESCAPE = 'ꖥ';
    static final byte ESCAPE_BYTE = -91;
    static final char[] HEX_DIGIT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String[] mes = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    //Cadena para no andar restando al obeneter el mes
    public static String[] mesU = {"", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    static final String[] html = {"<!DOCTYPE", "<p", "</p>", "<a", "</a>", "<img", "<script", "</script>", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "</h1>", "</h2>", "</h3>", "</h4>", "</h5>", "</h6>", "<iframe", "</iframe>", "<frame", "</frame>", "<marquee", "</marquee>", "<object", "</object>", "<table", "</table>", "<thead", "</thead>", "<tbody", "</tbody>", "<tfoot", "</tfoot>", "<td", "</td>", "<tr", "</tr>", "<div", "</div>", "<input", "<textarea", "</textarea>", "<select", "</select>", "<option", "</option>", "<html", "</html>", "<head>", "</head>", "<meta", "<title>", "</title>", "<link", "<body", "</body>", "<form", "</form>", "<br", "<b>", "<em>", "<i>", "<small>", "<strong>", "<sub>", "<ul", "</ul>", "<li", "</li>", "<ol", "</ol>", "<!--", "-->", "<![endif]-->", "<span", "</span>"};
    static final String[] sql_LMD = {"INSERT", "DELETE", "UPDATE", "SELECT", ";INSERT", ";DELETE", ";UPDATE", ";SELECT"};

    public static String[] getMeses() {
        return mesU;
    }

    public static boolean tieneHTML(String valor) {
        boolean tieneHtml = false;
        String cadena = valor.replaceAll("\\s+", "");
        cadena = cadena.toLowerCase();
        for (String html1 : html) {
            if (cadena.contains(html1)) {
                tieneHtml = true;
                return tieneHtml;
            }
        }
        return tieneHtml;
    }

    public static boolean tieneSQL(String valor) {
        boolean tieneSQL = false;
        String cadena = valor.toUpperCase();
        String mtz_cadena[] = cadena.split(" ");
        for (String mtz : mtz_cadena) {
            for (String sql : sql_LMD) {
                if (mtz.equals(sql)) {
                    tieneSQL = true;
                    return tieneSQL;
                }
                if (mtz.contains(sql)) {
                    tieneSQL = true;
                    return tieneSQL;
                }
            }
        }
        return tieneSQL;
    }

    public static String CadenaEncomillada(String valor) {
        String cad = valor;
        for (int i = valor.length() - 1; i >= 0; i--) {
            if (valor.substring(i, i + 1).equals("'")) {
                if (i == 0) {
                    cad = "'".concat(String.valueOf(String.valueOf(cad)));
                } else {
                    cad = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(cad.substring(0, i)))).append("'").append(cad.substring(i, cad.length()))));
                }
            }
        }
        return new String(String.valueOf(String.valueOf(new StringBuffer("'").append(cad).append("'"))));
    }

    public static String CadenaEncomilladaLike(String valor) {
        String cad = valor;
        for (int i = valor.length() - 1; i >= 0; i--) {
            if (valor.substring(i, i + 1).equals("'")) {
                if (i == 0) {
                    cad = "'".concat(String.valueOf(String.valueOf(cad)));
                } else {
                    cad = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(cad.substring(0, i)))).append("'").append(cad.substring(i, cad.length()))));
                }
            }
        }
        return new String(String.valueOf(String.valueOf(new StringBuffer("'%").append(cad).append("%'"))));
    }

    public static String CantidadEnLetra(double Cantidad) {
        DecimalFormat nf = new DecimalFormat("00");
        double cantidad = 0.0D;
        double centavos = 0.0D;
        int digito = 0;
        int primerDigito = 0;
        int segundoDigito = 0;
        int tercerDigito = 0;
        int numeroBloques = 0;
        int bloqueCero = 0;
        String bloque = "";
        String cantidadEnLetra = "";
        Cantidad = Math.round(Cantidad * Math.pow(10.0D, 2.0D)) / Math.pow(10.0D, 2.0D);
        cantidad = Math.floor(Cantidad);
        centavos = (Cantidad - cantidad) * 100.0D;
        String[] Unidades = {"UN", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUN", "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", "VEINTICINCO", "VEINTISEIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE"};

        String[] Decenas = {"DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"};

        String[] Centenas = {"CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"};

        numeroBloques = 1;
        do {
            primerDigito = 0;
            segundoDigito = 0;
            tercerDigito = 0;
            bloque = "";
            bloqueCero = 0;
            int i = 1;

            while (i <= 3) {
                digito = (int) cantidad % 10;
                if (digito != 0) {
                    switch (i) {
                        case 1:
                            bloque = " ".concat(String.valueOf(String.valueOf(Unidades[(digito - 1)])));
                            primerDigito = digito;
                            break;
                        case 2:
                            if (digito <= 2) {
                                bloque = " ".concat(String.valueOf(String.valueOf(Unidades[(digito * 10 + primerDigito - 1)])));
                            } else {
                                bloque = String.valueOf(String.valueOf(new StringBuffer(" ").append(Decenas[(digito - 1)]).append(primerDigito == 0 ? " " : " Y").append(bloque)));
                            }
                            segundoDigito = digito;
                            break;
                        case 3:
                            bloque = String.valueOf(String.valueOf(new StringBuffer(" ").append((digito != 1) || (primerDigito != 0) || (segundoDigito != 0) ? Centenas[(digito - 1)] : "CIEN ").append(bloque)));
                            tercerDigito = digito;
                    }
                } else {
                    bloqueCero++;
                }
                cantidad = Math.floor(cantidad / 10.0D);
                if (cantidad == 0.0D) {
                    break;
                }
                i++;
            }
            switch (numeroBloques) {
                case 1:
                    cantidadEnLetra = bloque;
                    break;
                case 2:
                    cantidadEnLetra = String.valueOf(new StringBuffer(String.valueOf(bloque)).append(bloqueCero != 3 ? " MIL" : " ").append(cantidadEnLetra));
                    break;
                case 3:
                    cantidadEnLetra = String.valueOf(new StringBuffer(String.valueOf(bloque)).append((primerDigito != 1) || (segundoDigito != 0) || (tercerDigito != 0) ? " MILLONES" : " MILLON").append(cantidadEnLetra));
            }

            numeroBloques++;
        } while (cantidad != 0.0D);
        return String.valueOf(new StringBuffer("(").append(cantidadEnLetra).append(" PESOS ").append(nf.format(centavos)).append("/100 M.N. )"));
    }

    public static int ComparaFechas(Calendar fecha1, Calendar fecha2) {
        int comparador = 0;
        if (fecha1.get(1) == fecha2.get(1)) {
            comparador = 0;
        } else if (fecha1.get(1) > fecha2.get(1)) {
            comparador = 1;
        } else if (fecha1.get(1) < fecha2.get(1)) {
            comparador = 2;
        }
        if (comparador == 0) {
            if (fecha1.get(2) == fecha2.get(2)) {
                comparador = 0;
            } else if (fecha1.get(2) > fecha2.get(2)) {
                comparador = 1;
            } else if (fecha1.get(2) < fecha2.get(2)) {
                comparador = 2;
            }
        }
        if (comparador == 0) {
            if (fecha1.get(5) == fecha2.get(5)) {
                comparador = 0;
            } else if (fecha1.get(5) > fecha2.get(5)) {
                comparador = 1;
            } else if (fecha1.get(5) < fecha2.get(5)) {
                comparador = 2;
            }
        }
        return comparador;
    }

    public static String DesencriptarContrasena(String contrasena) {
        String original = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz1234567890!$%&/()=?¿¡,.-;:_ ";
        String encriptada = "Uqyh.-aJ,g4TPVDE/2WZ15uBC78b3X:_ 6AOHIYrstNFGvcjñzÑdef)=?¿¡omKL90!(i$%&QRklwxnMSp;";
        String temporal = "";

        for (int x = 0; x < contrasena.length(); x++) {
            int y = 0;

            while (y < encriptada.length()) {
                char xContrasena = contrasena.charAt(x);
                char xOriginal = encriptada.charAt(y);
                if (xContrasena == xOriginal) {
                    if ((x + 1) % 2 == 0) {
                        temporal = String.valueOf(temporal) + String.valueOf(original.charAt(y));
                        break;
                    }
                    if (y == original.length()) {
                        temporal = String.valueOf(temporal) + String.valueOf(original.charAt(1));
                        break;
                    }
                    temporal = String.valueOf(temporal) + String.valueOf(original.charAt(y - 1));
                    break;
                }
                y++;
            }
        }

        String cadenaEncriptada = temporal;
        return cadenaEncriptada;
    }

    public static float NoredondeaCalificacion(float cal) {
        float ret = cal;
        String cad = formatoDoble(ret, 3);
        ret = Float.parseFloat(cad.substring(0, cad.length() - 2));
        return ret;
    }

    public static String NuloAVacio(String cad) {
        return cad != null ? cad.trim() : "";
    }

    static final byte[] RLEStringToByteArray(String s) {
        int length = s.charAt(0) << '\020' | s.charAt(1);
        byte[] array = new byte[length];
        boolean nextChar = true;
        char c = '\000';
        int node = 0;
        int runLength = 0;
        int i = 2;
        int ai = 0;

        while (ai < length) {
            byte b;
            if (nextChar) {
                c = s.charAt(i++);
                b = (byte) (c >> '\b');
                nextChar = false;
            } else {
                b = (byte) (c & 0xFF);
                nextChar = true;
            }
            switch (node) {
                case 0:
                    if (b == -91) {
                        node = 1;
                    } else {
                        array[(ai++)] = b;
                    }
                    break;
                case 1:
                    if (b == -91) {
                        array[(ai++)] = -91;
                        node = 0;
                    } else {
                        runLength = b;
                        if (runLength < 0) {
                            runLength += 256;
                        }
                        node = 2;
                    }
                    break;
                case 2:
                    for (int j = 0; j < runLength; j++) {
                        array[(ai++)] = b;
                    }
                    node = 0;
            }

        }

        if (node != 0) {
            throw new InternalError("Bad run-length encoded byte array");
        }
        if (i != s.length()) {
            throw new InternalError("Excess data in RLE byte array string");
        }
        return array;
    }

    static final short[] RLEStringToShortArray(String s) {
        int length = s.charAt(0) << '\020' | s.charAt(1);
        short[] array = new short[length];
        int ai = 0;
        for (int i = 2; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 42405) {
                c = s.charAt(++i);
                if (c == 42405) {
                    array[(ai++)] = ((short) c);
                    continue;
                }
                int runLength = c;
                short runValue = (short) s.charAt(++i);
                for (int j = 0; j < runLength; j++) {
                    array[(ai++)] = runValue;
                }
            } else {
                array[(ai++)] = ((short) c);
            }
        }

        if (ai != length) {
            throw new InternalError("Bad run-length encoded short array");
        }
        return array;
    }

    public static String Repetir(String cadena, int veces) {
        String res = "";
        for (int i = 0; i < veces; i++) {
            res = String.valueOf(res) + String.valueOf(cadena);
        }
        return res;
    }

    public static String TiempoDetallado(String fecha_inicial, String fecha_actual) {
        Vector ft = new Vector();
        Vector ft2 = new Vector();
        int anio = 0;
        int mes = 0;
        int dia = 0;
        int anio2 = 0;
        int mes2 = 0;
        int dia2 = 0;
        int res = 0;
        int i = 0;
        int con = 0;
        String tiempo = "";
        ft = split("/", fecha_inicial);
        anio = Integer.parseInt((String) ft.elementAt(2));
        mes = Integer.parseInt((String) ft.elementAt(1)) - 1;
        dia = Integer.parseInt((String) ft.elementAt(0));
        ft2 = split("/", fecha_actual);
        anio2 = Integer.parseInt((String) ft2.elementAt(2));
        mes2 = Integer.parseInt((String) ft2.elementAt(1)) - 1;
        dia2 = Integer.parseInt((String) ft2.elementAt(0));
        if (anio < anio2) {
            con = 0;
            for (i = anio; i <= anio2; i++) {
                con++;
            }
            if (con == 1) {
                tiempo = String.valueOf(String.valueOf(String.valueOf(tiempo))) + String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(con)).concat(" año "))));
            } else {
                tiempo = String.valueOf(String.valueOf(String.valueOf(tiempo))) + String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(con)).concat(" años "))));
            }
        }
        if (mes < mes2) {
            con = 0;
            for (i = mes; i <= mes2; i++) {
                con++;
            }
            if (con == 1) {
                tiempo = String.valueOf(String.valueOf(String.valueOf(tiempo))) + String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(con)).concat(" mes "))));
            } else {
                tiempo = String.valueOf(String.valueOf(String.valueOf(tiempo))) + String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(con)).concat(" meses "))));
            }
        }
        if (dia < dia2) {
            con = 0;
            for (i = dia; i <= dia2; i++) {
                con++;
            }
            if (con == 1) {
                tiempo = String.valueOf(String.valueOf(String.valueOf(tiempo))) + String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(con)).concat(" dia "))));
            } else {
                tiempo = String.valueOf(String.valueOf(String.valueOf(tiempo))) + String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(con)).concat(" dias "))));
            }
        }
        return tiempo;
    }

    public static String TiempoEnMes(String fecha_inicial, String fecha_final) {
        Vector ft = new Vector();
        Vector ft2 = new Vector();
        int anio = 0;
        int mes = 0;
        int dia = 0;
        int anio2 = 0;
        int mes2 = 0;
        int dia2 = 0;
        int res = 0;
        String tiempo = "";
        ft = split("/", fecha_inicial);
        anio = Integer.parseInt((String) ft.elementAt(2));
        mes = Integer.parseInt((String) ft.elementAt(1)) - 1;
        dia = Integer.parseInt((String) ft.elementAt(0));
        ft2 = split("/", fecha_final);
        anio2 = Integer.parseInt((String) ft2.elementAt(2));
        mes2 = Integer.parseInt((String) ft2.elementAt(1)) - 1;
        dia2 = Integer.parseInt((String) ft2.elementAt(0));
        if ((anio == anio2) && (mes == mes2)) {
            tiempo = "3 meses";
        }
        if ((anio == anio2) && (mes != mes2)) {
            res = mes - mes2;
            if (res <= 3) {
                tiempo = "3 meses";
            }
            if ((res > 3) && (res <= 6)) {
                tiempo = "6 meses";
            }
            if ((res > 6) && (res <= 9)) {
                tiempo = "9 meses";
            }
            if ((res > 9) && (res <= 12)) {
                tiempo = "1 año";
            }
        }
        if (anio < anio2) {
            tiempo = "Mas de un año";
        }
        return tiempo;
    }

    public static int UltimoDia(int xAno, int xMes) {
        int diasFebrero = 0;

        int dias = 0;
        if (xMes == 2) {
            String sqlFecha = "Select IsDate('29/02/" + String.valueOf(xAno) + "') as valor";
            Resultados rsFecha = UtilDB.ejecutaConsulta(sqlFecha);
            int banderaFechaValida = 0;
            while (rsFecha.next()) {
                if (rsFecha.getInt("valor") == 1) {
                    dias = 29;
                } else {
                    dias = 28;
                }
            }

        }

        if (xMes == 1) {
            dias = 31;
        }
        if (xMes == 3) {
            dias = 30;
        }
        if (xMes == 4) {
            dias = 30;
        }
        if (xMes == 5) {
            dias = 31;
        }
        if (xMes == 6) {
            dias = 30;
        }
        if (xMes == 7) {
            dias = 31;
        }
        if (xMes == 8) {
            dias = 31;
        }
        if (xMes == 9) {
            dias = 30;
        }
        if (xMes == 10) {
            dias = 31;
        }
        if (xMes == 11) {
            dias = 30;
        }
        if (xMes == 12) {
            dias = 31;
        }

        return dias;
    }

    public static String VacioACero(String cad) {
        return (cad != null) && (!cad.equals("")) ? cad.trim() : "0";
    }

    private static final void appendEncodedByte(StringBuffer buffer, byte value, byte[] state) {
        if (state[0] != 0) {
            char c = (char) (state[1] << 8 | value & 0xFF);
            buffer.append(c);
            state[0] = 0;
        } else {
            state[0] = 1;
            state[1] = value;
        }
    }

    static final boolean arrayEquals(Object source, Object target) {
        if (source == null) {
            return target == null;
        }
        if ((source instanceof Object[])) {
            return arrayEquals((Object[]) source, target);
        }
        if ((source instanceof int[])) {
            return arrayEquals((int[]) source, target);
        }
        if ((source instanceof double[])) {
            return arrayEquals((int[]) source, target);
        }
        return source.equals(target);
    }

    static final boolean arrayEquals(double[] source, Object target) {
        if (source == null) {
            return target == null;
        }
        if (!(target instanceof double[])) {
            return false;
        }

        double[] targ = (double[]) target;
        return (source.length == targ.length) && (arrayRegionMatches(source, 0, targ, 0, source.length));
    }

    static final boolean arrayEquals(int[] source, Object target) {
        if (source == null) {
            return target == null;
        }
        if (!(target instanceof int[])) {
            return false;
        }

        int[] targ = (int[]) target;
        return (source.length == targ.length) && (arrayRegionMatches(source, 0, targ, 0, source.length));
    }

    static final boolean arrayEquals(Object[] source, Object target) {
        if (source == null) {
            return target == null;
        }
        if (!(target instanceof Object[])) {
            return false;
        }

        Object[] targ = (Object[]) target;
        return (source.length == targ.length) && (arrayRegionMatches(source, 0, targ, 0, source.length));
    }

    static final boolean arrayRegionMatches(double[] source, int sourceStart, double[] target, int targetStart, int len) {
        int sourceEnd = sourceStart + len;
        int delta = targetStart - sourceStart;
        for (int i = sourceStart; i < sourceEnd; i++) {
            if (source[i] != target[(i + delta)]) {
                return false;
            }
        }
        return true;
    }

    static final boolean arrayRegionMatches(int[] source, int sourceStart, int[] target, int targetStart, int len) {
        int sourceEnd = sourceStart + len;
        int delta = targetStart - sourceStart;
        for (int i = sourceStart; i < sourceEnd; i++) {
            if (source[i] != target[(i + delta)]) {
                return false;
            }
        }
        return true;
    }

    static final boolean arrayRegionMatches(Object[] source, int sourceStart, Object[] target, int targetStart, int len) {
        int sourceEnd = sourceStart + len;
        int delta = targetStart - sourceStart;
        for (int i = sourceStart; i < sourceEnd; i++) {
            if (!arrayEquals(source[i], target[(i + delta)])) {
                return false;
            }
        }
        return true;
    }

    static final String arrayToRLEString(byte[] a) {
        StringBuffer buffer = new StringBuffer();
        buffer.append((char) (a.length >> 16));
        buffer.append((char) a.length);
        byte runValue = a[0];
        int runLength = 1;
        byte[] state = new byte[2];
        for (int i = 1; i < a.length; i++) {
            byte b = a[i];
            if ((b == runValue) && (runLength < 255)) {
                runLength++;
            } else {
                encodeRun(buffer, runValue, runLength, state);
                runValue = b;
                runLength = 1;
            }
        }

        encodeRun(buffer, runValue, runLength, state);
        if (state[0] != 0) {
            appendEncodedByte(buffer, (byte) 0, state);
        }
        return buffer.toString();
    }

    static final String arrayToRLEString(short[] a) {
        StringBuffer buffer = new StringBuffer();
        buffer.append((char) (a.length >> 16));
        buffer.append((char) a.length);
        short runValue = a[0];
        int runLength = 1;
        for (int i = 1; i < a.length; i++) {
            short s = a[i];
            if ((s == runValue) && (runLength < 65535)) {
                runLength++;
            } else {
                encodeRun(buffer, runValue, runLength);
                runValue = s;
                runLength = 1;
            }
        }

        encodeRun(buffer, runValue, runLength);
        return buffer.toString();
    }

    public static String cargaEjercicioDefault() {
        Resultados res = new Resultados();
        String sql = "SELECT base FROM ejercicios.siap.ejercicio WHERE predeterminado=1";
        res = UtilDB.ejecutaConsulta(sql);
        if (res.recordCount() > 0) {
            res.next();
            return res.getString("base");
        }

        return "";
    }

    public static String convertirMinutos(int minutos) {
        String ret = "";
        int dia = minutos / 1440;
        int hor = (minutos - dia * 24 * 60) / 60;
        int min = minutos - dia * 24 * 60 - hor * 60;
        String xdia = String.valueOf(dia);
        String xhor = rellenarCeros(String.valueOf(hor), 2);
        String xmin = rellenarCeros(String.valueOf(min), 2);
        String tiempo = "";
        if (dia > 0) {
            tiempo = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tiempo))).append(xdia).append("D").append(xhor).append(":").append(xmin)));
        } else if (hor > 0) {
            tiempo = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tiempo))).append(xhor).append(":").append(xmin)));
        } else {
            tiempo = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(tiempo))).append("00:").append(xmin)));
        }
        ret = tiempo;
        return ret;
    }

    private static final void encodeRun(StringBuffer buffer, byte value, int length, byte[] state) {
        if (length < 4) {
            for (int j = 0; j < length; j++) {
                if (value == -91) {
                    appendEncodedByte(buffer, (byte) -91, state);
                }
                appendEncodedByte(buffer, value, state);
            }
        } else {
            if (length == -91) {
                if (value == -91) {
                    appendEncodedByte(buffer, (byte) -91, state);
                }
                appendEncodedByte(buffer, value, state);
                length--;
            }
            appendEncodedByte(buffer, (byte) -91, state);
            appendEncodedByte(buffer, (byte) length, state);
            appendEncodedByte(buffer, value, state);
        }
    }

    private static final void encodeRun(StringBuffer buffer, short value, int length) {
        if (length < 4) {
            for (int j = 0; j < length; j++) {
                if (value == 42405) {
                    buffer.append(42405);
                }
                buffer.append((char) value);
            }
        } else {
            if (length == 42405) {
                if (value == 42405) {
                    buffer.append(42405);
                }
                buffer.append((char) value);
                length--;
            }
            buffer.append(42405);
            buffer.append((char) length);
            buffer.append((char) value);
        }
    }

    static final String formatForSource(String s) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < s.length(); buffer.append('"')) {
            if (i > 0) {
                buffer.append("+\n");
            }
            buffer.append("        \"");
            for (int count = 11; (i < s.length()) && (count < 80);) {
                char c = s.charAt(i++);
                if ((c < ' ') || (c == '"')) {
                    buffer.append('\\');
                    buffer.append(HEX_DIGIT[((c & 0x1C0) >> '\006')]);
                    buffer.append(HEX_DIGIT[((c & 0x38) >> '\003')]);
                    buffer.append(HEX_DIGIT[(c & 0x7)]);
                    count += 4;
                } else if (c <= '~') {
                    buffer.append(c);
                    count++;
                } else {
                    buffer.append("\\u");
                    buffer.append(HEX_DIGIT[((c & 0xF000) >> '\f')]);
                    buffer.append(HEX_DIGIT[((c & 0xF00) >> '\b')]);
                    buffer.append(HEX_DIGIT[((c & 0xF0) >> '\004')]);
                    buffer.append(HEX_DIGIT[(c & 0xF)]);
                    count += 6;
                }
            }

        }

        return buffer.toString();
    }

    public static String formatoCardinales(int valor) {
        String letras = "";
        if (valor == 1) {
            letras = "Primer";
        }
        if (valor == 2) {
            letras = "Segundo";
        }
        if (valor == 3) {
            letras = "Tercer";
        }
        if (valor == 4) {
            letras = "Cuarto";
        }
        if (valor == 5) {
            letras = "Quinto";
        }
        if (valor == 6) {
            letras = "Sexto";
        }
        if (valor == 7) {
            letras = "Septimo";
        }
        if (valor == 8) {
            letras = "Octavo";
        }
        if (valor == 9) {
            letras = "Noveno";
        }
        if (valor == 10) {
            letras = "Decimo";
        }
        return letras;
    }

    public static String formatoCardinalesCortos(int valor) {
        String letras = "";
        if (valor == 1) {
            letras = "1er";
        }
        if (valor == 2) {
            letras = "2do";
        }
        if (valor == 3) {
            letras = "3er";
        }
        if (valor == 4) {
            letras = "4to";
        }
        if (valor == 5) {
            letras = "5to";
        }
        if (valor == 6) {
            letras = "6to";
        }
        if (valor == 7) {
            letras = "7mo";
        }
        if (valor == 8) {
            letras = "8vo";
        }
        if (valor == 9) {
            letras = "9no";
        }
        if (valor == 10) {
            letras = "10mo";
        }
        return letras;
    }

    public static String formatoCurrency(double valor) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(valor);
    }

    public static String formatoDoble(double valor, int digitos) {
        String pattern = "0000000000";
        DecimalFormat nf = null;
        if (digitos > 0) {
            nf = new DecimalFormat("###,###,##0.".concat(String.valueOf(String.valueOf(pattern.substring(0, digitos)))));
        } else {
            nf = new DecimalFormat("###,###,##0");
        }
        return nf.format(valor);
    }

    public static String formatoDoble(double valor) {
        DecimalFormat nf = new DecimalFormat("###,###,##0.00");
        return nf.format(valor);
    }

    public static String formatoDobleEntero(double valor) {
        DecimalFormat nf = new DecimalFormat("###,###,##0");
        return nf.format(valor);
    }

    public static String formatoFechaDB(Calendar fecha) {
        if (fecha != null) {
            return String.valueOf(String.valueOf(new StringBuffer("{ts '").append(String.valueOf(fecha.get(1))).append("-").append(rellenarCeros(String.valueOf(fecha.get(2) + 1), 2)).append("-").append(rellenarCeros(String.valueOf(fecha.get(5)), 2)).append(" ").append(rellenarCeros(String.valueOf(fecha.get(11)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(12)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(13)), 2)).append("'}")));
        }
        return "null";
    }

    public static String formatoFechaDBSimple(Calendar fecha) {
        if (fecha != null) {
            return String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(String.valueOf(fecha.get(1))))).append("-").append(rellenarCeros(String.valueOf(fecha.get(2) + 1), 2)).append("-").append(rellenarCeros(String.valueOf(fecha.get(5)), 2)).append(" ").append(rellenarCeros(String.valueOf(fecha.get(11)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(12)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(13)), 2))));
        }
        return "null";
    }

    public static String formatoLetras(int valor) {
        String letras = "";
        if (valor == 0) {
            letras = "Cero";
        }
        if (valor == 1) {
            letras = "Uno";
        }
        if (valor == 2) {
            letras = "Dos";
        }
        if (valor == 3) {
            letras = "Tres";
        }
        if (valor == 4) {
            letras = "Cuatro";
        }
        if (valor == 5) {
            letras = "Cinco";
        }
        if (valor == 6) {
            letras = "Seis";
        }
        if (valor == 7) {
            letras = "Siete";
        }
        if (valor == 8) {
            letras = "Ocho";
        }
        if (valor == 9) {
            letras = "Nueve";
        }
        if (valor == 10) {
            letras = "Diez";
        }
        return letras;
    }

    public static String formatoLetras2(int valor) {
        int digito = 0;
        int primerDigito = 0;
        int segundoDigito = 0;
        int tercerDigito = 0;
        int numeroBloques = 0;
        int bloqueCero = 0;
        String bloque = "";
        String cantidadEnLetra = valor != 0 ? "" : "CERO";
        String[] Unidades = {"UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISEIS", "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO", "VEINTIDOS", "VEINTITRES", "VEINTICUATRO", "VEINTICINCO", "VEINTISEIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE"};

        String[] Decenas = {"DIEZ", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA", "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"};

        String[] Centenas = {"CIEN", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS", "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"};

        numeroBloques = 1;
        if (valor != 0) {
            do {
                primerDigito = 0;
                segundoDigito = 0;
                tercerDigito = 0;
                bloque = "";
                bloqueCero = 0;
                int i = 1;

                while (i <= 3) {
                    digito = valor % 10;
                    if (digito != 0) {
                        switch (i) {
                            case 1:
                                bloque = " ".concat(String.valueOf(String.valueOf(Unidades[(digito - 1)])));
                                primerDigito = digito;
                                break;
                            case 2:
                                if (digito <= 2) {
                                    bloque = " ".concat(String.valueOf(String.valueOf(Unidades[(digito * 10 + primerDigito - 1)])));
                                } else {
                                    bloque = String.valueOf(String.valueOf(new StringBuffer(" ").append(Decenas[(digito - 1)]).append(primerDigito == 0 ? " " : " Y").append(bloque)));
                                }
                                segundoDigito = digito;
                                break;
                            case 3:
                                bloque = String.valueOf(String.valueOf(new StringBuffer(" ").append((digito != 1) || (primerDigito != 0) || (segundoDigito != 0) ? Centenas[(digito - 1)] : "CIEN ").append(bloque)));
                                tercerDigito = digito;
                        }
                    } else {
                        bloqueCero++;
                    }
                    valor = (int) Math.floor(valor / 10);
                    if (valor == 0) {
                        break;
                    }
                    i++;
                }
                switch (numeroBloques) {
                    case 1:
                        cantidadEnLetra = bloque;
                        break;
                    case 2:
                        cantidadEnLetra = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(bloque))).append(bloqueCero != 3 ? " MIL" : " ").append(cantidadEnLetra)));
                        break;
                    case 3:
                        cantidadEnLetra = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(bloque))).append((primerDigito != 1) || (segundoDigito != 0) || (tercerDigito != 0) ? " MILLONES" : " MILLON").append(cantidadEnLetra)));
                }

                numeroBloques++;
            } while (valor != 0);
        }
        return cantidadEnLetra;
    }

    public static String getCadenaFecha(Calendar fecha) {
        String res = "";
        res = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(rellenarCeros(String.valueOf(fecha.get(5)), 2)))).append("/").append(rellenarCeros(String.valueOf(fecha.get(2) + 1), 2)).append("/").append(String.valueOf(fecha.get(1)))));
        return res;
    }

    public static String getCadenaFechaGuiones(Calendar fecha) {
        String res = "";
        res = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(rellenarCeros(String.valueOf(fecha.get(5)), 2)))).append("-").append(rellenarCeros(String.valueOf(fecha.get(2) + 1), 2)).append("-").append(String.valueOf(fecha.get(1)))));
        return res;
    }

    public static String getCadenaFechaAlreves(Calendar fecha) {
        String res = "";
        res = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(String.valueOf(fecha.get(1))))).append(rellenarCeros(String.valueOf(fecha.get(2) + 1), 2)).append(rellenarCeros(String.valueOf(fecha.get(5)), 2))));
        return res;
    }

    public static String getCadenaFechaCompletaCorta(Calendar fecha) {
        String res = "";
        String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        res = String.valueOf(fecha.get(5)) + " de " + meses[fecha.get(2)] + " de " + String.valueOf(fecha.get(1));

        return res;
    }

    public static String getCadenaFechaHora(Calendar fecha) {
        String res = "";
        res = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(rellenarCeros(String.valueOf(fecha.get(5)), 2)))).append("/").append(rellenarCeros(String.valueOf(fecha.get(2) + 1), 2)).append("/").append(String.valueOf(fecha.get(1))).append(" ").append(rellenarCeros(String.valueOf(fecha.get(11)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(12)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(13)), 2))));
        return res;
    }

    public static String getCadenaFechaLarga(Calendar fecha) {
        String res = "";
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        res = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(String.valueOf(fecha.get(5))))).append(" de ").append(meses[fecha.get(2)]).append(" de ").append(String.valueOf(fecha.get(1)))));
        return res;
    }

    public static String getCadenaFechaMes(Calendar fecha) {
        String res = "";
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        res = meses[fecha.get(2)];
        return res;
    }

    public static String getCadenaHora(Calendar fecha) {
        String res = "";
        res = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(rellenarCeros(String.valueOf(fecha.get(11)), 2)))).append(":").append(rellenarCeros(String.valueOf(fecha.get(12)), 2)).append(":").append(rellenarCeros(String.valueOf(fecha.get(13)), 2))));
        return res;
    }

    public static String getFechaAno(Calendar fecha) {
        String res = "";
        res = String.valueOf(fecha.get(1));
        return res;
    }

    public static String getFechaDia(Calendar fecha) {
        String res = "";
        res = String.valueOf(fecha.get(5));
        return res;
    }

    public static String getFechaHoras(Calendar fecha) {
        String res = "";
        res = String.valueOf(fecha.get(11));
        return res;
    }

    public static String getFechaMes(Calendar fecha) {
        String res = "";
        res = String.valueOf(fecha.get(2) + 1);
        return res;
    }

    public static String getFechaMinutos(Calendar fecha) {
        String res = "";
        res = String.valueOf(fecha.get(12));
        return res;
    }

    public static String getFechaSegundos(Calendar fecha) {
        String res = "";
        res = String.valueOf(fecha.get(13));
        return res;
    }

    public static String getMes(int xMes) {
        return mes[xMes];
    }

    public static String getMes(Calendar cal) {
        return mes[cal.get(2)];
    }

    public static String inicialCadena(String cad) {
        char[] aux = cad.toLowerCase().toCharArray();
        StringBuffer c = new StringBuffer();
        boolean inicial = true;
        for (int i = 0; i < aux.length; i++) {
            if (inicial) {
                if (!String.valueOf(aux[i]).equals(" ")) {
                    c.append(String.valueOf(aux[i]).toUpperCase());
                    inicial = false;
                }
            } else {
                if (String.valueOf(aux[i]).equals(" ")) {
                    inicial = true;
                }
                c.append(aux[i]);
            }
        }
        return c.toString();
    }

    public static String inicialCadena2(String cad) {
        char[] aux = new char[1];
        String cad2 = "";
        StringBuffer c = new StringBuffer();
        int p1 = 0, p2 = 0, p3 = 0;
        Vector v = new Vector();

        v = split(" ", cad.toLowerCase());

        for (int i = 0; i < v.size(); i++) {
            cad2 = v.elementAt(i).toString();  //Copiar la palabra      
            aux = cad2.toCharArray();        //Separar la palabra a un arreglo

            //Palabras para no poner iniciales en mayúscula                            
            p1 = v.indexOf("de", i);
            p2 = v.indexOf("del", i);
            p3 = v.indexOf("y", i);

            for (int j = 0; j < aux.length; j++) {
                if (p1 != i && p2 != i && p3 != i) //Si no es la palabra a buscar en la misma posicion del vector
                {
                    if (j == 0) //Si es la primera letra, se pone en mayúscula
                    {
                        c.append(String.valueOf(aux[j]).toUpperCase());
                    } else //Si no es la primera letra queda en minúscula
                    {
                        c.append(aux[j]);
                    }
                } else //Si se encuentra la palabra a buscar en el vector, queda en minúscula     
                {
                    c.append(aux[j]);
                }
            }

            //if((v.size())
            c.append(" "); //Agregar espacio despues de cada palabra         
        }

        return c.toString();
    }

    static final boolean objectEquals(Object source, Object target) {
        if (source == null) {
            return target == null;
        }
        return source.equals(target);
    }

    public static float redondeaCalificacion(float cal) {
        float ret = cal;
        String cad = formatoDoble(ret, 3);
        ret = Float.parseFloat(cad.substring(0, cad.length() - 2));
        return ret;
    }

    public static float redondeaSalario(float cal) {
        float ret = cal;
        String cad = formatoDoble(ret, 3);
        ret = Float.parseFloat(cad.substring(0, cad.length() - 3));
        return ret;
    }

    public static double redondear(double numero, int decimales) {
        double numero_original = numero;
        String parte_entera = String.valueOf(numero_original).substring(0, String.valueOf(numero_original).indexOf("."));
        String parte_decimal = String.valueOf(numero_original).substring(String.valueOf(numero_original).indexOf(".") + 1);
        if (decimales < 1) {
            return Math.round(Double.parseDouble(String.valueOf(numero_original)));
        }
        if (parte_decimal.length() <= 1) {
            return numero_original;
        }
        int ceros = 0;
        for (int i = 0; (i < parte_decimal.length()) && (parte_decimal.charAt(i) == '0'); i++) {
            ceros++;
        }
        parte_decimal = parte_decimal.substring(ceros);
        if (decimales < ceros) {
            parte_decimal = "0";
        } else if (decimales == ceros) {
            if (Integer.parseInt(String.valueOf(parte_decimal.charAt(0))) >= 5) {
                ceros--;
                parte_decimal = "";
                for (; ceros > 0; ceros--) {
                    parte_decimal = "0".concat(String.valueOf(String.valueOf(parte_decimal)));
                }
                parte_decimal = String.valueOf(String.valueOf(parte_decimal)).concat("1");
            } else {
                parte_decimal = "0";
            }
        } else {
            while (decimales - ceros < parte_decimal.length()) {
                parte_decimal = String.valueOf(Math.round(Double.parseDouble(String.valueOf(parte_decimal)) / 10.0D));
            }
            for (; ceros > 0; ceros--) {
                parte_decimal = "0".concat(String.valueOf(String.valueOf(parte_decimal)));
            }
        }
        if ((String.valueOf(numero_original).substring(String.valueOf(numero_original).indexOf(".") + 1, String.valueOf(numero_original).indexOf(".") + 2).equals("9")) && (parte_decimal.substring(0, 1).equals("1"))) {
            return Double.parseDouble(parte_entera) + 1.0D;
        }
        return Double.parseDouble(String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(parte_entera))).append(".").append(parte_decimal))));
    }

    public static String rellenarCeros(String cad, int lng) {
        String pattern = "000000000000000000000000000000000";
        if (cad.equals("")) {
            return cad;
        }
        return String.valueOf(String.valueOf(String.valueOf(pattern.substring(0, lng - cad.length())))) + String.valueOf(String.valueOf(String.valueOf(cad)));
    }

    public static String rellenarGuiones(String cad, int lng) {
        String pattern = "----------------------------------";
        if ((cad.equals("")) || (lng == 0)) {
            return cad;
        }
        return String.valueOf(String.valueOf(String.valueOf(pattern.substring(0, lng)))) + String.valueOf(cad);
    }

    public static String sinAcentos(String dato) {
        String vocales = "áéíóúaeiou";
        int i = 0;
        String nuevacad = "";
        for (; i < dato.length(); i++) {
            dato.toLowerCase();
            if (vocales.indexOf(dato.charAt(i)) >= 0) {
                nuevacad = String.valueOf(String.valueOf(nuevacad)).concat("_");
            } else {
                nuevacad = String.valueOf(nuevacad) + String.valueOf(dato.charAt(i));
            }
        }
        return nuevacad;
    }

    public static Vector split(String pattern, String in) {
        int s1 = 0;
        int s2 = -1;
        Vector out = new Vector(30);
        while (true) {
            s2 = in.indexOf(pattern, s1);
            if (s2 != -1) {
                out.addElement(in.substring(s1, s2));
            } else {
                String _ = in.substring(s1);
                if ((_ == null) || (_.equals(""))) {
                    break;
                }
                out.addElement(_);
                break;
            }

            s1 = s2;
            s1 += pattern.length();
        }
        return out;
    }

    public static String strAcrobat(String valor) {
        String cad = valor;
        for (int i = valor.length() - 1; i >= 0; i--) {
            if ((valor.substring(i, i + 1).equals("(")) || (valor.substring(i, i + 1).equals(")")) || (valor.substring(i, i + 1).equals("\\"))) {
                if (i == 0) {
                    cad = "\\".concat(String.valueOf(String.valueOf(cad)));
                } else {
                    cad = String.valueOf(String.valueOf(new StringBuffer(String.valueOf(String.valueOf(cad.substring(0, i)))).append("\\").append(cad.substring(i, cad.length()))));
                }
            }
        }
        return cad;
    }

    public static String formatoMoneda(double valor) {
        return String.valueOf(java.text.NumberFormat.getCurrencyInstance(new Locale("es", "Mx")).format(valor));
    }

    public static int ObtenerRangoGrafica(double valor) {
        int rango = 0;
        if (valor <= 15000) {
            rango = 1000;
        } else if (valor > 15000 && valor < 100000) {
            rango = 10000;
        } else if (valor > 100000 && valor < 1000000) {
            rango = 100000;
        } else {
            rango = 500000;
        }
        return rango;

    }

    /**
     *
     * @param fecha Cadena de
     * @return
     */
    public static Calendar fechaValida(String fecha) {
        if (fecha == null) {
            return null;
        }
        Vector fv = Utilerias.split("/", fecha);
        Calendar fechaAux = Calendar.getInstance();
        fechaAux.set(Integer.parseInt((String) fv.elementAt(2)),
                Integer.parseInt((String) fv.elementAt(1)) - 1,
                Integer.parseInt((String) fv.elementAt(0)));
        return fechaAux;
    }

    public static void getImage(String ruta, HttpServletResponse r, ServletConfig gs) {
        try {
            OutputStream o = r.getOutputStream();
            ServletContext sc = gs.getServletContext();
            InputStream is = sc.getResourceAsStream(ruta);
            byte[] buf = new byte[32 * 1024];
            int nRead = 0;
            while ((nRead = is.read(buf)) != -1) {
                o.write(buf, 0, nRead);
            }
            o.flush();
            o.close();
        } catch (IOException e) {
        }
    }
}
