package com.rizkyghofur.aplikasipklsmkn1glagah.data;

public class DataKompetensiDasar {
    private String id, id_mapel, kode, kompetensi_dasar;

    public DataKompetensiDasar() {
    }

    public DataKompetensiDasar(String id, String kompetensi_dasar, String id_mapel, String kode) {
        this.id = id;
        this.id_mapel = id_mapel;
        this.kompetensi_dasar = kompetensi_dasar;
        this.kode = kode;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getKompetensi_dasar() {
        return kompetensi_dasar;
    }

    public void setKompetensi_dasar(String kompetensi_dasar) {
        this.kompetensi_dasar = kompetensi_dasar;
    }

    public String getKode(){
        return kode;
    }

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public void setId_mapel(String id_mapel) {
        this.id_mapel = id_mapel;
    }
}
