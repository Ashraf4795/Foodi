package com.city.coding.restaurant3.Model;

public class store {

    private String store_id;
    private String owner_id;
    private String owner_Fname;
    private String store_name;
    private String store_address;
    private String lati;
    private String longi;

    //constructor
    public store(String store_id, String owner_id, String owner_Fname
            , String store_name, String store_address, String lati, String longi) {
        this.store_id = store_id;
        this.owner_id = owner_id;
        this.owner_Fname = owner_Fname;
        this.store_name = store_name;
        this.store_address = store_address;
        this.lati = lati;
        this.longi = longi;
    }//end

    public String getStore_id() {
        return store_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public String getOwner_Fname() {
        return owner_Fname;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public String getLati() {
        return lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public void setOwner_Fname(String owner_Fname) {
        this.owner_Fname = owner_Fname;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}
