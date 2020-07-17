package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataProgramPKL;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public class AdapterProgramPKL extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataProgramPKL> items;

    public AdapterProgramPKL(Activity activity, List<DataProgramPKL> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_guru_program_pkl_list, null);

        TextView id_program_pkl = convertView.findViewById(R.id.id_program_pkl);
        TextView nama_siswa = convertView.findViewById(R.id.nama_siswa);
        TextView kelas = convertView.findViewById(R.id.kelas);
        TextView kompetensi_dasar = convertView.findViewById(R.id.kompetensi_dasar);
        TextView nama_dudi = convertView.findViewById(R.id.nama_dudi);
        TextView topik_pekerjaan = convertView.findViewById(R.id.topik_pekerjaan);
        TextView urutan_waktu_pelaksanaan = convertView.findViewById(R.id.tanggal);

        DataProgramPKL data = items.get(position);

        id_program_pkl.setText(data.getId_programpkl());
        nama_siswa.setText("Nama Siswa : " + data.getId_siswa());
        kelas.setText("Kelas : " + data.getKelas());
        kompetensi_dasar.setText("Kompetensi Dasar : " + data.getKompetensi_dasar());
        nama_dudi.setText("Instansi/DUDI Pasangan : "+ data.getDudi_pasangan());
        topik_pekerjaan.setText("Topik Pekerjaan : "+ data.getTopik_pekerjaan());
        urutan_waktu_pelaksanaan.setText("Tanggal Pelaksanaan : " + data.getUrutan_waktu_pelaksanaan());
        return convertView;
    }
}
