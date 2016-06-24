<%-- 
    Document   : enviar_email
    Created on : 24-jun-2016, 12:43:38
    Author     : Roberto Eder Weiss Juárez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Carrera atlética XXV aniversario CGUTyP</title>
        <link href="../bower_components/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
    </head>
    <body>
        <div class="container">
            <div class="row" >
                <div class="col-md-12">
                    <h1 class="text-center">Carrera atlética XXV aniversario CGUTyP</h1>
                </div>
                <div class="col-sm-4 col-sm-offset-4">
                    <form role="form" name="frmEnviarEmail" id="frmEnviarEmail" action="" method="post" style="margin-top:25px;">
                        <div class="form-group">
                            <label for="txtHost">SMTP HOST:</label>
                            <input type="text" class="form-control" id="txtHost" name="txtHost" placeholder="Ingrese el SMTP host" maxlength="50" tabindex="1" value="pod51010.outlook.com">
                        </div>
                        <div class="form-group">
                            <label for="txtUser">SMTP USER:</label>
                            <input type="text" class="form-control" id="txtUser" name="txtUser" placeholder="Ingrese el SMTP user" maxlength="50" tabindex="1">
                        </div>
                        <div class="form-group">
                            <label for="txtPassword">SMTP PASSWORD:</label>
                            <input type="password" class="form-control" id="txtPassword" name="txtPassword" placeholder="Ingrese el SMTP password" maxlength="50" tabindex="1">
                        </div>
                        <div class="form-group">
                            <button type="button" class="btn btn-success btn-block" id="btnGrabar" name="btnGrabar" tabindex="11" onClick="if (confirm('Desea Enviar email(s)?')) {
                                        enviarEmail();
                                    } else {
                                        return false;
                                    }">Enviar</button>
                        </div> 
                    </form>
                    <div id="resultados" style="border:1px dashed red; margin:20px auto; width:auto; height:auto; font-family:Georgia, serif; font-style:normal;font-size:40px; font-weight:bold; color: #000;text-align:center;">&nbsp;</div>
                </div>
            </div>                
        </div>
        <script src="../bower_components/jquery/dist/jquery.min.js"></script> 
        <script>
                                jQuery.fn.reset = function () {
                                    $(this).each(function () {
                                        this.reset();
                                    });
                                }

                                function enviarEmail()
                                {
                                    var msg = "[Aviso]\n\nNo puede enviar email(s) por alguna de los siguientes casos:\n\n";
                                    var valido = true;

                                    if ($("#txtHost").val() === "")
                                    {
                                        msg += "El campo host esta vacio\n";
                                        valido = false;
                                    }
                                    if ($("#txtUser").val() === "")
                                    {
                                        msg += "El campo User esta vacio\n";
                                        valido = false;
                                    }
                                    if ($("#txtPassword").val() === "")
                                    {
                                        msg += "El campo Password esta vacio\n";
                                        valido = false;

                                    }
                                    msg += "\nVerifiquelo porfavor.\n";
                                    if (valido)
                                    {
                                        $("#resultados").html("<img src='../img/ajax-loading.gif'> Cargando ...");
                                        $("#resultados").load('enviar_email_ajax.jsp', {"xAccion": 'enviarEmailConstanciaParticipacion', "xHost": $("#txtHost").val(), "xUser": $("#txtUser").val(), "xPasswd": $("#txtPassword").val()}, function (data) {
                                            $("#frmEnviarEmail").reset();
                                        });
                                    }
                                    else
                                    {
                                        alert(msg);
                                    }

                                }
        </script>
    </body>
</html>
