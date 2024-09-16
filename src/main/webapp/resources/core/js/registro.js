$(document).ready(function() {
    $('#password, #c_password').on('keyup', function() {
        const password = $('#password').val();
        const confirmPassword = $('#c_password').val();

        // Mostrar mensaje de error si las contraseñas no coinciden
        if (password !== confirmPassword) {
            $('#error').removeClass('d-none'); // Mostrar mensaje de error
        } else {
            $('#error').addClass('d-none'); // Ocultar mensaje de error
        }
    });
});