

let animationPath = '/animations/animation.json';

// Configuración de la animación
let animationSettings = {
    container: document.getElementById('lottie-animation'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: animationPath
};

// Cargar y renderizar la animación
let anim = lottie.loadAnimation(animationSettings);