package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponStatus {

    @SerializedName("status_kode")
    @Expose
    private int status_kode;
    @SerializedName("status_pesan")
    @Expose
    private String status_pesan;

    public ResponStatus(int status_kode, String status_pesan) {
        this.status_kode = status_kode;
        this.status_pesan = status_pesan;
    }

    public int getStatus_kode() {
        return status_kode;
    }

    public void setStatus_kode(int status_kode) {
        this.status_kode = status_kode;
    }

    public String getStatus_pesan() {
        return status_pesan;
    }

    public void setStatus_pesan(String status_pesan) {
        this.status_pesan = status_pesan;
    }
}