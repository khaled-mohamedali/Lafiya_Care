package net.devcircuit.lafiyacare2025;

import java.util.ArrayList;

public class EmergencyResponse {

   private ArrayList<EmergencyPharmacy> Emergencies;

    public EmergencyResponse() {
    }

    public ArrayList<EmergencyPharmacy> getEmergencies() {
        return Emergencies;
    }

    @Override
    public String toString() {
        return "EmergencyResponse{" +
                "Emergencies=" + Emergencies +
                '}';
    }
}
