document.addEventListener("DOMContentLoaded", function() {
    const searchBox = document.querySelector(".searchBox");
    const searchInput = document.querySelector(".searchInput input");
    const searchIcon = document.querySelector(".searchBox .search");
    const closeIcon = document.querySelector(".searchBox .close");

    searchIcon.addEventListener("click", () => {
        searchBox.classList.add("active");
        searchInput.focus();
    });

    closeIcon.addEventListener("click", () => {
        searchBox.classList.remove("active");
        searchInput.value = "";
        filterTable("");
    });

    searchInput.addEventListener("input", (e) => {
        filterTable(e.target.value);
    });

    function filterTable(query) {
        const rows = document.querySelectorAll("#reservasTable tr");
        rows.forEach(row => {
            const cells = row.querySelectorAll("td");
            const matches = Array.from(cells).some(cell => cell.textContent.toLowerCase().includes(query.toLowerCase()));
            row.style.display = matches ? "" : "none";
        });
    }
});

document.addEventListener("DOMContentLoaded", function() {
    const errorMessage = document.querySelector('.error-message');
    const dateTimeInput = document.querySelector('input[type="datetime-local"]');

    if (errorMessage) {
        errorMessage.style.color = 'red';
        dateTimeInput.addEventListener('input', function() {
            errorMessage.style.display = 'none';
        });
    }
});



