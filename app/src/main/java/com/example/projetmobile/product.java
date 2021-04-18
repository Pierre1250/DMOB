package com.example.projetmobile;

import android.os.Parcel;
import android.os.Parcelable;

public class product implements Parcelable {

    private  String nomPro ;
    private  long prix_pro;

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

    public  product(){
        super();
    }

    public product(Parcel parcel){

        this.nomPro = parcel.readString();
        this.prix_pro = parcel.readLong();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nomPro);
        parcel.writeLong(prix_pro);
    }

    public  static  final Creator<product>CREATOR=new Creator<product>() {
        @Override
        public product createFromParcel(Parcel parcel) {
            return new  product(parcel);

        }

        @Override
        public product[] newArray(int i) {
            return new product[i];
        }
    };


}
