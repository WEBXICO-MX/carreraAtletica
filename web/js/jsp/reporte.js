var url = "reporte_ajax.jsp";

$(document).ready(function () {

    $('#registros_carrera_atletica').DataTable({"pageLength": 25}).order([0, 'desc']).draw();
});

function setAsistencia(checkbox) {
    //console.log($(checkbox).prop("checked") + " " + $(checkbox).prop("name") + " " + ($(checkbox).prop("name").substring(10, $(checkbox).prop("name").length)));

    var xCveParticipante = parseInt($(checkbox).prop("name").substring(10, $(checkbox).prop("name").length));
    var asistio = $(checkbox).prop("checked");

    //console.log("xCveParticipante: " + xCveParticipante + ", asistio: " + asistio);
    $('#myModal').modal('show');
    $.getJSON(url, {"xAccion": "setAsistio", "xCveParticipante": xCveParticipante, "xAsistio": asistio}, function (data, textStatus, jqXHR) {
        $('#myModal').modal('hide');
        $("#div_mensaje").removeClass("alert-success alert-info  alert-warning alert-danger");

        if (data.resultado === 1)
        {
            $("#mensaje").html("<span>" + data.mensaje + "</span>");
            $("#div_mensaje").addClass("alert-success");
            $("#div_mensaje").fadeIn("slow");
            if (data.asistio)
            {
                $("#td_asistio" + data.participante_id).html("<a href=\"prn_constancia_participacion.jsp?id=" + data.participante_id + "\" target=\"_blank\">Descargar</a>");

            }
            else
            {
                $("#td_asistio" + data.participante_id).html("No disponible");
            }
        }
        else
        {
            $("#mensaje").html("<span>" + data.mensaje + "</span>");
            $("#div_mensaje").addClass("alert-warning");


        }

        $("#div_mensaje").fadeTo(2000, 500).slideUp(500);

    });

}

function descargarExcel()
{
    var search = $("input[type='search']").val();
    //window.location.href = "prn_registro_excel.jsp?s=" + search;
    window.open('prn_registro_excel.jsp?s=' + search,'_blank');
}