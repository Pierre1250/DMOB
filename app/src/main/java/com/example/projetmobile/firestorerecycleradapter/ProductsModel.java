package com.example.projetmobile.firestorerecycleradapter;

public class ProductsModel  {
    private  String img_pro;
    private  String lien_pro ;
    private  String nomPro ;
    private  long prix_pro;

    public String getType_pro() {
        return type_pro;
    }

    public void setType_pro(String type_pro) {
        this.type_pro = type_pro;
    }

    private String type_pro;

    public ProductsModel(String img_pro, String lien_pro, String nomPro, long prix_pro, String type_pro) {
        this.img_pro = img_pro;
        this.lien_pro = lien_pro;
        this.nomPro = nomPro;
        this.prix_pro = prix_pro;
        this.type_pro = type_pro;
    }

    public String getLien_pro() {
        return lien_pro;
    }

    public void setLien_pro(String lien_pro) {
        this.lien_pro = lien_pro;
    }

    public String getImg_pro() {
        return img_pro;
    }

    public void setImg_pro(String img_pro) {
        this.img_pro = img_pro;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public long getPrix_pro() {
        return prix_pro;
    }

    public void setPrix_pro(long prix_pro) {
        this.prix_pro = prix_pro;
    }

    public ProductsModel(){};




}




