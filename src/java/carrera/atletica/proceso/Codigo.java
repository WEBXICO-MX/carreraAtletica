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

public class Codigo {

    private int id;
    private String codigo;
    private boolean _existe;

    public Codigo() {
        this.limpiar();
    }

    public Codigo(int id) {
        this.limpiar();
        this.id = id;
        this.cargar();
    }

    private void limpiar() {
        this.id = 0;
        this.codigo = "";
        this._existe = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public ErrorSistema grabar() {
        ErrorSistema err;
        StringBuilder sql = null;

        if (!this._existe) {
            this.id = (UtilDB.getSiguienteNumero("codigos", "id"));
            sql = new StringBuilder("INSERT INTO codigos (id, codigo) VALUES(");
            sql.append(this.id).append(",");
            sql.append(Utilerias.CadenaEncomillada(this.codigo));
            sql.append(")");

            err = UtilDB.ejecutaSQL(sql.toString());
            if (err.getNumeroError() == 0) {
                this._existe = true;
            }
        } else {
            sql = new StringBuilder("UPDATE codigos SET ");
            sql.append("codigo = ").append(Utilerias.CadenaEncomillada(this.codigo));
            sql.append(" WHERE id = ").append(this.id);
            err = UtilDB.ejecutaSQL(sql.toString());
        }

        return err;
    }

    private void cargar() {
        String sql = "SELECT * FROM codigos WHERE id = " + this.id;
        Resultados rs = UtilDB.ejecutaConsulta(sql);
        while (rs.next()) {
            cargar(rs);
        }
        rs.close();
    }

    private void cargar(Resultados rs) {
        this.id = rs.getInt("id");
        this.codigo = rs.getString("codigo");
        this._existe = true;
    }

}
