package com.management;

import java.util.LinkedList;

public class JsonRequestStack {
    private LinkedList<String> stack;

    public JsonRequestStack() {
        stack = new LinkedList<>();
    }

    // Ajouter une requête JSON à la pile
    public void push(String jsonRequest) {
        stack.push(jsonRequest);
    }

    // Récupérer et supprimer la requête JSON au sommet de la pile
    public String pop() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.pop();
    }

    // Récupérer la requête JSON au sommet de la pile sans la supprimer
    public String peek() {
        if (stack.isEmpty()) {
            return null;
        }
        return stack.peek();
    }

    // Vérifier si la pile est vide
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    // Vider la pile
    public void clear() {
        stack.clear();
    }

    // Récupérer la taille de la pile
    public int size() {
        return stack.size();
    }

    // Méthode pour afficher le contenu de la pile (pour le débogage)
    public void printStack() {
        for (String request : stack) {
            System.out.println(request);
        }
    }
}
