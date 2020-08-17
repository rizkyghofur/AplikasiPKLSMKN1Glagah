package com.rizkyghofur.aplikasipklsmkn1glagah.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;
import com.rizkyghofur.aplikasipklsmkn1glagah.data.DataListDUDI;
import java.util.List;

public class AdapterListDUDI extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataListDUDI> item;

    public AdapterListDUDI(Activity activity, List<DataListDUDI> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
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
            convertView = inflater.inflate(R.layout.activity_siswa_permohonan_pkl_dudi, null);

        TextView dudi = convertView.findViewById(R.id.nama_dudi);

        DataListDUDI data = new DataListDUDI();
        if(data != null && item.size() !=0){
            data = item.get(position);
        }

        dudi.setText(data.getNama_dudi());
        return convertView;
    }
}
