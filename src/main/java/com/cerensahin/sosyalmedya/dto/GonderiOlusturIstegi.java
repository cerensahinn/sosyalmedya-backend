package com.cerensahin.sosyalmedya.dto;

public class GonderiOlusturIstegi { 

    private String icerik;
    private String medyaUrl;
    private String medyaBase64;
    private String medyaTipi;

    public GonderiOlusturIstegi() { }

    public String getIcerik() { return icerik; }
    public void setIcerik(String icerik) { this.icerik = icerik; }

    public String getMedyaUrl() { return medyaUrl; }
    public void setMedyaUrl(String medyaUrl) { this.medyaUrl = medyaUrl; }

    public String getMedyaBase64() { return medyaBase64; }
    public void setMedyaBase64(String medyaBase64) { this.medyaBase64 = medyaBase64; }

    public String getMedyaTipi() { return medyaTipi; }
    public void setMedyaTipi(String medyaTipi) { this.medyaTipi = medyaTipi; }
}
