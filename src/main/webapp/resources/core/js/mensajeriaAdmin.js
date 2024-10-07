$(document).ready(function() {
    $('#iconoMensajeria').click(function() {
        $('#cajaMensajeria').toggle();
        if ($('#cajaMensajeria').is(':visible')) {
            obtenerMensajes();
        }
    });
});

function obtenerMensajes() {
    $.ajax({
        type: "GET",
        url: "/spring/admin/obtenerMensajes",
        dataType: "json",
        success: function(response) {
            $('#mensajes').empty();
            response.forEach(function(msj) {
                var mensajeHTML = `
                        <div class="mensaje" id="mensaje-${msj.id}" data-id="${msj.id}">
                            <span>${msj.contenido}</span>
                            <span class="hora">${new Date(msj.hora).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                            <i class="bi bi-check2-all visto" ${msj.visto ? 'style="display:block;"' : 'style="display:none;"'}></i>
                        </div>`;
                $('#mensajes').append(mensajeHTML);
            });
            scrollToBottom();
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
            alert("Hubo un problema al obtener los mensajes.");
        }
    });
}


function enviarMensaje() {
    const mensaje = $("#mensaje").val();
    $.ajax({
        type: "POST",
        url: "/spring/admin/enviarMensaje",
        data: { mensaje: mensaje },
        dataType: "json",
        success: function(response) {
            console.log(response); // Muestra el objeto completo en la consola

            var nuevoMensaje = `
                        <div class="mensaje" id="mensaje-${response.id}" data-id="${response.id}">
                        <span>${response.contenido}</span>
                        <span class="hora">${new Date(response.hora).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                        <i class="bi bi-check2-all visto" ${response.visto ? 'style="display:block;"' : 'style="display:none;"'}></i>
                    </div>`;
            $("#mensajes").append(nuevoMensaje);
            $("#mensaje").val("");

            scrollToBottom();
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
            alert("Hubo un problema al enviar el mensaje.");
        }
    });
}

function scrollToBottom() {
    var mensajes = document.getElementById("mensajes");
    mensajes.scrollTop = mensajes.scrollHeight;
}