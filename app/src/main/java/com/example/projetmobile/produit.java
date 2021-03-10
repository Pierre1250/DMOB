package com.example.projetmobile;




public class produit {
    public String nomProduit;
    public String auteur;
    public int prix;
    public String imageURL;
    public produit(){}

    public produit(String nomProduit, String auteur, int prix, String imageURL) {
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

}
