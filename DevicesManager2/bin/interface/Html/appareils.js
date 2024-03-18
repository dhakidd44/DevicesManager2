document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("ajout-form");
    const appareilsTable = document.getElementById("appareils-table");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(form);
        const jsonObject = {};
        formData.forEach(function (value, key) {
            jsonObject[key] = value;
        });

        const url = "client/api"; // Mettez ici l'URL de votre API client

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(jsonObject)
        })
        .then(response => response.json())
        .then(data => {
            console.log("Réponse du serveur :", data);
            form.reset(); // Réinitialiser le formulaire après l'envoi réussi
            refreshAppareilsTable(); // Actualiser la liste des appareils après l'ajout
        })
        .catch(error => {
            console.error("Erreur lors de l'envoi des données :", error);
            // Gérer les erreurs, par exemple afficher un message à l'utilisateur
        });
    });

    // Fonction pour afficher le formulaire d'ajout
    document.getElementById("btn-ajouter").addEventListener("click", function() {
        var formSection = document.querySelector(".ajout-appareil");
        formSection.style.display = "block";
    });

    // Fonction pour masquer le formulaire d'ajout
    function closeForm() {
        var formSection = document.querySelector(".ajout-appareil");
        formSection.style.display = "none";
    }

    // Fonction pour actualiser la liste des appareils
    function refreshAppareilsTable() {
        fetch("http://localhost:8000/api/appareils")
            .then(response => response.json())
            .then(data => {
                appareilsTable.innerHTML = ""; // Effacer le contenu existant de la table
                populateAppareilsTable(data); // Remplir la table avec les données reçues
            })
            .catch(error => {
                console.error("Erreur lors de la récupération des données des appareils :", error);
            });
    }

// Fonction pour remplir la table des appareils avec les données reçues
function populateAppareilsTable(appareils) {
    appareils.forEach(appareil => {
        const row = appareilsTable.insertRow();
        row.innerHTML = `
            <td>${appareil.nom}</td>
            <td>${appareil.type}</td>
            <td>${appareil.categorie}</td>
            <td>${appareil.adresse_ip}</td>
            <td>${appareil.localisation}</td>
            <td>${appareil.etat === 1 ? "Actif" : "Inactif"}</td>
        `;
    });
}

    // Gestionnaire d'événement pour le bouton "Supprimer"
        const btnSupprimer = row.querySelector('.btn-supprimer');
        btnSupprimer.addEventListener('click', () => {
            const appareilId = btnSupprimer.getAttribute('data-id');
            if (confirm(`Voulez-vous vraiment supprimer l'appareil avec l'ID ${appareilId} ?`)) {
                supprimerAppareil(appareilId);
            }
        });

    // Appeler la fonction pour charger la liste des appareils au chargement de la page
    refreshAppareilsTable();
});

function goBack() {
    window.history.back();
}

