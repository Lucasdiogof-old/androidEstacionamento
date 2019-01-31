package com.example.alexandre.trabalho04;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class CadastroTela extends AppCompatActivity {

    EditText editCod, editDesc, editPlaca, editPontoDeRef, editLatitude, editLongitude;
    Button btnLimpar, btnSalvar, btnExcluir, btnConsultar;
    ListView lista;
    
    ClienteDataBase bd = new ClienteDataBase(this);
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_tela);

        editCod = findViewById(R.id.PlainCod);
        editDesc = findViewById(R.id.PlainDesc);
        editPlaca = findViewById(R.id.PlainPlaca);
        editPontoDeRef = findViewById(R.id.PlainPontoDeRef);
        editLatitude = findViewById(R.id.PlainLatitude);
        editLongitude = findViewById(R.id.PlainLongitude);

        client = LocationServices.getFusedLocationProviderClient(this);

        btnConsultar = findViewById(R.id.btn4);
        btnLimpar = findViewById(R.id.btn1);
        btnSalvar = findViewById(R.id.btn2);
        btnExcluir = findViewById(R.id.btn3);
        lista = findViewById(R.id.lista);
        listarClientes();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conteudo = (String) lista.getItemAtPosition(position);
                String codigo = conteudo.substring(0, conteudo.indexOf(" - "));
                Cliente cliente = bd.selecionarCliente(Integer.parseInt(codigo));
                editCod.setText(String.valueOf(cliente.getCod()));
                editDesc.setText(cliente.getDesc());
                editPlaca.setText(cliente.getPlaca());
                editPontoDeRef.setText(cliente.getPontoDeRef());
            }
        });

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampo();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editCod.getText().toString();
                String descc = editDesc.getText().toString();
                String placa = editPlaca.getText().toString();
                String pontoDeRef = editPontoDeRef.getText().toString();
                String latitude = editLatitude.getText().toString();
                String longitude = editLongitude.getText().toString();
                String dataChegada = new Date().toString();
                String dataSaida = "Saiu";

                if (ActivityCompat.checkSelfPermission(CadastroTela.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                client.getLastLocation().addOnSuccessListener(CadastroTela.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double lat = location.getLatitude();
                            double lon = location.getLongitude();

                            editLatitude.setText("" + lat);
                            editLongitude.setText("" + lon);
                        }
                    }
                });
                if (descc.isEmpty()) editDesc.setError("Campo Obrigatório!");
                else if (codigo.isEmpty()) {
                    bd.AddCliente(new Cliente(descc, placa, pontoDeRef, latitude, longitude, dataChegada, dataSaida));
                    Toast.makeText(CadastroTela.this, "Adicionado com Sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampo();
                    listarClientes();
                } else {
                    bd.atualizarCliente(new Cliente(Integer.parseInt(codigo), descc, placa, pontoDeRef, latitude, longitude));
                    Toast.makeText(CadastroTela.this, "Atualizado com Sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampo();
                    listarClientes();
                }

            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editCod.getText().toString();
                if (codigo.isEmpty()) {
                    Toast.makeText(CadastroTela.this, "Não há nada selecionado!", Toast.LENGTH_SHORT).show();
                } else {
                    Cliente cliente = new Cliente();
                    cliente.setCod(Integer.parseInt(codigo));
                    bd.ApagarCliente(cliente);
                    Toast.makeText(CadastroTela.this, "Excluído com Sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampo();
                    listarClientes();
                }
            }
        });


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CadastroTela.this, PenultimaTela.class);
                startActivity(i);

            }
        });


    }

    public void listarClientes() {
        List<Cliente> clientes = bd.listarTodos();
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(CadastroTela.this, android.R.layout.simple_list_item_1, arrayList);
        lista.setAdapter(adapter);
        for (Cliente c : clientes) {
            arrayList.add(c.getCod() + " - " + c.getDesc() + " - " + c.getPlaca() + " - " + c.getPontoDeRef() + " - " + c.getLatitude() + " - " + c.getLongitude() + " - "
                    + c.getDataChegada() + " - " + c.getDataSaida());
            adapter.notifyDataSetChanged();
        }
        bd.close();
    }

    public void limparCampo() {
        editCod.setText("");
        editDesc.setText("");
        editPlaca.setText("");
        editPontoDeRef.setText("");
        editLatitude.setText("");
        editLongitude.setText("");
        editDesc.requestFocus();

    }
}
