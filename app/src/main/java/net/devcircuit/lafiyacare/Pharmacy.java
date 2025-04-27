package net.devcircuit.lafiyacare;
import java.util.List;

public class Pharmacy {

        private String name;
        private Address address;
        private String phone;
        private String openHours;
        private double rating;
        private boolean isEmergency;

        private  String placeId;
        public Pharmacy(String name){
            this.name = name;
        }

        // Constructor
        public Pharmacy(String placeId,String name, Address address, String phone, String openHours, boolean isOpenNow,
                        double distanceKm, double rating,
                        String imageUrl, boolean isEmergency) {
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.openHours = openHours;
            this.rating = rating;
            this.placeId = placeId;
            this.isEmergency = isEmergency;
        }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    // Getters and Setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOpenHours() {
            return openHours;
        }

        public void setOpenHours(String openHours) {
            this.openHours = openHours;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }


        public boolean isEmergency() {
            return isEmergency;
        }

        public void setEmergency(boolean emergency) {
            isEmergency = emergency;
        }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "name='" + name + '\'' +
                ", isEmergency=" + isEmergency +
                '}';
    }
}


