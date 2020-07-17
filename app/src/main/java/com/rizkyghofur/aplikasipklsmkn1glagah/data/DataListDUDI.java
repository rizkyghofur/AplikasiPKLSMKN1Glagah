package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataListDUDI {
    private String id, nama_dudi;

    public DataListDUDI() {
    }

    public DataListDUDI(String id, String nama_dudi) {
        this.id = id;
        this.nama_dudi = nama_dudi;
    }

    public String getId_dudi() {
        return id;
    }

    public void setId_dudi(String id) {
        this.id = id;
    }

    public String getNama_dudi() {
        return nama_dudi;
    }

    public void setNama_dudi(String nama_dudi) {
        this.nama_dudi = nama_dudi;
    }

}
