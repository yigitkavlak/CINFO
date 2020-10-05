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

public class PaymentRecyclerAdapter extends RecyclerView.Adapter<PaymentRecyclerAdapter.PaymentHolder> {

    private ArrayList<String> userPaymentTypeList;
    private ArrayList<String> userPaymentPriceList;
    private ArrayList<Date> userPaymentDateList;

    public PaymentRecyclerAdapter(ArrayList<String> userPaymentTypeList, ArrayList<String> userPaymentPriceList, ArrayList<Date> userPaymentDateList) {
        this.userPaymentTypeList = userPaymentTypeList;
        this.userPaymentPriceList = userPaymentPriceList;
        this.userPaymentDateList = userPaymentDateList;
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rcycler_payment, parent, false);

        return new PaymentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {

        holder.paymentTypeText.setText("Ödeme Tipi: " + userPaymentTypeList.get(position));
        holder.paymentPriceText.setText("Ödeme Miktarı: " + userPaymentPriceList.get(position) +" TL" );
        holder.paymentDateText.setText("Ödeme Tarihi" + userPaymentDateList.get(position));


        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        holder.paymentTypeText.setBackgroundColor(currentColor);
        holder.paymentPriceText.setBackgroundColor(currentColor);
        holder.paymentDateText.setBackgroundColor(currentColor);

    }

    @Override
    public int getItemCount() {
        return userPaymentDateList.size();
    }

    class PaymentHolder extends RecyclerView.ViewHolder{

        TextView paymentTypeText;
        TextView paymentPriceText;
        TextView paymentDateText;


        public PaymentHolder(@NonNull View itemView) {
            super(itemView);

            paymentTypeText = itemView.findViewById(R.id.recyclerview_user_paymenttype);
            paymentPriceText = itemView.findViewById(R.id.recyclerview_user_paymentPrice);
            paymentDateText = itemView.findViewById(R.id.recyclerview_user_paymentDate);
        }
    }
}
