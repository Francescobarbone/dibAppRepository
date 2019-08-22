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
    private String endLezione;
    private String startLezione;
    private QRCodeGenerator codice;

    public Lezione(String corsoDiLaurea, String argomento, String dataLezione, String oraInzio, String oraFine){
         this.cdL = corsoDiLaurea;
         this.descrizione= argomento;
         this.data = dataLezione;
         this.startLezione = oraInzio;
         this.endLezione = oraFine;
         ID = id++;
         codice = new QRCodeGenerator(ID);
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

    public String getStartLezione() {
        return startLezione;
    }

    public QRCodeGenerator getCodice() {
        return codice;
    }

    public String getEndLezione() {
        return endLezione;
    }

    public Integer getID() {
        return ID;
    }

}
