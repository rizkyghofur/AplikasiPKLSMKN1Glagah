package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataDUDI {
    private String id, no_telp_dudi, nama_dudi, alamat_dudi, jenis_usaha, nama_pimpinan, no_telp_pimpinan, kuota;
    public DataDUDI() {
    }

    public DataDUDI(String id, String nama_dudi, String alamat_dudi, String no_telp_dudi, String jenis_usaha, String nama_pimpinan, String no_telp_pimpinan, String kuota) {
        this.id = id;
        this.nama_dudi = nama_dudi;
        this.alamat_dudi = alamat_dudi;
        this.no_telp_dudi = no_telp_dudi;
        this.jenis_usaha = jenis_usaha;
        this.nama_pimpinan = nama_pimpinan;
        this.no_telp_pimpinan = no_telp_pimpinan;
        this.kuota =  kuota;
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

    public String getAlamat_dudi() {
        return alamat_dudi;
    }

    public void setAlamat_dudi(String alamat_dudi) {
        this.alamat_dudi = alamat_dudi;
    }

    public String getNo_telp_dudi() {
        return no_telp_dudi;
    }

    public void setNo_telp_dudi(String no_telp_dudi) {
        this.no_telp_dudi = no_telp_dudi;
    }

    public String getJenis_usaha() {
        return jenis_usaha;
    }

    public void setJenis_usaha(String jenis_usaha) {
        this.jenis_usaha = jenis_usaha;
    }

    public String getNama_pimpinan() {
        return nama_pimpinan;
    }

    public void setNama_pimpinan(String nama_pimpinan) {
        this.nama_pimpinan = nama_pimpinan;
    }

    public String getNo_telp_pimpinan() {
        return no_telp_pimpinan;
    }

    public void setNo_telp_pimpinan(String no_telp_pimpinan) {
        this.no_telp_pimpinan = no_telp_pimpinan;
    }

    public String getKuota() {
        return kuota;
    }

    public void setKuota(String kuota) {
        this.kuota = kuota;
    }

}
