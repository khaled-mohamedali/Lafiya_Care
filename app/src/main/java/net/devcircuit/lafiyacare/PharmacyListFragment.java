package net.devcircuit.lafiyacare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class PharmacyListFragment extends Fragment {


   private ArrayList<Pharmacy> pharmacyList;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getData(new PharmacyCallback() {
            @Override
            public void onCallback(ArrayList<Pharmacy> pharmacies) {
                pharmacyList = pharmacies;
                PharmacyAdapter adapter = new PharmacyAdapter(pharmacyList);
                recyclerView.setAdapter(adapter);
            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_list, container, false);
    }

    public void getData(PharmacyCallback callback){
        //Connecting to firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<Pharmacy> pharmacyList = new ArrayList<>();

        //Getting the data from firebase
            db.collection("pharmacies_niamey")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            String json = gson.toJson(document.getData());
                            Pharmacy pharmacy = gson.fromJson(json, Pharmacy.class);
                            pharmacyList.add(pharmacy);
                        }

                        //get the list of IDs of the emergencies pharmacies from firebase

                        db.collection("pharmacies_de_garde")
                                .document("current")
                                        .get()
                                                .addOnSuccessListener(gardeDoc -> {
                                                    if(gardeDoc.exists()) {
                                                        ArrayList<String> pharmacyIds =  (ArrayList<String>)(gardeDoc.get("placesIDs"));

                                                        for (Pharmacy pharmacy : pharmacyList) {
                                                            if (pharmacyIds.contains(pharmacy.getPlaceId())) {
                                                                pharmacy.setEmergency(true);
                                                            } else {
                                                                pharmacy.setEmergency(false);
                                                            }

                                                        }
                                                    }

                                                    callback.onCallback(pharmacyList);
                                                });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}