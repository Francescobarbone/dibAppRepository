package com.dibapp.dibapp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public final class Lezione {
    private final int ID;
    private static int id = 0;
    private List<String> studenti = new ArrayList<>();
    private boolean valutazione = false;
    private String cdL;
    private String data;
    private String descrizione;

    public Lezione(){
        ID = id;
    }

    public Lezione(String corsoDiLaurea, String argomento, String dataLezione){
         this.cdL = corsoDiLaurea;
         this.descrizione= argomento;
         this.data = dataLezione;
         ID = id++;
    }

    public boolean isValutazione() {
        return valutazione;
    }


    public void setValutazione(boolean valutazione) {
        this.valutazione = valutazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getData() {
        return data;
    }

    public String getCdL() {
        return cdL;
    }

    public List<String> getStudenti() {
        return studenti;
    }
}
