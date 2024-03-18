document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("ajout-form-capteur");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(form);
        const jsonObject = {};
        formData.forEach(function (value, key) {
            jsonObject[key] = value;
        });

        const url = "http://localhost:8000/api/capteurs"; // URL de votre API Java

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
            // Actualiser la liste des capteurs après l'ajout
            populateCapteursTable(); // Appel de la fonction pour mettre à jour la table des capteurs
            form.reset(); // Réinitialiser le formulaire après l'envoi réussi
            closeFormCapteur(); // Cacher le formulaire après l'envoi réussi
        })
        .catch(error => {
            console.error("Erreur lors de l'envoi des données :", error);
            // Gérer les erreurs, par exemple afficher un message à l'utilisateur
        });
    });

    // Fonction pour afficher le formulaire d'ajout de capteur
    document.getElementById("btn-ajouter-capteur").addEventListener("click", function() {
        var formSection = document.querySelector(".ajout-capteur");
        formSection.style.display = "block";
    });

    // Fonction pour masquer le formulaire d'ajout de capteur
    function closeFormCapteur() {
        var formSection = document.querySelector(".ajout-capteur");
        formSection.style.display = "none";
    }

    // Fonction pour récupérer et afficher les capteurs
    function populateCapteursTable() {
        const capteursTable = document.getElementById("capteurs-table");
        capteursTable.innerHTML = ""; // Nettoyer le contenu actuel de la table

        fetch("http://localhost:8000/api/capteurs")
            .then(response => response.json())
            .then(data => {
                data.forEach(capteur => {
                    const row = capteursTable.insertRow();
                    row.innerHTML = `
                        <td>${capteur.nom}</td>
                        <td>${capteur.modele}</td>
                        <td>
                        <button class="btn-supprimer" data-id="${capteur.id}">Supprimer</button>
                    </td>
                        
                    `;
                });
            })
            .catch(error => {
                console.error("Erreur lors de la récupération des données des capteurs :", error);
            });
    }

    // Appel initial pour afficher les capteurs au chargement de la page
    populateCapteursTable();

    // Fonction pour revenir à la page précédente
    function goBack() {
        window.history.back();
    }

    // Écouter le clic sur le bouton "Retour" pour fermer le formulaire
    document.getElementById("btn-retour").addEventListener("click", function() {
        closeFormCapteur(); // Appel de la fonction pour masquer le formulaire
    });
});
