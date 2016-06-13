// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UtilDB.java

package carrera.atletica.comun;

import com.codestudio.util.JDBCPool;
import com.codestudio.util.SQLManager;
import java.sql.*;

// Referenced classes of package saiiut.comun:
//            ErrorSistema, Resultados, Contador

public class UtilDB
{

    public UtilDB()
    {
    }

    public static Connection getConnection()
    {
        try
        {
            Class.forName("com.codestudio.sql.PoolMan").newInstance();
            if(!Contador.getArrancado())
            {
                JDBCPool jpool = (JDBCPool)SQLManager.getInstance().getPool("control");
                System.out.println("Poolman inicializado utilizando base de datos en ".concat(String.valueOf(String.valueOf(jpool.getURL()))));
            }
        }
        catch(Exception ex)
        {
            System.out.println("No se ha hallado el PoolMan. Checar si esta instalado.");
            Connection connection = null;
            Connection connection2 = connection;
            Connection connection4 = connection2;
            Connection connection6 = connection4;
            return connection6;
        }
        Connection con;
        try
        {
            con = DriverManager.getConnection("jdbc:poolman//control");
        }
        catch(SQLException e)
        {
            System.out.println("No se ha establecido la conexion:".concat(String.valueOf(String.valueOf(e.toString()))));
            Connection connection1 = null;
            Connection connection3 = connection1;
            Connection connection5 = connection3;
            Connection connection7 = connection5;
            return connection7;
        }
        return con;
    }

    public static Connection getConnectionAC()
    {
        try
        {
            Class.forName("com.codestudio.sql.PoolMan").newInstance();
            if(!Contador.getArrancado())
            {
                JDBCPool jpool = (JDBCPool)SQLManager.getInstance().getPool("control");
                System.out.println("Poolman inicializado utilizando base de datos en ".concat(String.valueOf(String.valueOf(jpool.getURL()))));
            }
        }
        catch(Exception ex)
        {
            System.out.println("No se ha hallado el PoolMan. Checar si esta instalado.");
            Connection connection = null;
            Connection connection2 = connection;
            Connection connection4 = connection2;
            Connection connection6 = connection4;
            return connection6;
        }
        Connection con;
        try
        {
            con = DriverManager.getConnection("jdbc:poolman//control");
            con.setAutoCommit(false);
        }
        catch(SQLException e)
        {
            System.out.println("No se ha establecido la conexion:".concat(String.valueOf(String.valueOf(e.toString()))));
            Connection connection1 = null;
            Connection connection3 = connection1;
            Connection connection5 = connection3;
            Connection connection7 = connection5;
            return connection7;
        }
        return con;
    }
    
    
    public static ErrorSistema insertaDatos(String tabla, String campos[], String valores[])
    {
        ErrorSistema err = new ErrorSistema();
        if(campos.length == valores.length)
        {
            String cadSQL = String.valueOf(String.valueOf((new StringBuffer("insert into ")).append(tabla).append(" (")));
            String cadSQL2 = " values (";
            for(int i = 0; i < campos.length; i++)
            {
                cadSQL = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(cadSQL)))).append(campos[i]).append(",")));
                cadSQL2 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(cadSQL2)))).append(valores[i]).append(",")));
            }

            cadSQL = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(cadSQL.substring(0, cadSQL.length() - 1))))).append(")").append(cadSQL2.substring(0, cadSQL2.length() - 1)).append(")")));
            Connection con = null;
            Statement s = null;
            try
            {
                con = getConnection();
                s = con.createStatement();
                s.execute(cadSQL);
                s.close();
            }
            catch(SQLException e)
            {
                err.setNumeroError(e.getErrorCode());
                err.setCadenaError(e.toString());
                err.setCadenaSQL(cadSQL);
                err.out();
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch(Exception exception1) { }
            }
        } else
        {
            err.numError = -2;
            err.setCadenaError("Numero de campos y valores no coincidente");
            err.out();
        }
        return err;
    }

    public static ErrorSistema borraDatos(String tabla, String campos[], String valores[])
    {
        ErrorSistema err = new ErrorSistema();
        if(campos.length == valores.length)
        {
            String cadSQL = String.valueOf(String.valueOf((new StringBuffer("delete from ")).append(tabla).append(" where ")));
            for(int i = 0; i < campos.length; i++)
                cadSQL = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(cadSQL)))).append(campos[i]).append("=").append(valores[i]).append(" and ")));

            cadSQL = cadSQL.substring(0, cadSQL.length() - 5);
            Connection con = null;
            Statement s = null;
            try
            {
                con = getConnection();
                s = con.createStatement();
                s.execute(cadSQL);
                s.close();
            }
            catch(SQLException e)
            {
                err.setNumeroError(e.getErrorCode());
                err.setCadenaError(e.toString());
                err.setCadenaSQL(cadSQL);
                err.out();
            }
            finally
            {
                try
                {
                    con.close();
                }
                catch(Exception exception1) { }
            }
        } else
        {
            err.numError = -2;
            err.setCadenaError("Numero de campos y valores no coincidente");
        }
        return err;
    }

    public static ErrorSistema ejecutaSQL(String sql)
    {
        ErrorSistema err = new ErrorSistema();
        Connection con = null;
        Statement s = null;
        int num = 0;
        try
        {
            con = getConnection();
            s = con.createStatement(1003, 1007);
            num = s.executeUpdate(sql);
        }
        catch(SQLException e)
        {
            num = -1;
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        if(num == 0)
        {
            err.setNumeroError(100);
            err.setCadenaError("No se afecto ningun registro");
        }
        return err;
    }

    public static Resultados ejecutaConsulta(String sql)
    {
        ErrorSistema err = new ErrorSistema();
        Resultados res = new Resultados();
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            s = con.createStatement(1004, 1007);
            rs = s.executeQuery(sql);
            res.setDatos(rs);
            rs.close();
        }
        catch(SQLException e)
        {
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        return res;
    }

    public static int getSiguienteNumero(String tabla, String campo)
    {
        ErrorSistema err = new ErrorSistema();
        int res = 0;
        String sql = String.valueOf(String.valueOf((new StringBuffer("select max(")).append(campo).append(") as num from ").append(tabla)));
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(sql);
            if(rs.next())
                res = rs.getInt("num") + 1;
            else
                res = 1;
            rs.close();
        }
        catch(SQLException e)
        {
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        return res;
    }

    public static int getSiguienteNumero(String tabla, String campo, String filtro)
    {
        ErrorSistema err = new ErrorSistema();
        int res = 0;
        String sql = String.valueOf(String.valueOf((new StringBuffer("select max(")).append(campo).append(") as num from ").append(tabla).append(" ").append(filtro)));
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(sql);
            if(rs.next())
                res = rs.getInt("num") + 1;
            else
                res = 1;
            rs.close();
        }
        catch(SQLException e)
        {
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        return res;
    }

    public static int getSiguienteNumero(String tabla, String campo, int universidad)
    {
        ErrorSistema err = new ErrorSistema();
        int res = 0;
        String sql = String.valueOf(String.valueOf((new StringBuffer("select max(")).append(campo).append(") as num from ").append(tabla).append(" where cve_universidad = ").append(universidad)));
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(sql);
            if(rs.next())
                res = rs.getInt("num") + 1;
            else
                res = 1;
            rs.close();
        }
        catch(SQLException e)
        {
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        return res;
    }

    public static int getSiguienteNumero(String tabla, String campo, int universidad, int periodo)
    {
        ErrorSistema err = new ErrorSistema();
        int res = 0;
        String sql = String.valueOf(String.valueOf((new StringBuffer("select max(")).append(campo).append(") as num from ").append(tabla).append(" where cve_universidad = ").append(universidad).append("and cve_periodo = ").append(periodo)));
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(sql);
            if(rs.next())
                res = rs.getInt("num") + 1;
            else
                res = 1;
            rs.close();
        }
        catch(SQLException e)
        {
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        return res;
    }

    public static int getSiguienteNumero(String tabla, String campo, int universidad, int unidadAcademica, int division, int carrera)
    {
        ErrorSistema err = new ErrorSistema();
        int res = 0;
        String sql = String.valueOf(String.valueOf((new StringBuffer("select max(")).append(campo).append(") as num from ").append(tabla).append(" where cve_universidad = ").append(universidad).append(" and cve_unidad_academica = ").append(unidadAcademica).append(" and cve_division = ").append(division).append(" and cve_carrera = ").append(carrera)));
        Connection con = null;
        Statement s = null;
        ResultSet rs = null;
        try
        {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery(sql);
            if(rs.next())
                res = rs.getInt("num") + 1;
            else
                res = 1;
            rs.close();
        }
        catch(SQLException e)
        {
            err.setNumeroError(e.getErrorCode());
            err.setCadenaError(e.toString());
            err.setCadenaSQL(sql);
            err.out();
        }
        finally
        {
            try
            {
                s.close();
                con.close();
            }
            catch(Exception exception1) { }
        }
        return res;
    }

    public static int numRegistros(String sql)
    {
        int total = 0;
        Resultados rs = null;
        try
        {
            for(rs = ejecutaConsulta(sql); rs.next();)
                total++;

            rs.close();
            rs = null;
        }
        catch(Exception exception) { }
        return total;
    }
}
