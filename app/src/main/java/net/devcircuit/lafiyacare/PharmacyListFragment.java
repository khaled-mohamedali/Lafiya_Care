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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PharmacyListFragment extends Fragment {


    ArrayList<Pharmacy> pharmacyList;

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            getData();
        pharmacyList = new ArrayList<>();
        // Add Pharmacy instances to the list
        pharmacyList.add(new Pharmacy("Pharmacie Ridwane"));
        pharmacyList.add(new Pharmacy("Pharmacie Recasement Recasement"));
        pharmacyList.add(new Pharmacy("Pharmacie Sixième"));
        pharmacyList.add(new Pharmacy("Pharmacie des Arènes"));
        pharmacyList.add(new Pharmacy("Pharmacie de l’Avenir"));
        pharmacyList.add(new Pharmacy("Pharmacie Cité BECEAO"));
        pharmacyList.add(new Pharmacy("Pharmacie EL NASR"));
        pharmacyList.add(new Pharmacy("Pharmacie Sira"));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        PharmacyAdapter adapter = new PharmacyAdapter(pharmacyList);

        recyclerView.setAdapter(adapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_list, container, false);
    }

    public void getData(){
        //Connecting to firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Getting the data from firebase
        db.collection("pharmacies")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.d("TAGIR", document.getId() + " => " + document.getData());
                            }
                        }else{
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}