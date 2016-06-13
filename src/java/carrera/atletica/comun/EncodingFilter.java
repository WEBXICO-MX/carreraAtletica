// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EncodingFilter.java

package carrera.atletica.comun;

import java.io.IOException;
import javax.servlet.*;

public class EncodingFilter
    implements Filter
{

    public EncodingFilter()
    {
    }

    public void init(FilterConfig fc)
        throws ServletException
    {
        filterConfig = fc;
        encoding = filterConfig.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException
    {
        req.setCharacterEncoding(encoding);
        chain.doFilter(req, resp);
    }

    public void destroy()
    {
    }

    private String encoding;
    private FilterConfig filterConfig;
}
