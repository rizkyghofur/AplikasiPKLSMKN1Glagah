package com.rizkyghofur.aplikasipklsmkn1glagah.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataJurnalPKL {

    @SerializedName("id_jurnal_pkl")
    @Expose
    private String id_jurnal_pkl;
    @SerializedName("id_siswa")
    @Expose
    private String id_siswa;
    @SerializedName("kelas")
    @Expose
    private String kelas;
    @SerializedName("kompetensi_dasar")
    @Expose
    private String kompetensi_dasar;
    @SerializedName("topik_pekerjaan")
    @Expose
    private String topik_pekerjaan;
    @SerializedName("nama_dudi")
    @Expose
    private String id_dudi;
    @SerializedName("tanggal")
    @Expose
    private String tanggal;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("catatan")
    @Expose
    private String catatan;

    public DataJurnalPKL(String id_jurnal_pkl, String id_siswa, String kelas, String kompetensi_dasar, String tanggal, String topik_pekerjaan, String id_dudi, String status, String catatan) {
        this.id_jurnal_pkl = id_jurnal_pkl;
        this.id_siswa = id_siswa;
        this.kelas = kelas;
        this.kompetensi_dasar = kompetensi_dasar;
        this.tanggal = tanggal;
        this.topik_pekerjaan = topik_pekerjaan;
        this.id_dudi = id_dudi;
        this.status = status;
        this.catatan = catatan;
    }

    public DataJurnalPKL() {
    }

    public String getId_jurnal_pkl() {
        return id_jurnal_pkl;
    }

    public void setId_jurnal_pkl(String id_jurnal_pkl) {
        this.id_jurnal_pkl = id_jurnal_pkl;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_guru) {
        this.id_siswa = id_guru;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKompetensi_dasar(){
        return kompetensi_dasar;
    }

    public void setKompetensi_dasar(String kompetensi_dasar){
        this.kompetensi_dasar = kompetensi_dasar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTopik_pekerjaan() {
        return topik_pekerjaan;
    }

    public void setTopik_pekerjaan(String topik_pekerjaan) {
        this.topik_pekerjaan = topik_pekerjaan;
    }

    public String getId_dudi(){
        return id_dudi;
    }

    public void setId_dudi(String id_dudi){
        this.id_dudi = id_dudi;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getCatatan(){
        return catatan;
    }

    public void setCatatan(String catatan){
        this.catatan = catatan;
    }

}
