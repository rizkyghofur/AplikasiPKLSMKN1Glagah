package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataAbsensiPKL {
    private String id_absensi, id_dudi, nama, kelas, tanggal_absensi, keterangan, nama_dudi;

    public DataAbsensiPKL() {
    }

    public DataAbsensiPKL(String id_absensi, String id_dudi, String nama, String kelas, String tanggal_absensi, String keterangan, String nama_dudi) {
        this.id_absensi = id_absensi;
        this.id_dudi = id_dudi;
        this.nama = nama;
        this.kelas = kelas;
        this.tanggal_absensi = tanggal_absensi;
        this.keterangan = keterangan;
        this.nama_dudi = nama_dudi;
    }

    public String getId_absensi() {
        return id_absensi;
    }

    public void setId_absensi(String id_absensi) {
        this.id_absensi = id_absensi;
    }

    public String getId_dudi() {
        return id_dudi;
    }

    public void setId_dudi(String id_dudi) {
        this.id_dudi = id_dudi;
    }

    public String getId_siswa() {
        return nama;
    }

    public void setId_siswa(String nama) {
        this.nama = nama;
    }

    public String getTanggal_absensi() {
        return tanggal_absensi;
    }

    public void setTanggal_absensi(String tanggal_absensi) {
        this.tanggal_absensi = tanggal_absensi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama_dudi(){
        return nama_dudi;
    }

    public void setNama_dudi(String nama_dudi){
        this.nama_dudi = nama_dudi;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKelas() {
        return kelas;
    }
}
