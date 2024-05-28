const leftAnimation = bodymovin.loadAnimation({
    container: document.getElementById('left-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/addItem.json' // Ruta al archivo JSON de la animación
});
const rightAnimation = bodymovin.loadAnimation({
    container: document.getElementById('right-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/addItem.json' // Ruta al archivo JSON de la animación
});