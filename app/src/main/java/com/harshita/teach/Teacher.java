package com.harshita.teach;

import java.io.Serializable;

public class Teacher implements Comparable, Serializable {

    private String name;
    private String major;
    private String[] subjects = new String[3];
   private String priceRange;
    private int imageID;
    private double lat;
    private double lng;
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Teacher(String name, String major, String[] subjects, String priceRange, int imageID, double lat, double lng) {
        this.name = name;
        this.major = major;
        this.subjects = subjects;
        this.priceRange = priceRange;
        this.imageID = imageID;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int compareTo(Object o) {
        int compareDist = (int) ((Teacher) o).getDistance();
        return (int) this.distance - compareDist;
    }
}
