/**
 *
 * @author Roberto Eder Weiss Juárez
 * @see {@link http://webxico.blogspot.mx/}
 */
package carrera.atletica.proceso;

import carrera.atletica.comun.ErrorSistema;
import carrera.atletica.comun.Resultados;
import carrera.atletica.comun.UtilDB;
import carrera.atletica.comun.Utilerias;
import java.util.Calendar;

public class Participante {

    private int id;
    private int cvePersona;
    private int cveTipoPersona;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private Calendar fechaNacimiento;
    private String sexo;
    private int categoria;
    private String numeroCompetidor;
    private String email;
    private Calendar fechaRegistro;
    private boolean asistio;
    private boolean activo;
    private boolean _existe;

    public Participante() {
        this.limpiar();
    }

    public Participante(int id) {
        this.limpiar();
        this.id = id;
        this.cargar();
    }

    private void limpiar() {
        this.id = 0;
        this.cvePersona = 0;
        this.cveTipoPersona = 0;
        this.nombre = "";
        this.apPaterno = "";
        this.apMaterno = "";
        this.fechaNacimiento = null;
        this.sexo = "";
        this.categoria = 0;
        this.numeroCompetidor = "";
        this.email = "";
        this.fechaRegistro = null;
        this.asistio = false;
        this.activo = false;
        this._existe = false;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCvePersona() {
        return cvePersona;
    }

    public void setCvePersona(int cvePersona) {
        this.cvePersona = cvePersona;
    }

    public int getCveTipoPersona() {
        return cveTipoPersona;
    }

    public void setCveTipoPersona(int cveTipoPersona) {
        this.cveTipoPersona = cveTipoPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApPaterno() {
        return apPaterno;
    }

    public void setApPaterno(String apPaterno) {
        this.apPaterno = apPaterno;
    }

    public String getApMaterno() {
        return apMaterno;
    }

    public void setApMaterno(String apMaterno) {
        this.apMaterno = apMaterno;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getNumeroCompetidor() {
        return numeroCompetidor;
    }

    public void setNumeroCompetidor(String numeroCompetidor) {
        this.numeroCompetidor = numeroCompetidor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ErrorSistema grabar() {
        ErrorSistema err;
        StringBuilder sql = null;

        if (!this._existe) {
            this.id = (UtilDB.getSiguienteNumero("participantes", "id"));
            this.numeroCompetidor = Utilerias.rellenarCeros(String.valueOf(this.id), 3);
            sql = new StringBuilder("INSERT INTO participantes (id,cve_persona,cve_tipo_persona,nombre,ap_paterno,ap_materno,fecha_nacimiento,sexo,categoria,numero_competidor,email,fecha_registro,asistio,activo) VALUES(");
            sql.append(this.id).append(",");
            sql.append(this.cvePersona).append(",");
            sql.append(this.cveTipoPersona).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.nombre)).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.apPaterno)).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.apMaterno)).append(",");
            sql.append(Utilerias.formatoFechaDB(this.fechaNacimiento)).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.sexo)).append(",");
            sql.append(this.categoria).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.numeroCompetidor)).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.email)).append(",");
            sql.append("GETDATE()").append(",");
            sql.append(this.asistio ? 1 : 0).append(",");
            sql.append(this.activo ? 1 : 0);
            sql.append(")");

            err = UtilDB.ejecutaSQL(sql.toString());
            if (err.getNumeroError() == 0) {
                this._existe = true;
            }
        } else {
            sql = new StringBuilder("UPDATE participantes SET ");
            sql.append("nombre = ").append(Utilerias.CadenaEncomillada(this.nombre)).append(",");
            sql.append("ap_paterno = ").append(Utilerias.CadenaEncomillada(this.apPaterno)).append(",");
            sql.append("ap_materno = ").append(Utilerias.CadenaEncomillada(this.apMaterno)).append(",");
            sql.append("fecha_nacimiento = ").append(Utilerias.formatoFechaDB(this.fechaRegistro)).append(",");
            sql.append("sexo = ").append(Utilerias.CadenaEncomillada(this.sexo)).append(",");
            sql.append("categoria = ").append(this.categoria).append(",");
            sql.append("numero_competidor = ").append(Utilerias.CadenaEncomillada(this.numeroCompetidor)).append(",");
            sql.append("email = ").append(Utilerias.CadenaEncomillada(this.email)).append(",");
            sql.append("asistio = ").append(this.asistio ? 1 : 0).append(",");
            sql.append("activo = ").append(this.activo ? 1 : 0);
            sql.append(" WHERE id = ").append(this.id);
            err = UtilDB.ejecutaSQL(sql.toString());
        }

        return err;
    }

    private void cargar() {
        String sql = "SELECT * FROM participantes WHERE id = " + this.id;
        Resultados rs = UtilDB.ejecutaConsulta(sql);
        while (rs.next()) {
            cargar(rs);
        }
        rs.close();
    }

    private void cargar(Resultados rst) {
        this.id = rst.getInt("id");
        this.cvePersona = rst.getInt("cve_persona");
        this.cveTipoPersona = rst.getInt("cve_tipo_persona");
        this.nombre = rst.getString("nombre");
        this.apPaterno = rst.getString("ap_paterno");
        this.apMaterno = rst.getString("ap_materno");
        this.fechaNacimiento = null;
        this.sexo = rst.getString("sexo");
        this.categoria = rst.getInt("categoria");
        this.numeroCompetidor = rst.getString("numero_competidor");
        this.email = rst.getString("email");
        this.fechaRegistro = rst.getCalendar("fecha_registro");
        this.asistio = rst.getBoolean("asistio");
        this.activo = rst.getBoolean("activo");
        this._existe = true;
    }

}
