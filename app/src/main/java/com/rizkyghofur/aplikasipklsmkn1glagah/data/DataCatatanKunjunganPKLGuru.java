package com.rizkyghofur.aplikasipklsmkn1glagah.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCatatanKunjunganPKLGuru {

    @SerializedName("id_catatan_kunjungan_pkl")
    @Expose
    private String id_catatan_kunjungan_pkl;
    @SerializedName("id_guru")
    @Expose
    private String id_guru;
    @SerializedName("tanggal_kunjungan")
    @Expose
    private String tanggal_kunjungan;
    @SerializedName("catatan_pembimbing")
    @Expose
    private String catatan_pembimbing;

    public DataCatatanKunjunganPKLGuru(String id_catatan_kunjungan_pkl, String id_guru, String tanggal_kunjungan, String catatan_pembimbing) {
        this.id_catatan_kunjungan_pkl = id_catatan_kunjungan_pkl;
        this.id_guru = id_guru;
        this.tanggal_kunjungan = tanggal_kunjungan;
        this.catatan_pembimbing = catatan_pembimbing;
    }

    public String getId_catatan_kunjungan_pkl() {
        return id_catatan_kunjungan_pkl;
    }

    public void setId_catatan_kunjungan_pkl(String id_catatan_kunjungan_pkl) {
        this.id_catatan_kunjungan_pkl = id_catatan_kunjungan_pkl;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getTanggal_kunjungan() {
        return tanggal_kunjungan;
    }

    public void setTanggal_kunjungan(String tanggal_kunjungan) {
        this.tanggal_kunjungan = tanggal_kunjungan;
    }

    public String getCatatan_pembimbing() {
        return catatan_pembimbing;
    }

    public void setCatatan_pembimbing(String catatan_pembimbing) {
        this.catatan_pembimbing = catatan_pembimbing;
    }
}