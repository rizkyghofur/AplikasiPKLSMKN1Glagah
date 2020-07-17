package com.rizkyghofur.aplikasipklsmkn1glagah.siswa;

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

public class MenuSiswa extends AppCompatActivity {

    public static final String TAG_USER = "nama";
    ImageView btn_logout;
    TextView txt_username;
    String user;
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_siswa);

        txt_username = findViewById(R.id.user_id);
        sharedpreferences = getSharedPreferences(Login.my_shared_preferences, Context.MODE_PRIVATE);
        btn_logout = findViewById(R.id.logout);
        user = getIntent().getStringExtra(TAG_USER);
        txt_username.setText(user);

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
              editor.putBoolean(MenuSiswa.session_status, false);
              editor.putString(TAG_USER, null);
              editor.commit();
              Intent ua = new Intent(MenuSiswa.this, Login.class);
              finish();
              startActivity(ua);
    }

        public void InfoDUDI (View view){
            Intent intent = new Intent(MenuSiswa.this, InfoDUDI.class);
            startActivity(intent);
        }

        public void PermohonanPKL (View view){
        Intent intent = new Intent(MenuSiswa.this, PermohonanPKL.class);
        startActivity(intent);
        }

        public void ProgramPKL (View view){
        Intent intent = new Intent(MenuSiswa.this, ProgramPKL.class);
        startActivity(intent);
        }

        public void JurnalPKL (View view){
            Intent intent = new Intent(MenuSiswa.this, JurnalPKL.class);
            startActivity(intent);
        }

        public void AbsensiPKL (View view){
            Intent intent = new Intent(MenuSiswa.this, AbsensiPKL.class);
            startActivity(intent);
        }

        public void CatatanKunjunganPKL (View view){
            Intent intent = new Intent(MenuSiswa.this, CatatanKunjunganPKLSiswa.class);
            startActivity(intent);
        }

    long lastPress;
    Toast backpressToast;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastPress > 5000){
            backpressToast = Toast.makeText(getBaseContext(), "Tekan Kembali untuk keluar", Toast.LENGTH_LONG);
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

    }
