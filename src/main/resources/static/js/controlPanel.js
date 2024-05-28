


//Animaciones
let addMenuAnimation = lottie.loadAnimation({
    container: document.getElementById('add-menu-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/plato.json' // Path to your Lottie animation JSON file
});

let addDishAnimation = lottie.loadAnimation({
    container: document.getElementById('add-dish-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/plato.json'
});

let viewInventoryAnimation = lottie.loadAnimation({
    container: document.getElementById('view-inventory-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/inventory.json'
});

let addInventoryItemAnimation = lottie.loadAnimation({
    container: document.getElementById('add-inventory-item-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/inventory.json'
});

let deleteDishAnimation = lottie.loadAnimation({
    container: document.getElementById('delete-dish-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/delete.json'
});

let deleteMenuAnimation = lottie.loadAnimation({
    container: document.getElementById('delete-menu-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/delete.json'
});

let deleteItemAnimation = lottie.loadAnimation({
    container: document.getElementById('delete-item-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/delete.json'
});

document.addEventListener("DOMContentLoaded", function() {
    lottie.loadAnimation({
        container: document.getElementById('informe-pedidos-animation'), // ID del contenedor de la animación
        renderer: 'svg',
        loop: true,
        autoplay: true,
        path: '/animations/mail.json' // Ruta al archivo JSON de la animación Lottie
    });
});
