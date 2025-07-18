/*document.addEventListener('DOMContentLoaded', function() {
    // Aggiunge una conferma per tutti i link di cancellazione
    const deleteLinks = document.querySelectorAll('.delete-link');
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            if (!confirm('Sei sicuro di voler cancellare questo elemento?')) {
                event.preventDefault();
            }
        });
    });
});
*/

// Aggiunge una finestra di dialogo di conferma a tutti i link con la classe 'delete-link'
document.addEventListener('DOMContentLoaded', function() {
    const deleteLinks = document.querySelectorAll('.delete-link');
    
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            if (!confirm('Sei sicuro di voler eliminare questo elemento?')) {
                event.preventDefault(); // Annulla l'azione predefinita (il click sul link)
            }
        });
    });
});