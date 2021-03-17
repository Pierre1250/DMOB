package com.example.projetmobile;




public class produit {
    public int statut;
    public String nomProduit;
    public String auteur;
    public int prix;
    public String imageURL;


    public produit(){}

    public produit(int statut, String nomProduit, String auteur, int prix, String imageURL) {
        this.statut = statut;
        this.nomProduit = nomProduit;
        this.auteur = auteur;
        this.prix = prix;
        this.imageURL = imageURL;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getPrix() {
        return prix;
    }

    public String getImageURL() {
        return imageURL;
    }
    public int statut() {
        return statut;
    }
}
