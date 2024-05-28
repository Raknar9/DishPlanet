const images = document.querySelectorAll('.image-slider img');

let index = 0;

function showImage() {
    images.forEach(image => image.classList.remove('active'));
    images[index].classList.add('active');
    index = (index + 1) % images.length;
}

// Mostrar la primera imagen al cargar la página
showImage();

// Cambiar de imagen cada 3 segundos (3000 milisegundos)
setInterval(showImage, 2000);


let animation = bodymovin.loadAnimation({
    container: document.querySelector('.animation-box'),
    renderer: 'svg',
    loop: true,
    autoplay: true,
    path: '/animations/welcome.json'
});