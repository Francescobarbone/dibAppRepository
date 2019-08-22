package com.dibapp.dibapp;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class Lezione {
    private final int ID;
    private static int id = 0;
    private List<String> studenti = new ArrayList<>();
    private String valutazione;
    private String cdL;
    private String data;
    private String descrizione;
    private String endLezione;
    private String startLezione;
    private QRCodeGenerator codice;
    private long startTimestamp;
    private long endTimestamp;
    private long createdAt;


    public Lezione(String corsoDiLaurea, String argomento, String dataLezione, String oraInzio, String oraFine){
        this.cdL = corsoDiLaurea;
        this.descrizione= argomento;
        this.data = dataLezione;
        this.startLezione = oraInzio;
        this.endLezione = oraFine;
        ID = id++;
        codice = new QRCodeGenerator(ID);
        this.startTimestamp = toTimeStamp(data, startLezione);
        this.endTimestamp = toTimeStamp(data, endLezione);
        this.createdAt = new Date().getTime();
    }

    public String getValutazione() {
        return valutazione;
    }


    public void setValutazione(String valutazione) {
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

    private long toTimeStamp(String date, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        try {
            Date parsedDate = dateFormat.parse(String.format("%s %s", date, time));
            long timestamp = parsedDate.getTime();
            return timestamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
