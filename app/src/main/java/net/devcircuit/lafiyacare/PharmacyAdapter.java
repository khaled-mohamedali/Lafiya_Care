package net.devcircuit.lafiyacare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return this.pharmacies.size();
    }

    public static class PharmacyViewHolder extends RecyclerView.ViewHolder{

       TextView pharmacyName;
        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            pharmacyName = itemView.findViewById(R.id.pharmacyName);
        }
    }
}
