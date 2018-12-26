package com.example.alexandre.trabalho04;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ParaInclude extends AppCompatActivity {

    Button btnMarcarLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.para_include);
        btnMarcarLoc = findViewById(R.id.btnMarcarLoc);
        requestPermission();
        btnMarcarLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ParaInclude.this, CadastroTela.class);
                startActivity(i);
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}