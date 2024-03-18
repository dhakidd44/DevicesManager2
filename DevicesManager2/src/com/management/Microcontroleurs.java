package com.management;

import java.util.ArrayList;
import java.util.List;

public class Microcontroleurs  {
    private List<Capteur> capteurs;
    private List<Actuateur> actuateurs;

    public Microcontroleurs(int id, String nom, String type, String categorie, String location, String adresse_ip, int etat) {
        this.capteurs = new ArrayList<>();
        this.actuateurs = new ArrayList<>();
    }

    public void addCapteur(Capteur capteur) {
        capteurs.add(capteur);
    }

    public void addActionneur(Actuateur actuateur) {
        actuateurs.add(actuateur);
    }

    public List<Capteur> getCapteurs() {
        return capteurs;
    }

    public List<Actuateur> getActuateurs() {
        return actuateurs;
    }
}
