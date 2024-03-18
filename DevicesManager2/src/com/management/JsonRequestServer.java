package com.management;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JsonRequestServer {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/new1";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "admin";

    public static void main(String[] args) throws IOException {
        int serverPort = 8000; 
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/sensor-data", new SensorDataHandler());
        server.createContext("/api/appareils", new AppareilsHandler());
        server.createContext("/api/capteurs", new CapteursHandler());
        server.createContext("/api/actuateurs", new ActuateursHandler());
        server.createContext("/api/donnees", new DonneesHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Serveur démarré sur le port " + serverPort);
    }


    static class DonneesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                JSONArray jsonArray = new JSONArray();
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    // Récupération des données de la table DonneesCapteurs uniquement
                    String queryCapteurs = "SELECT * FROM DonneesCapteurs";
                    PreparedStatement statementCapteurs = connection.prepareStatement(queryCapteurs);
                    ResultSet resultSetCapteurs = statementCapteurs.executeQuery();
                    while (resultSetCapteurs.next()) {
                        JSONObject donneeCapteur = new JSONObject();
                        donneeCapteur.put("id", resultSetCapteurs.getInt("ID"));
                        donneeCapteur.put("valeur", resultSetCapteurs.getFloat("Valeur"));
                        // Utilisation de la méthode pour convertir la chaîne en Timestamp
                        donneeCapteur.put("timestamp",resultSetCapteurs.getString("Timestamp"));
                    //    donneeCapteur.put("capteur_id", resultSetCapteurs.getInt("Capteur_ID"));
                        donneeCapteur.put("type_capteur_id", resultSetCapteurs.getInt("TypeCapteur_ID"));
                        jsonArray.put(donneeCapteur);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
    
                String response = jsonArray.toString();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                String response = "Méthode non autorisée. Utilisez GET.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        }
    }
    

    
    static class AppareilsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                JSONArray jsonArray = new JSONArray();
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM objets";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        JSONObject objet = new JSONObject();
                        objet.put("id", resultSet.getInt("id"));
                        objet.put("nom", resultSet.getString("nom"));
                        objet.put("type", resultSet.getString("type"));
                        objet.put("categorie", resultSet.getString("categorie"));
                        objet.put("adresse_ip", resultSet.getString("adresse_ip"));
                        objet.put("localisation", resultSet.getString("location"));
                        objet.put("etat", resultSet.getInt("etat"));
                        jsonArray.put(objet);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String response = jsonArray.toString();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                String response = "Méthode non autorisée. Utilisez GET.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        }
    }

    static class ActuateursHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                JSONArray jsonArray = new JSONArray();
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM Actuateurs";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        JSONObject actuateur = new JSONObject();
                        actuateur.put("id", resultSet.getInt("id"));
                        actuateur.put("nom", resultSet.getString("nom"));
                        actuateur.put("modele", resultSet.getString("modele"));
                        // actuateur.put("numero_serie", resultSet.getString("numero_serie"));
                        actuateur.put("etat", resultSet.getInt("etat"));
                        jsonArray.put(actuateur);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String response = jsonArray.toString();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                String response = "Méthode non autorisée. Utilisez GET.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        }
    }

    static class CapteursHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                JSONArray jsonArray = new JSONArray();
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM capteurs";
                    PreparedStatement statement = connection.prepareStatement(query);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        JSONObject objet = new JSONObject();
                        objet.put("id", Integer.toString(resultSet.getInt("ID")));
                        objet.put("nom", resultSet.getString("Nom"));
                        objet.put("modele", resultSet.getString("Modele"));
                        objet.put("etat", resultSet.getInt("etat"));
                        jsonArray.put(objet);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                String response = jsonArray.toString();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            } else {
                String response = "Méthode non autorisée. Utilisez GET.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        }
    }

    static class SensorDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Récupérer la méthode de requête (POST, GET, etc.)
            String requestMethod = exchange.getRequestMethod();

            if (requestMethod.equalsIgnoreCase("POST")) {
                // Lire les données du corps de la requête
                InputStream inputStream = exchange.getRequestBody();
                StringBuilder sb = new StringBuilder();
                int nextByte;
                while ((nextByte = inputStream.read()) != -1) {
                    sb.append((char) nextByte);
                }

                // Convertir les données en String JSON
                String jsonRequest = sb.toString();
                System.out.println("Requête JSON reçue: " + jsonRequest);

                // Ajouter la requête JSON à la pile
                JsonRequestStack stack = new JsonRequestStack();
                stack.push(jsonRequest);

                // Envoyer une réponse au client
                String response = "Données JSON reçues avec succès.";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();

                // Insérer les données dans la base de données PostgreSQL
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String insertQuery = "INSERT INTO DonneesCapteurs (Valeur, Timestamp, Capteur_ID, TypeCapteur_ID) VALUES (?, ?, ?, ?)";

                    JSONObject jsonObject = new JSONObject(jsonRequest);
                    float valeur = (float) jsonObject.getDouble("temperature");
                    String timestamp = jsonObject.getString("time");
                    int capteurId = jsonObject.getInt("id");
                    int typeCapteurId = jsonObject.getInt("typeCapteurId");

                    // Première requête
                    try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                        statement.setFloat(1, valeur);
                        statement.setString(2, timestamp);
                        statement.setInt(3, capteurId);
                        statement.setInt(4, typeCapteurId);

                        // statement.executeUpdate();
                        System.out.println("Données insérées dans la table DonneesCapteurs (Capteur ID 1).");
                    }

                    // Deuxième requête
                    int typeCap2 = 2; // TypeCapteurId pour le deuxième capteur
                    String insertQuery2 = "INSERT INTO DonneesCapteurs (Valeur, Timestamp, Capteur_ID, TypeCapteur_ID) VALUES (?, ?, ?, ?)";

                    float valeur2 = (float) jsonObject.getDouble("humidite");
                    String timestamp2 = jsonObject.getString("time");
                    int capteurId2 = jsonObject.getInt("id");
                    // Vous pouvez définir le typeCapteurId pour le deuxième capteur ici, dans cet
                    // exemple c'est 2
                    int typeCapteurId2 = typeCap2;

                    try (PreparedStatement statement2 = connection.prepareStatement(insertQuery2)) {
                        statement2.setFloat(1, valeur2);
                        statement2.setString(2, timestamp2);
                        statement2.setInt(3, capteurId2);
                        statement2.setInt(4, typeCapteurId2);

                        statement2.executeUpdate();
                        System.out.println("Check");
                    }

                    // Troisième requête pour Capteur ID 3 (pourcentage de lumière)
                    int typeCap3 = 3; // TypeCapteurId pour le troisième capteur
                    String insertQuery3 = "INSERT INTO DonneesCapteurs (Valeur, Timestamp, Capteur_ID, TypeCapteur_ID) VALUES (?, ?, ?, ?)";

                    float valeur3 = (float) (85 + Math.random() * (86 - 85)); // Valeur aléatoire entre 85 et 86
                    String timestamp3 = jsonObject.getString("time");
                    int capteurId3 = jsonObject.getInt("id");
                    int typeCapteurId3 = typeCap3;

                    try (PreparedStatement statement3 = connection.prepareStatement(insertQuery3)) {
                        statement3.setFloat(1, valeur3);
                        statement3.setString(2, timestamp3);
                        statement3.setInt(3, capteurId3);
                        statement3.setInt(4, typeCapteurId3);

                        statement3.executeUpdate();
                        System.out.println("Données insérées dans la table DonneesCapteurs (Capteur ID 3).");
                    }
                } catch (SQLException e) {
                    System.err.println(
                            "Erreur lors de l'insertion des données dans la base de données : " + e.getMessage());
                }

            } else {
                // Méthode de requête non autorisée
                String response = "Méthode non autorisée. Utilisez POST.";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream outputStream = exchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }
}
