package com.yigitkavlak.cinfo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FuelRecyclerAdapter extends RecyclerView.Adapter<FuelRecyclerAdapter.FuelHolder> {

    private ArrayList<String> userFuelTypeList;
    private ArrayList<String> userFuelPriceList;
    private ArrayList<String> userFuelCompanyNameList;
    private ArrayList<String> userGasolineDistanceList;
    private ArrayList<Date> userFuelDateList;

    public FuelRecyclerAdapter(ArrayList<String> userFuelTypeList, ArrayList<String> userFuelPriceList, ArrayList<String> userFuelCompanyNameList, ArrayList<String> userGasolineDistanceList, ArrayList<Date> userFuelDateList) {
        this.userFuelTypeList = userFuelTypeList;
        this.userFuelPriceList = userFuelPriceList;
        this.userFuelCompanyNameList = userFuelCompanyNameList;
        this.userGasolineDistanceList = userGasolineDistanceList;
        this.userFuelDateList = userFuelDateList;
    }


    @NonNull
    @Override
    public FuelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_fuel, parent, false);
        return new FuelHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FuelHolder holder, int position) {
        holder.fuelTypeText.setText("Yakıt Tipi: " + userFuelTypeList.get(position));
        holder.fuelPriceText.setText("Yakıt Ücreti: " + userFuelCompanyNameList.get(position) + " TL");
        holder.fuelCompanyText.setText("Yakıt Şirketi: " + userFuelPriceList.get(position));
        holder.gasolineDistanceText.setText("Aracın Km'si: " + userGasolineDistanceList.get(position) + " KM");
        holder.fuelDateText.setText("Yakıt Alım Tarihi: " + userFuelDateList.get(position));
        holder.getAdapterPosition();


        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.fuelCompanyText.setBackgroundColor(currentColor);
        holder.fuelPriceText.setBackgroundColor(currentColor);
        holder.fuelTypeText.setBackgroundColor(currentColor);
        holder.gasolineDistanceText.setBackgroundColor(currentColor);
        holder.fuelDateText.setBackgroundColor(currentColor);
    }

    @Override
    public int getItemCount() {
        return userFuelCompanyNameList.size();
    }

    class FuelHolder extends RecyclerView.ViewHolder {

        TextView fuelTypeText;
        TextView fuelCompanyText;
        TextView fuelPriceText;
        TextView gasolineDistanceText;
        TextView fuelDateText;


        public FuelHolder(@NonNull View itemView) {

            super(itemView);

            fuelTypeText = itemView.findViewById(R.id.recyclerview_user_fueltype);
            fuelCompanyText = itemView.findViewById(R.id.recyclerview_user_fuelcompany);
            fuelPriceText = itemView.findViewById(R.id.recyclerview_user_fuelPrice);
            gasolineDistanceText = itemView.findViewById(R.id.recyclerview_user_gasolineDistance);
            fuelDateText = itemView.findViewById(R.id.recyclerview_user_fueldate);
        }
    }
}
