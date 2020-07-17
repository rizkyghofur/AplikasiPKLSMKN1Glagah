package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataCatatanKunjunganPKL {

    private String id_catatan_kunjungan_pkl, id_guru, tanggal_kunjungan, catatan_pembimbing, nama_dudi;
    public DataCatatanKunjunganPKL() {
    }

    public DataCatatanKunjunganPKL(String id_catatan_kunjungan_pkl, String id_guru, String tanggal_kunjungan, String catatan_pembimbing, String nama_dudi) {
        this.id_catatan_kunjungan_pkl = id_catatan_kunjungan_pkl;
        this.id_guru = id_guru;
        this.tanggal_kunjungan = tanggal_kunjungan;
        this.catatan_pembimbing = catatan_pembimbing;
        this.nama_dudi = nama_dudi;
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

    public String getNama_dudi() {
        return nama_dudi;
    }

    public void setNama_dudi(String nama_dudi){
        this.nama_dudi = nama_dudi;
    }
}
