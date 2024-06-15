// Conectarse al WebSocket
let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Conectado: ' + frame);

    // Suscribirse al tópico de inventario
    stompClient.subscribe('/topic/inventario', function (message) {
        let inventario = JSON.parse(message.body);
        actualizarInventario(inventario);
    });
});

// Función para actualizar el inventario en el frontend
function actualizarInventario(inventario) {
    // Seleccionar la fila existente usando el ID del inventario
    let filaExistente = document.querySelector(`tr[data-id="${inventario.idInventario}"]`);

    if (filaExistente) {
        // Actualizar la cantidad en la fila existente
        filaExistente.querySelector('.cantidad').value = inventario.cantidad;
    } else {
        // Crear una nueva fila si no existe
        let nuevaFila = `
                <tr data-id="${inventario.idInventario}">
                    <td>${inventario.nombre}</td>
                    <td>${inventario.categoria}</td>
                    <td>
                        <form th:action="@{/inventario/actualizar}" method="post">
                            <input type="hidden" name="id" value="${inventario.idInventario}">
                            <input type="number" name="cantidad" class="cantidad" value="${inventario.cantidad}" required>
                            <button type="submit" class="btn btn-primary">Guardar</button>
                        </form>
                    </td>
                    <td>${inventario.unidadMedida}</td>
                    <td>${inventario.precioUnitario} €</td>
                </tr>
            `;
        document.getElementById('inventarioTable').insertAdjacentHTML('beforeend', nuevaFila);
    }
}