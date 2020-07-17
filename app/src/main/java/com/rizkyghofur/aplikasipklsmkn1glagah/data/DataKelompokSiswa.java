package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataKelompokSiswa {
    private String id, nama;

    public DataKelompokSiswa() {
    }

    public DataKelompokSiswa(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }


    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId_siswa() {
        return id;
    }

    public void setId_siswa(String id) {
        this.id = id;
    }
}
