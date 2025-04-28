package net.devcircuit.lafiyacare;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.text.WordUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder>implements Filterable {

    ArrayList<Pharmacy> pharmacies;
    private ArrayList<Pharmacy> pharmaciesFull; // backup copy for filtering

    public PharmacyAdapter(ArrayList<Pharmacy> pharmacies){
        this.pharmacies = pharmacies;
        this.pharmaciesFull = new ArrayList<>(pharmacies);

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
            String formattedName = WordUtils.capitalizeFully(pharmacy.getName());
            holder.pharmacyName.setText(formattedName);
            holder.openingHours.setText(pharmacy.getOpenHours());
            holder.badge.setVisibility(pharmacy.isEmergency() ? View.VISIBLE : View.GONE);
            holder.rating.setText(String.valueOf(pharmacy.getRating()));
            String status = getPharmacyStatus(pharmacy.getOpenHours(),pharmacy);
            holder.status.setText(status);
            holder.status.setTextColor(status.equals("Ouvert") ? holder.itemView.getContext().getColor(R.color.green) : holder.itemView.getContext().getColor(R.color.red));


            // Get direction to the pharmacy
            holder.pharmacyAddress.setOnClickListener(v -> {
                Address address = pharmacy.getAddress();

                Uri gmmIntentUri = Uri.parse("google.navigation:q="+address.getLatitude()+","+ address.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

           try {
               v.getContext().startActivity(mapIntent);
           }catch (ActivityNotFoundException e){
               Toast.makeText(v.getContext(), "Google Maps n'est pas installé", Toast.LENGTH_SHORT).show();
           }
//
            });

//           Make a call to the pharmacy
            holder.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Get the number
                    String phoneNumber = pharmacy.getPhone();

                    // Check if valid phone number
                    if (!phoneNumber.isEmpty()) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + phoneNumber));
                        v.getContext().startActivity(callIntent);
                    }else {
                        //Create a toast saying that the phone number is not valid
                        Toast.makeText(v.getContext(), "La Pharmcie n'a pas de numéro de téléphone", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }

    @Override
    public int getItemCount() {
        return this.pharmacies.size();
    }

    @Override
    public Filter getFilter() {
        return pharmacyFilter;
    }

    private Filter pharmacyFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Pharmacy> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                        filteredList.addAll(pharmaciesFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Pharmacy item: pharmaciesFull){
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;


            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            pharmacies.clear();
            pharmacies.addAll((ArrayList<Pharmacy>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class PharmacyViewHolder extends RecyclerView.ViewHolder{

       TextView pharmacyName, pharmacyAddress, openingHours,status,badge,rating;
       ImageView phone;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            pharmacyName = itemView.findViewById(R.id.pharmacyName);
            pharmacyAddress = itemView.findViewById(R.id.direction);
            status = itemView.findViewById(R.id.status);
            openingHours = itemView.findViewById(R.id.openingHours);
            badge = itemView.findViewById(R.id.emergency);
            rating = itemView.findViewById(R.id.rating);
            phone = itemView.findViewById(R.id.phone);

        }
    }

    public static String getPharmacyStatus(String openingHours,Pharmacy pharmacy) {
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


            if ((!now.isBefore(openTime) && now.isBefore(closeTime))|| pharmacy.isEmergency()) {
                return "Ouvert";
            } else {
                return "Fermé";
            }
        } catch (Exception e) {
            return "Heures non valides";
        }
    }

}
