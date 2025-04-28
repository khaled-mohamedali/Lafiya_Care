package net.devcircuit.lafiyacare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
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
import java.util.Collection;
import java.util.Collections;

public class PharmacyListFragment extends Fragment {


   private ArrayList<Pharmacy> pharmacyList;

    RecyclerView recyclerView;
    SearchView searchView;
    PharmacyAdapter adapter;

    ImageView sortMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.search);
        sortMenu = view.findViewById(R.id.sortBtn);



        sortMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showSortMenu(v);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter != null){
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getData(new PharmacyCallback() {
            @Override
            public void onCallback(ArrayList<Pharmacy> pharmacies) {
                pharmacyList = pharmacies;
                 adapter = new PharmacyAdapter(pharmacyList);
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

    public void showSortMenu(View anchor){
        PopupMenu popupMenu = new PopupMenu(requireContext(), anchor);

        popupMenu.getMenuInflater().inflate(R.menu.sorting_menu,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {

            int id = item.getItemId();
            if (id == R.id.sort_by_name) {
                sortByName();
                return true;
            }else if (id==R.id.sort_by_garde){
                sortByGarde();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    public void sortByName(){
        pharmacyList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        adapter.notifyDataSetChanged();
    }

    public void sortByGarde(){
        pharmacyList.sort((o1, o2) -> {
            if (o1.isEmergency() && !o2.isEmergency()) {
                return -1; // o1 emergency, o2 normal -> o1 comes first
            } else if (!o1.isEmergency() && o2.isEmergency()) {
                return 1; // o1 normal, o2 emergency -> o2 comes first
            } else {
                return 0; // both same (both emergency or both normal) -> no change
            }
        });
        adapter.notifyDataSetChanged();
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