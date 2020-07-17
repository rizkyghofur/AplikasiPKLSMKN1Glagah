package com.rizkyghofur.aplikasipklsmkn1glagah.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPermohonanPKL {

    @SerializedName("id_pengajuanpkl")
    @Expose
    private String id_pengajuanpkl;
    @SerializedName("id_siswa")
    @Expose
    private String id_siswa;
    @SerializedName("nama")
    @Expose
    private String nama_siswa;
    @SerializedName("kelas")
    @Expose
    private String kelas;
    @SerializedName("nama_dudi")
    @Expose
    private String id_dudi;
    @SerializedName("tanggal_masuk")
    @Expose
    private String tanggal_masuk;
    @SerializedName("tanggal_keluar")
    @Expose
    private String tanggal_keluar;
    @SerializedName("nama_guru")
    @Expose
    private String id_guru;
    @SerializedName("status_validasi")
    @Expose
    private String status_validasi;

    public DataPermohonanPKL() {
    }

    public DataPermohonanPKL(String id_pengajuanpkl, String id_siswa, String id_dudi, String nama_siswa, String kelas, String id_guru, String status_validasi, String tanggal_masuk, String tanggal_keluar) {
        this.id_pengajuanpkl = id_pengajuanpkl;
        this.id_siswa = id_siswa;
        this.id_dudi = id_dudi;
        this.nama_siswa = nama_siswa;
        this.kelas = kelas;
        this.tanggal_masuk = tanggal_masuk;
        this.tanggal_keluar = tanggal_keluar;
        this.id_guru = id_guru;
        this.status_validasi = status_validasi;
    }

    public String getId() {
        return id_pengajuanpkl;
    }

    public void setId(String id_pengajuanpkl) {
        this.id_pengajuanpkl = id_pengajuanpkl;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getId_dudi() {
        return id_dudi;
    }

    public void setId_dudi(String id_dudi) {
        this.id_dudi = id_dudi;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getTanggal_keluar() {
        return tanggal_keluar;
    }

    public void setTanggal_keluar(String tanggal_keluar) {
        this.tanggal_keluar = tanggal_keluar;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getStatus_validasi() {
        return status_validasi;
    }

    public void setStatus_validasi(String status_validasi) {
        this.status_validasi = status_validasi;
    }

    public String getNama_siswa(){
        return nama_siswa;
    }

    public void setNama_siswa(String nama_siswa) {
        this.nama_siswa = nama_siswa;
    }

    public String getKelas(){
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
