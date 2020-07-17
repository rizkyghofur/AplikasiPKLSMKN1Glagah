package com.rizkyghofur.aplikasipklsmkn1glagah.ketuakompetensi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.rizkyghofur.aplikasipklsmkn1glagah.Login;
import com.rizkyghofur.aplikasipklsmkn1glagah.R;

public class MenuKaKomp extends AppCompatActivity {

    public static final String TAG_USER = "nama";
    public static final String session_status = "session_status";
    TextView txt_username;
    String user;
    SharedPreferences sharedpreferences;
    ImageView btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ka_komp);

        txt_username = findViewById(R.id.user_id);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        user = getIntent().getStringExtra(TAG_USER);
        txt_username.setText(user);
        btn_logout = findViewById(R.id.logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
        alertDialogBuilder.setTitle("Logout dari aplikasi");
        alertDialogBuilder
                .setMessage("Yakin ingin keluar dari aplikasi?")
                .setIcon(R.mipmap.login)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        logout();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(MenuKaKomp.session_status, false);
        editor.putString(TAG_USER, null);
        editor.commit();
        Intent ua = new Intent(MenuKaKomp.this, Login.class);
        finish();
        startActivity(ua);
    }

    public void PengajuanPKLSiswa (View view){
        Intent intent = new Intent(MenuKaKomp.this, PengajuanPKLSiswa.class);
        startActivity(intent);
    }

    long lastPress;
    Toast backpressToast;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Tekan tombol kembali 2 kali untuk keluar", Toast.LENGTH_LONG);
            backpressToast.show();
            lastPress = currentTime;

        } else {
            if (backpressToast != null) backpressToast.cancel();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish();
            startActivity(intent);
            super.onBackPressed();
        }
    }

    public void KompetensiDasar (View view){
        Intent intent = new Intent(MenuKaKomp.this, KompetensiDasar.class);
        startActivity(intent);
    }

    public void AbsensiPKL_Siswa (View view){
        Intent intent = new Intent(MenuKaKomp.this, AbsensiPKLSiswa.class);
        startActivity(intent);
    }

    public void JurnalPKL_Siswa (View view){
        Intent intent = new Intent(MenuKaKomp.this, JurnalPKLSiswa.class);
        startActivity(intent);
    }

    public void CatatanKunjunganPKLKakomp (View view){
        Intent intent = new Intent(MenuKaKomp.this, CatatanKunjunganPKLKakomp.class);
        startActivity(intent);
    }

}
