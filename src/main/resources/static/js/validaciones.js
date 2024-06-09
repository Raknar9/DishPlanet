//validacion de form de menu
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("menuForm");

    form.addEventListener("submit", function(event) {
        let isValid = true;

        // Clear previous error messages
        document.getElementById("errorNombre").innerText = "";
        document.getElementById("errorEntrante").innerText = "";
        document.getElementById("errorPrincipal").innerText = "";
        document.getElementById("errorPostre").innerText = "";
        document.getElementById("errorBebida").innerText = "";
        document.getElementById("errorPrecio").innerText = "";

        const nombre = document.getElementById("nombre").value;
        const entrante = document.getElementById("entrante").value;
        const principal = document.getElementById("principal").value;
        const postre = document.getElementById("postre").value;
        const bebida = document.getElementById("bebida").value;
        const precio = document.getElementById("precio").value;

        if (!isNaN(nombre)) {
            isValid = false;
            document.getElementById("errorNombre").innerText = "El campo 'Nombre' no puede contener números.";
        }
        if (!isNaN(entrante)) {
            isValid = false;
            document.getElementById("errorEntrante").innerText = "El campo 'Entrante' no puede contener números.";
        }
        if (!isNaN(principal)) {
            isValid = false;
            document.getElementById("errorPrincipal").innerText = "El campo 'Principal' no puede contener números.";
        }
        if (!isNaN(postre)) {
            isValid = false;
            document.getElementById("errorPostre").innerText = "El campo 'Postre' no puede contener números.";
        }
        if (!isNaN(bebida)) {
            isValid = false;
            document.getElementById("errorBebida").innerText = "El campo 'Bebida' no puede contener números.";
        }
        if (isNaN(precio) || precio <= 0) {
            isValid = false;
            document.getElementById("errorPrecio").innerText = "El campo 'Precio' debe ser un número positivo.";
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});

//validacion de form de plato
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("platoForm");

    form.addEventListener("submit", function(event) {
        let isValid = true;

        // Clear previous error messages
        document.getElementById("errorNombre").innerText = "";
        document.getElementById("errorDescripcion").innerText = "";
        document.getElementById("errorPrecio").innerText = "";
        document.getElementById("errorTipo").innerText = "";

        const nombre = document.getElementById("nombre").value;
        const descripcion = document.getElementById("descripcion").value;
        const precio = document.getElementById("precio").value;
        const tipo = document.getElementById("tipo").value;

        if (!isNaN(nombre)) {
            isValid = false;
            document.getElementById("errorNombre").innerText = "El campo 'Nombre' no puede contener números.";
        }
        if (descripcion.trim() === "") {
            isValid = false;
            document.getElementById("errorDescripcion").innerText = "El campo 'Descripción' no puede estar vacío.";
        }
        if (isNaN(precio) || precio <= 0) {
            isValid = false;
            document.getElementById("errorPrecio").innerText = "El campo 'Precio' debe ser un número positivo.";
        }
        if (!isNaN(tipo)) {
            isValid = false;
            document.getElementById("errorTipo").innerText = "El campo 'Tipo' no puede contener números.";
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});

//validacion de form de nuevo item
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("inventarioForm");

    form.addEventListener("submit", function(event) {
        let isValid = true;

        // Clear previous error messages
        document.getElementById("errorNombre").innerText = "";
        document.getElementById("errorCategoria").innerText = "";
        document.getElementById("errorCantidad").innerText = "";
        document.getElementById("errorUnidadMedida").innerText = "";
        document.getElementById("errorPrecioUnitario").innerText = "";

        const nombre = document.getElementById("nombre").value;
        const categoria = document.getElementById("categoria").value;
        const cantidad = document.getElementById("cantidad").value;
        const unidadMedida = document.getElementById("unidadMedida").value;
        const precioUnitario = document.getElementById("precioUnitario").value;

        if (!isNaN(nombre)) {
            isValid = false;
            document.getElementById("errorNombre").innerText = "El campo 'Nombre' no puede contener números.";
        }
        if (!isNaN(categoria)) {
            isValid = false;
            document.getElementById("errorCategoria").innerText = "El campo 'Categoría' no puede contener números.";
        }
        if (isNaN(cantidad) || cantidad < 0) {
            isValid = false;
            document.getElementById("errorCantidad").innerText = "El campo 'Cantidad' debe ser un número no negativo.";
        }
        if (!isNaN(unidadMedida)) {
            isValid = false;
            document.getElementById("errorUnidadMedida").innerText = "El campo 'Unidad de Medida' no puede contener números.";
        }
        if (isNaN(precioUnitario) || precioUnitario <= 0) {
            isValid = false;
            document.getElementById("errorPrecioUnitario").innerText = "El campo 'Precio Unitario' debe ser un número positivo.";
        }

        if (!isValid) {
            event.preventDefault();
        }
    });
});

document.addEventListener("DOMContentLoaded", function() {
    // Validación de eliminar item de inventario
    const formItem = document.getElementById("eliminarItemForm");
    formItem.addEventListener("submit", function(event) {
        event.preventDefault();
        const nombreItem = document.getElementById("nombreItem").value;
        const errorNombreItem = document.getElementById("errorNombreItem");
        errorNombreItem.innerText = "";
        fetch(`/item/existe?nombreItem=${encodeURIComponent(nombreItem)}`)
            .then(response => response.json())
            .then(data => {
                if (!data.existe) {
                    errorNombreItem.innerText = "El item no existe.";
                    errorNombreItem.style.color = "red";
                } else {
                    formItem.submit();
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });

    // Validación de eliminar plato
    const formPlato = document.getElementById("eliminarPlatoForm");
    formPlato.addEventListener("submit", function(event) {
        event.preventDefault();
        const nombre = document.getElementById("nombre").value;
        const errorNombre = document.getElementById("errorNombre");
        errorNombre.innerText = "";
        fetch(`/plato/existe?nombre=${encodeURIComponent(nombre)}`)
            .then(response => response.json())
            .then(data => {
                if (!data.existe) {
                    errorNombre.innerText = "El plato no existe.";
                    errorNombre.style.color = "red";
                } else {
                    formPlato.submit();
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });

    // Validación de eliminar menu
    const formMenu = document.getElementById("eliminarMenuForm");
    formMenu.addEventListener("submit", function(event) {
        event.preventDefault();
        const nombreMenu = document.getElementById("nombreMenu").value;
        const errorNombreMenu = document.getElementById("errorNombreMenu");
        errorNombreMenu.innerText = "";
        fetch(`/menu/existe?nombreMenu=${encodeURIComponent(nombreMenu)}`)
            .then(response => response.json())
            .then(data => {
                if (!data.existe) {
                    errorNombreMenu.innerText = "El menú no existe.";
                    errorNombreMenu.style.color = "red";
                } else {
                    formMenu.submit();
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });
});

//validaciones para que no se envie un pedido vacio
document.addEventListener("DOMContentLoaded", function() {
    const pedidosTableBody = document.getElementById("pedidosTableBody");
    const finalizarPedidoForm = document.getElementById("finalizarPedidoForm");
    const enviarReciboEmailForm = document.getElementById("enviarReciboEmailForm");

    const finalizarPedidoError = document.getElementById("finalizarPedidoError");
    const enviarReciboEmailError = document.getElementById("enviarReciboEmailError");

    function hayPedidos() {
        return pedidosTableBody.getElementsByTagName("tr").length > 0;
    }

    finalizarPedidoForm.addEventListener("submit", function(event) {
        finalizarPedidoError.innerText = "";
        if (!hayPedidos()) {
            event.preventDefault();
            finalizarPedidoError.innerText = "No hay ningún pedido para finalizar.";
        }
    });

    enviarReciboEmailForm.addEventListener("submit", function(event) {
        enviarReciboEmailError.innerText = "";
        if (!hayPedidos()) {
            event.preventDefault();
            enviarReciboEmailError.innerText = "No hay ningún pedido para enviar por email.";
        }
    });
});
//validacion para si el usuario ya existe
document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("registroForm");

    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Previene el envío del formulario

        const username = document.getElementById("username").value;
        const errorUsername = document.getElementById("errorUsername");

        // Limpiar el mensaje de error previo
        errorUsername.innerText = "";

        // Hacer la petición AJAX para verificar si el usuario existe
        fetch(`/usuario/existe?username=${username}`)
            .then(response => response.json())
            .then(data => {
                if (data.existe) {
                    errorUsername.innerText = "El nombre de usuario ya existe.";
                    errorUsername.style.color = "red";
                } else {
                    form.submit(); // Enviar el formulario si el usuario no existe
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });
});

//contar pedidos
document.addEventListener('DOMContentLoaded', function() {
    var pedidosTableBody = document.getElementById('pedidosTableBody');
    var pedidoCountSpan = document.getElementById('pedidoCount');

    if (pedidosTableBody && pedidoCountSpan) {
        var rowCount = pedidosTableBody.getElementsByTagName('tr').length;
        pedidoCountSpan.textContent = `(${rowCount})`;
    }
});