//ajax de agregar bebida
$(document).ready(function() {
    $('.agregarBoton').click(function(event) {
        event.preventDefault(); // Prevenir el comportamiento predeterminado del botón

        let formulario = $(this).closest('form');
        let formData = formulario.serialize();

        mostrarCarga(); // Mostrar la carga

        $.ajax({
            type: 'POST',
            url: formulario.attr('action'),
            data: formData,
        });
    });
});

//ajax de agregar entrantes

//agregar menus
$(document).ready(function() {
    $('.pedir-menu-btn').click(function(event) {
        event.preventDefault(); // Prevenir el comportamiento predeterminado del botón

        let formulario = $(this).closest('form');
        let formData = formulario.serialize();

        mostrarCarga(); // Mostrar la carga

        $.ajax({
            type: 'POST',
            url: formulario.attr('action'),
            data: formData,

        });
    });
});




