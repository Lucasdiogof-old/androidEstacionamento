package com.example.alexandre.trabalho04;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UltimaTelaInclude extends AppCompatActivity {

    protected TextView txtDescricao;
    protected TextView txtPlaca;
    protected TextView txtPontoDeRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ultima_tela_include);
        txtDescricao = findViewById(R.id.txtDescricao);
        txtPlaca = findViewById(R.id.txtPlaca);
        txtPontoDeRef = findViewById(R.id.txtPontoDeRef);

        this.setarDados();

    }

    public void setarDados() {
        ClienteDataBase clienteDataBase = new ClienteDataBase(this);

        Cliente cliente = new Cliente();
        cliente = clienteDataBase.listarUltimoRegistro();


        if (cliente != null) {

            txtDescricao.setText("Descrição: " + cliente.getDesc());
            txtPlaca.setText("Placa: " + cliente.getPlaca());
            txtPontoDeRef.setText("Ponto de Referência: " + cliente.getPontoDeRef());

        }
    }
}