
function mostrarCarga() {
    document.getElementById("overlay").style.display = "block"; // Mostrar pantalla de carga
    setTimeout(function(){
        document.getElementById("overlay").style.display = "none"; // Ocultar pantalla de carga
        document.getElementById("formularioPedido").submit(); // Enviar formulario
    }, 1000);
}