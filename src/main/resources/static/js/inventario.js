$(document).ready(function() {
    let search = document.querySelector('.search');
    let close = document.querySelector('.close');
    let searchBox = document.querySelector('.searchBox');

    search.onclick = function() {
        searchBox.classList.add('active');
    }

    close.onclick = function() {
        searchBox.classList.remove('active');
    }

    $("#searchInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#inventarioTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });
});