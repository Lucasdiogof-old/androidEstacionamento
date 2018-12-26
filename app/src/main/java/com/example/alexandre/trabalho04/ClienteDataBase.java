package com.example.alexandre.trabalho04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClienteDataBase extends SQLiteOpenHelper {

    private static final int versao = 1;
    private static final String nomeBD = "bd_clientes";

    private static final String tb_clientes = "clientes";
    private static final String c_cod = "cod";
    private static final String c_desc = "descc";
    private static final String c_placa = "placa";
    private static final String c_pontoDeRef = "pontoDeRef";
    private static final String c_latitude = "latitude";
    private static final String c_longitude = "longitude";
    private static final String c_dataChegada = "dataChegada";
    private static final String c_dataSaida = "dataSaida";

    public ClienteDataBase(Context context) {
        super(context, nomeBD, null, versao);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + tb_clientes + "(" +
                c_cod + " INTEGER PRIMARY KEY, " + c_desc + " TEXT, " +
                c_placa + " TEXT, " + c_pontoDeRef + " TEXT, " + c_latitude + " TEXT, " + c_longitude + " TEXT, " + c_dataChegada + " TEXT, " + c_dataSaida + " TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(c_desc, cliente.getDesc());
        values.put(c_placa, cliente.getPlaca());
        values.put(c_pontoDeRef, cliente.getPontoDeRef());
        values.put(c_latitude, cliente.getLatitude());
        values.put(c_longitude, cliente.getLongitude());
        values.put(c_dataChegada, cliente.getDataChegada());
        values.put(c_dataSaida, cliente.getDataSaida());
        db.insert(tb_clientes, null, values);
        db.close();
    }

    public void ApagarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_clientes, c_cod + " = ?", new String[]{String.valueOf(cliente.getCod())});
        db.close();
    }

    public Cliente selecionarCliente(int cod) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tb_clientes, new String[]{c_cod, c_desc, c_placa, c_pontoDeRef, c_latitude, c_longitude}, c_cod + " = ?", new String[]{String.valueOf(cod)}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        else return null;
        Cliente cliente1 = new Cliente
                (Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));

        return cliente1;

    }

    public List<Cliente> listarTodos() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Cliente> lista = new ArrayList<Cliente>();
        String query = "SELECT * FROM " + tb_clientes;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setCod(Integer.parseInt(cursor.getString(0)));
                cliente.setDesc(cursor.getString(1));
                cliente.setPlaca(cursor.getString(2));
                cliente.setPontoDeRef(cursor.getString(3));
                cliente.setLatitude(cursor.getString(4));
                cliente.setLongitude(cursor.getString(5));
                cliente.setDataChegada(cursor.getString(6));
                cliente.setDataSaida(cursor.getString(7));
                lista.add(cliente);
            } while (cursor.moveToNext());
        }

        return lista;
    }

    public Cliente listarUltimoRegistro() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tb_clientes + " WHERE cod = (SELECT MAX(cod) FROM " + tb_clientes + ")";
        Cursor cursor = db.rawQuery(query, null);
        Cliente cliente = new Cliente();
        if (cursor.moveToFirst()) {
            cliente = new Cliente();
            cliente.setCod(Integer.parseInt(cursor.getString(0)));
            cliente.setDesc(cursor.getString(1));
            cliente.setPlaca(cursor.getString(2));
            cliente.setPontoDeRef(cursor.getString(3));
            cliente.setLatitude(cursor.getString(4));
            cliente.setLongitude(cursor.getString(5));
            cliente.setDataChegada(cursor.getString(6));
            cliente.setDataSaida(cursor.getString(7));

            db.close();
            return cliente;
        }
        return null;
    }

    public void atualizarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(c_desc, cliente.getDesc());
        values.put(c_placa, cliente.getPlaca());
        values.put(c_pontoDeRef, cliente.getPontoDeRef());
        values.put(c_latitude, cliente.getLatitude());
        values.put(c_longitude, cliente.getLongitude());
        db.update(tb_clientes, values, c_cod + " = ?", new String[]{String.valueOf(cliente.getCod())});
        db.close();
    }


}