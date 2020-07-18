package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataMapel {
    private String id, nama_mapel;

    public DataMapel() {
    }

    public DataMapel(String id, String nama_mapel) {
        this.id = id;
        this.nama_mapel = nama_mapel;
    }

    public String getId_mapel() {
        return id;
    }

    public void setId_mapel(String id) {
        this.id = id;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public void setNama_mapel(String nama_mapel) {
        this.nama_mapel = nama_mapel;
    }

}
