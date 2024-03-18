package com.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ObjetConnecteTraitement {

    private Connection connection;

    public ObjetConnecteTraitement(Connection connection) {
        this.connection = connection;
    }

    public void addObjetConnecte(ObjetConnecte objetConnecte) throws SQLException {
        String query = "INSERT INTO nom_de_votre_table (nom, type, categorie, location, adresse_ip, etat, timestamp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, objetConnecte.getNom());
            preparedStatement.setString(2, objetConnecte.getType());
            preparedStatement.setString(3, objetConnecte.getCategorie());
            preparedStatement.setString(4, objetConnecte.getLocation());
            preparedStatement.setString(5, objetConnecte.getAdresseIp());
            preparedStatement.setInt(6, objetConnecte.getEtat());
            preparedStatement.setTimestamp(7, new java.sql.Timestamp(objetConnecte.getTimestamp().getTime()));
            preparedStatement.executeUpdate();
        }
    }

    public static void insertObjet(Connection connection, ObjetConnecte objet) throws SQLException {
        String insertQuery = "INSERT INTO Objets (Nom, Type, Categorie, adresse_ip, Location, Etat) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, objet.getNom());
            preparedStatement.setString(2, objet.getType());
            preparedStatement.setString(3, objet.getCategorie());
            preparedStatement.setInt(6, objet.getEtat());
            preparedStatement.setString(4, objet.getAdresseIp());
            preparedStatement.executeUpdate();
        }
    }

    public static void insertDonneeCapteur(Connection connection, float valeur, Timestamp timestamp, int capteurId, int typeCapteurId) throws SQLException {
        String insertQuery = "INSERT INTO DonneesCapteurs (Valeur, Timestamp, Capteur_ID, TypeCapteur_ID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setFloat(1, valeur);
            preparedStatement.setTimestamp(2, timestamp);
            preparedStatement.executeUpdate();
        }
    }
}
