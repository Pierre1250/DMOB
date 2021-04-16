package com.example.projetmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class adapterPanier extends BaseAdapter {
    private ArrayList<product> liste;
    private Context context;
    private LayoutInflater inflater;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =inflater.inflate(R.layout.line_panier,null);
        TextView nom=convertView.findViewById(R.id.nom_li);
        TextView prix=convertView.findViewById(R.id.pri_li);

        nom.setText(liste.get(position).getNomPro());
        prix.setText((int) liste.get(position).getPrix_pro());
        return convertView;
    }

    public adapterPanier(Context context, ArrayList<product> liste){
        this.context=context;
        this.inflater=(LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.liste=liste;
    }
}
