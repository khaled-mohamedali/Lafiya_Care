package net.devcircuit.lafiyacare;
import java.util.List;

public class Pharmacy {



        private String name;
        private Address address;
        private String phone;
        private String openHours;
        private boolean isOpenNow;
        private double distanceKm;
        private double rating;
        private String imageUrl;
        private boolean isEmergency;
        public Pharmacy(String name){
            this.name = name;
        }

        // Constructor
        public Pharmacy(String name, Address address, String phone, String openHours, boolean isOpenNow,
                        double distanceKm, double rating,
                        String imageUrl, boolean isEmergency) {
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.openHours = openHours;
            this.isOpenNow = isOpenNow;
            this.distanceKm = distanceKm;
            this.rating = rating;

            this.imageUrl = imageUrl;
            this.isEmergency = isEmergency;
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

        public boolean isOpenNow() {
            return isOpenNow;
        }

        public void setOpenNow(boolean openNow) {
            isOpenNow = openNow;
        }

        public double getDistanceKm() {
            return distanceKm;
        }

        public void setDistanceKm(double distanceKm) {
            this.distanceKm = distanceKm;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }


        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isEmergency() {
            return isEmergency;
        }

        public void setEmergency(boolean emergency) {
            isEmergency = emergency;
        }

        public String toString() {
            return this.name;
        }
}


