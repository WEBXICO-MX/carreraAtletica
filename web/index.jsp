<%-- 
    Document   : index
    Created on : 06-jun-2016, 12:24:08
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int xCvePersona = 0;
    try {
        xCvePersona = request.getParameter("xCve") != null ? Integer.parseInt(request.getParameter("xCve")) : 0;
    } catch (Exception e) {
        xCvePersona = 0;
    }

%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Carrera atlética XXV aniversario CGUTyP</title>
        <link href="bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="bower_components/jquery-ui/themes/smoothness/jquery-ui.min.css" rel="stylesheet"/>
        <link href='https://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'>
        <style>

            body { 
                font-family: 'Open Sans', sans-serif;
                background: url(img/fondo.png) no-repeat center center fixed; 
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;
            }

            .fondo_azul
            {background-color: #312F6F; color: white;}
        </style>
    </head>
    <body>
        <div class="container">
            <div class="row" >
                <div class="col-sm-6 col-sm-offset-3 text-right" style="margin-top:270px;">
                    <a href="javascript:void(0);" onclick="viewInicio();"><img src="img/btn_inicio.png" alt="Inicio"/></a>
                    <a href="javascript:void(0);" onclick="viewRegistro();"><img src="img/btn_registro.png" alt="Registro"/></a>
                </div>
                <div class="col-sm-6 col-sm-offset-3 fondo_azul text-center" id="div_informacion">
                    <img src="img/logo_fecha.png" alt="fecha" style="margin:25px auto 0 auto" class="img-responsive">
                    <a href="http://www.uttab.edu.mx/viewEventoPDF.action?id=138" target="_blank" title="Convocatoria y ruta de la carrera"><img src="img/logo_descarga.png" alt="descarga" class="img-responsive" style="margin:25px auto 25px auto;"/></a>
                </div>
                <!--<div class="col-sm-6 col-sm-offset-3 fondo_azul" id="div_consultar" style="display:none;">
                    <p style="margin-top:25px;">Introduce el número de tu boleto a 19 caracteres: (Ejemplo: UTTAB123412AABB0001)</p><br/>
                    <form role="form" name="frmCodigo" id="frmCodigo" action="" method="post">
                        <div class="form-group">
                            <label for="txtCodigo">Código:</label>
                            <input type="text" class="form-control" id="txtCodigo" name="txtCodigo" placeholder="Ingrese su código" maxlength="19">                       
                        </div>
                        <button type="button" class="btn btn-success" id="btnBuscar" name="btnBuscar">Buscar</button>
                    </form><br/>                  
                </div>-->
                <div class="col-sm-6 col-sm-offset-3 fondo_azul" id="div_registro" style="display:none;">
                    <p style="margin-top:25px; font-size: 18px">Registro de participantes</p>
                    <p style="font-size: 18px">Carrera corriendo por la educación UTTAB</p>
                    <form role="form" name="frmRegistro" id="frmRegistro" action="" method="post" style="margin-top:25px;">
                        <div class="col-md-6 form-group">
                            <label for="txtNombre">Nombre:</label>
                            <input type="text" class="form-control" id="txtNombre" name="txtNombre" placeholder="Ingrese su nombre" maxlength="50" tabindex="1">
                            <input type="hidden" class="form-control" name="xCvePersona" id="xCvePersona" value="<%=xCvePersona%>" />
                        </div>
                        <div class="col-md-6 form-group">
                            <div class="date-form">
                                <div class="form-horizontal">
                                    <div class="control-group">
                                        <label for="txtFechaNacimiento">Fecha de nacimiento:</label>
                                        <div class="controls">
                                            <div class="input-group">
                                                <input id="txtFechaNacimiento" name="txtFechaNacimiento" type="text" placeholder="Ingrese su fecha de nacimiento" class="date-picker form-control" tabindex="6"/>
                                                <label for="txtFechaNacimiento" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="txtPaterno">Apellido paterno:</label>
                            <input type="text" class="form-control" id="txtPaterno" name="txtPaterno" placeholder="Ingrese su apellido paterno" maxlength="50" tabindex="2">
                        </div>                        
                        <div class="col-md-6 form-group">
                            <label for="cmbCategoria">Categoría:</label>
                            <select name="cmbCategoria" id="cmbCategoria" class="form-control" tabindex="8">
                                <option value="0">Seleccione una opción</option>
                                <option value="1">3KM CARRERA</option>
                                <option value="2">3KM CAMINATA</option>
                            </select>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="txtMaterno">Apellido materno:</label>
                            <input type="text" class="form-control" id="txtMaterno" name="txtMaterno" placeholder="Ingrese su apellido materno" maxlength="50" tabindex="3">
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="txtEmail">Email:</label>
                            <input type="text" class="form-control" id="txtEmail" name="txtEmail" placeholder="Ingrese su email" maxlength="50" tabindex="9">
                        </div>
                        <div class=" col-md-6 form-group">
                            <label class="radio-inline">
                                <input type="radio" name="rdSexo" value="M" tabindex="4">Masculino
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="rdSexo" value="F" tabindex="5">Femenino
                            </label>
                        </div>
                        <div class=" col-md-6 form-group">

                        </div>                        
                        <div class="col-md-12  form-group">
                            <button type="button" class="btn btn-primary" id="btnLimpiar" name="btnLimpiar" tabindex="10">Limpiar</button>
                            <button type="submit" class="btn btn-success" id="btnGrabar" name="btnGrabar" tabindex="11">Enviar</button>
                        </div>                        
                    </form><br/>
                </div>
                <div class="col-sm-6 col-sm-offset-3">
                    <div class="alert fade in" id="div_mensaje" style="display:none; margin-top: 25px;">
                        <a href="#" class="close" onclick="$('.alert').hide()" aria-label="close">&times;</a> 
                        <!--<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>-->
                        <span id="mensaje"></span>
                    </div>
                </div>
                <div class="col-md-12">&nbsp;</div>                
            </div>
            <div class="row" >
                <div class="col-sm-12">
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                    <h4 class="modal-title">Carrera atlética XXV aniversario CGUTyP</h4>
                                </div>
                                <div class="modal-body">
                                    <img src="img/ajax-loading.gif" alt="Loading"/> procesando ...
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="bower_components/jquery/dist/jquery.min.js"></script>
        <script src="bower_components/jquery-ui/jquery-ui.min.js"></script>
        <script src="bower_components/jquery-ui/ui/i18n/datepicker-es.js"></script>
        <script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <script src="js/index.js"></script>
    </body>
</html>