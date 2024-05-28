function incrementarCantidad(button) {
    let cantidadInput = $(button).closest('tr').find('input[name="cantidad"]');
    let cantidadActual = parseInt(cantidadInput.val());
    cantidadInput.val(cantidadActual + 10);
}

function decrementarCantidad(button) {
    let cantidadInput = $(button).closest('tr').find('input[name="cantidad"]');
    let cantidadActual = parseInt(cantidadInput.val());
    if (cantidadActual > 0) {
        cantidadInput.val(cantidadActual - 10);
    }
}