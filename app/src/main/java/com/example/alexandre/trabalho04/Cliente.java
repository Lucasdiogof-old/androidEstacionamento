package com.example.alexandre.trabalho04;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cliente {
    private int cod;
    private String desc, placa, pontoDeRef,latitude, longitude,dataChegada, dataSaida;

    public Cliente(){

    }

    public Cliente(int cod, String desc, String placa, String pontoDeRef, String latitude, String longitude){
        this.cod = cod;
        this.desc = desc;
        this.placa = placa;
        this.pontoDeRef = pontoDeRef;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Cliente(String desc, String placa, String pontoDeRef, String latitude, String longitude, String dataChegada, String dataSaida){
        this.desc = desc;
        this.placa = placa;
        this.pontoDeRef = pontoDeRef;
        this.latitude = latitude;
        this.longitude = longitude;

        Date data = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataChegadaFormatada = formato.format(data);
        this.dataChegada = dataChegadaFormatada;
        this.dataSaida = "Não saiu ainda.";
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int codigo) {
        this.cod = codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String descricao) {
        this.desc = descricao;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPontoDeRef() {
        return pontoDeRef;
    }

    public void setPontoDeRef(String pontoDeReferencia) {
        this.pontoDeRef = pontoDeReferencia;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDataChegada() { return dataChegada; }

    public void setDataChegada(String dataChegada) { this.dataChegada = dataChegada; }

    public String getDataSaida() { return dataSaida; }

    public void setDataSaida(String dataSaida) { this.dataSaida = dataSaida; }
}
