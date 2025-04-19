package net.devcircuit.lafiyacare;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class PharmacyListFragment extends Fragment {


    ArrayList<Pharmacy> pharmacyList;

    ArrayAdapter<Pharmacy> adapter;

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pharmacyList = new ArrayList<>();
        // Add Pharmacy instances to the list
        pharmacyList.add(new Pharmacy("Pharmacie Ridwane"));
        pharmacyList.add(new Pharmacy("Pharmacie Recasement"));
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

        listView = view.findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, pharmacyList);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_list, container, false);
    }
}