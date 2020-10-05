package com.yigitkavlak.cinfo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class TireRecyclerAdapter extends RecyclerView.Adapter<TireRecyclerAdapter.TireHolder> {

    private ArrayList<String> userTireTypeList;
    private ArrayList<String> userTirePriceList;
    private ArrayList<String> userTireDistanceList;
    private ArrayList<Date> userTireDateList;

    public TireRecyclerAdapter(ArrayList<String> userTireTypeList, ArrayList<String> userTirePriceList, ArrayList<String> userTireDistanceList, ArrayList<Date> userTireDateList) {
        this.userTireTypeList = userTireTypeList;
        this.userTirePriceList = userTirePriceList;
        this.userTireDistanceList = userTireDistanceList;
        this.userTireDateList = userTireDateList;
    }

    @NonNull
    @Override
    public TireHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_tire,parent,false);


        return new TireHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TireHolder holder, int position) {

        holder.tireTypeText.setText("Lastik Tipi: " +userTireTypeList.get(position) );
        holder.tirePriceText.setText("Değişim Fiyatı: " + userTirePriceList.get(position) +" TL");
        holder.tireDistanceText.setText("Araç Km: " + userTireDistanceList.get(position) + " KM");
        holder.tireDateText.setText("Değişim Tarihi: " + userTireDateList.get(position));

        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        holder.tireTypeText.setBackgroundColor(currentColor);
        holder.tirePriceText.setBackgroundColor(currentColor);
        holder.tireDistanceText.setBackgroundColor(currentColor);
        holder.tireDateText.setBackgroundColor(currentColor);

    }

    @Override
    public int getItemCount() {
        return userTireDateList.size();
    }

    class TireHolder extends RecyclerView.ViewHolder{

        TextView tireTypeText;

        TextView tirePriceText;
        TextView tireDistanceText;
        TextView tireDateText;


        public TireHolder(@NonNull View itemView) {
            super(itemView);

            tireTypeText = itemView.findViewById(R.id.recyclerview_user_tiretype);
            tirePriceText = itemView.findViewById(R.id.recyclerview_user_tirePrice);
            tireDistanceText = itemView.findViewById(R.id.recyclerview_user_tireDistance);
            tireDateText = itemView.findViewById(R.id.recyclerview_user_tireDate);
        }
    }
}
