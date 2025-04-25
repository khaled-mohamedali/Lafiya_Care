package net.devcircuit.lafiyacare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>{

    ArrayList<Pharmacy> pharmacies;
    public PharmacyAdapter(ArrayList<Pharmacy> pharmacies){
        this.pharmacies = pharmacies;
    }

    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view   = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacy_cards_item,parent,false);

        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
            Pharmacy pharmacy = pharmacies.get(position);
            holder.pharmacyName.setText(pharmacy.getName());
            holder.openingHours.setText(pharmacy.getOpenHours());
            holder.badge.setVisibility(pharmacy.isEmergency() ? View.VISIBLE : View.GONE);
            holder.rating.setText(String.valueOf(pharmacy.getRating()));

            String status = getPharmacyStatus(pharmacy.getOpenHours());
            holder.status.setText(status);
            holder.status.setTextColor(status.equals("Ouvert") ? holder.itemView.getContext().getColor(R.color.green) : holder.itemView.getContext().getColor(R.color.red));




    }

    @Override
    public int getItemCount() {
        return this.pharmacies.size();
    }

    public static class PharmacyViewHolder extends RecyclerView.ViewHolder{

       TextView pharmacyName, pharmacyAddress, openingHours,status,badge,rating;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            pharmacyName = itemView.findViewById(R.id.pharmacyName);
            pharmacyAddress = itemView.findViewById(R.id.direction);
            status = itemView.findViewById(R.id.status);
            openingHours = itemView.findViewById(R.id.openingHours);
            badge = itemView.findViewById(R.id.emergency);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    public static String getPharmacyStatus(String openingHours) {
        if (openingHours == null || !openingHours.contains("-")) {
            return "Heures non disponibles";
        }

        try {
            String[] parts = openingHours.split("-");
            String openTimeStr = parts[0].trim().replace("h", ":");
            String closeTimeStr = parts[1].trim().replace("h", ":");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
            LocalTime openTime = LocalTime.parse(openTimeStr, formatter);
            LocalTime closeTime = LocalTime.parse(closeTimeStr, formatter);

            LocalTime now = LocalTime.now();

            if (!now.isBefore(openTime) && now.isBefore(closeTime)) {
                return "Ouvert";
            } else {
                return "Fermé";
            }
        } catch (Exception e) {
            return "Heures non valides";
        }
    }

}
