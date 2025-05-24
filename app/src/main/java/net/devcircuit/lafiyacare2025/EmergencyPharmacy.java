package net.devcircuit.lafiyacare2025;

public class EmergencyPharmacy {

    private String name;
    private  String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmergencyPharmacy{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
