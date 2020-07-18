package com.rizkyghofur.aplikasipklsmkn1glagah.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataProgramPKL {

    @SerializedName("id_program_pkl")
    @Expose
    private String id_program_pkl;
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
    private String dudi_pasangan;
    @SerializedName("tanggal")
    @Expose
    private String urutan_waktu_pelaksanaan;

    public DataProgramPKL(String id_program_pkl, String id_siswa, String kelas, String kompetensi_dasar, String dudi_pasangan, String topik_pekerjaan, String urutan_waktu_pelaksanaan) {
        this.id_program_pkl = id_program_pkl;
        this.id_siswa = id_siswa;
        this.kelas = kelas;
        this.kompetensi_dasar = kompetensi_dasar;
        this.dudi_pasangan = dudi_pasangan;
        this.topik_pekerjaan = topik_pekerjaan;
        this.urutan_waktu_pelaksanaan = urutan_waktu_pelaksanaan;
    }

    public DataProgramPKL() {
    }

    public String getId_programpkl() {
        return id_program_pkl;
    }

    public void setId_programpkl(String id_program_pkl) {
        this.id_program_pkl = id_program_pkl;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getKompetensi_dasar() {
        return kompetensi_dasar;
    }

    public void setKompetensi_dasar(String kompetensi_dasar) {
        this.kompetensi_dasar = kompetensi_dasar;
    }

    public String getTopik_pekerjaan() {
        return topik_pekerjaan;
    }

    public void setTopik_pekerjaan(String topik_pekerjaan) {
        this.topik_pekerjaan = topik_pekerjaan;
    }

    public String getDudi_pasangan() {
        return dudi_pasangan;
    }

    public void setDudi_pasangan(String dudi_pasangan) {
        this.dudi_pasangan = dudi_pasangan;
    }

    public String getUrutan_waktu_pelaksanaan() {
        return urutan_waktu_pelaksanaan;
    }

    public void setUrutan_waktu_pelaksanaan(String urutan_waktu_pelaksanaan) {
        this.urutan_waktu_pelaksanaan = urutan_waktu_pelaksanaan;
    }

}


