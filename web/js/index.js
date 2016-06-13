var url = "index_ajax.jsp";

$(document).ready(function () {
    $.ajaxSetup({"cache": false});

    $(".date-picker").datepicker({yearRange: "-70:+0", changeMonth: true, changeYear: true, dateFormat: 'dd/mm/yy'});

    /*$("#btnBuscar").click(function () {
        var codigo = $("#txtCodigo").val();

        if (codigo === "")
        {
            $("#mensaje").html("<strong>Información</strong> <span>Ingrese un código por favor</span>");
            $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");
            $("#div_mensaje").addClass("alert-info");
            $("#div_mensaje").fadeIn("slow");
            return;
        }

        $.getJSON(url, {"xAccion": "busqueda", "xCodigo": codigo}, function (data, textStatus, jqXHR) {
            if (data.resultado === 1)
            {
                $("#div_consultar").fadeOut("fast");
                $("#div_mensaje").fadeOut("slow");
                $("#mensaje").html("");
                $("#txtCodigo2").val(data.codigoId);
                $("#txtCodigo").val("");
                $("#div_registro").fadeIn("slow");
            }
            else
            {
                $("#mensaje").html("<strong>Advertencia</strong> <span>" + data.mensaje + "</span>");
                $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");
                $("#div_mensaje").addClass("alert-warning");
                $("#div_mensaje").fadeIn("slow");
                $("#txtCodigo").val("");

            }
        });

    });*/

    $('#frmRegistro').submit(function (e) {
        e.preventDefault();

        var json = JSON.stringify($('#frmRegistro').serializeObject());

        if (validar())
        {   $('#myModal').modal('show'); 
            $.post(url, {"xAccion": 'grabar', "datos": json}, function (data) {

                if (data.resultado === 1)
                {
                    /*$("#mensaje").html("<strong>Éxito</strong> <span>" + data.mensaje + "</span>");
                    $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");
                    $("#div_mensaje").addClass("alert-success");
                    $("#div_mensaje").fadeIn("slow");
                    limpiar();
                    $("#div_registro").fadeOut("slow");*/
                    window.location.replace("jsp/prn_registro_pdf.jsp?id="+data.participante_id);
                }
                else
                {   $('#myModal').modal('hide'); 
                    $("#mensaje").html("<strong>Advertencia</strong> <span>" + data.mensaje + "</span>");
                    $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");
                    $("#div_mensaje").addClass("alert-danger");
                    $("#div_mensaje").fadeIn("slow");
                }

            }, 'json');
        }


    });

});


$.fn.serializeObject = function ()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function isValidEmailAddress(emailAddress) {
    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
    return pattern.test(emailAddress);
}

function viewInicio()
{
    /*$("#div_consultar").fadeOut();*/
    $("#div_registro").fadeOut();
    $("#div_informacion").fadeIn();
    limpiar();
}

function viewRegistro()
{
    $("#div_informacion").fadeOut();
    $("#div_registro").fadeIn();
    /*$("#div_consultar").fadeIn();*/

}

function validar()
{
    var valido = true;
    var msg = "<ul>";

    if ($("#txtNombre").val() === "")
    {
        valido = false;
        msg += "<li>Ingrese su nombre</li>";

    }
    if ($("#txtPaterno").val() === "")
    {
        valido = false;
        msg += "<li>Ingrese su apellido paterno</li>";

    }
    if ($("#txtMaterno").val() === "")
    {
        valido = false;
        msg += "<li>Ingrese su apellido materno</li>";

    }
    if ($("#txtFechaNacimiento").val() === "")
    {
        valido = false;
        msg += "<li>Ingrese su fecha de nacimiento</li>";
    }
    if ($(':radio[name="rdSexo"]:checked').length === 0)
    {
        valido = false;
        msg += "<li>Seleccione su sexo</li>";
    }
    if ($("#cmbCategoria").val() === "0")
    {
        valido = false;
        msg += "<li>Seleccione una categoría</li>";
    }
    if($("#cmbTipoPersona").val() === "0")
    { valido = false;
      msg += "<li>Seleccione un tipo de persona</li>";
    }
    if( !isValidEmailAddress($("#txtEmail").val()) )
    { valido = false;
        msg += "<li>Ingrese un email valido</li>"; 
    }

    msg += "</ul>";

    if (!valido)
    {
        $("#mensaje").html("<strong>Información</strong> <span>" + msg + "</span>");
        $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");
        $("#div_mensaje").addClass("alert-info");
        $("#div_mensaje").fadeIn("slow");
    }
    return valido;


}

function limpiar()
{
    $("#div_mensaje").fadeOut();
    $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");
    $("#mensaje").html("");
    $("#txtNombre").val("");
    $("#txtPaterno").val("");
    $("#txtMaterno").val("");
    $("#txtFechaNacimiento").val("");
    $("input[name=rdSexo]").prop('checked', false);
    $("#cmbCategoria").val("0");
    $("#cmbTipoPersona").val("0");
    $("#txEmail").val("");

}