// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Contador.java

package carrera.atletica.comun;

//import saiiut.escolar.Periodo;

// Referenced classes of package saiiut.comun:
//            Resultados, UtilDB

public class Contador
{

    public Contador()
    {
    }

    public static boolean getArrancado()
    {
        boolean res = arrancado;
        if(!arrancado)
            arrancado = true;
        return res;
    }
    
    private static boolean arrancado = false;
}
