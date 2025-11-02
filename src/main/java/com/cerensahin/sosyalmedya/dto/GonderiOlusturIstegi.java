package com.cerensahin.sosyalmedya.dto;

public class GonderiOlusturIstegi { //Kullanıcı gönderi paylaşmak istediğinde, bu sınıf Postman’daki
    // JSON verisini tutar
    private String icerik;
    private String medyaUrl;
    private String medyaBase64;
    private String medyaTipi;

    public GonderiOlusturIstegi() { }

    /*
 Spring Boot gelen JSON verisini nesneye dönüştürürken (ör. @RequestBody kullanıldığında)
şunu yapar:

Boş bir nesne oluşturur → new GonderiOlusturIstegi()

JSON’daki alanları setter metotlarıyla tek tek doldurur.

📘 Yani bu boş kurucu,

Spring’in “JSON → Java nesnesi” dönüşümü (deserialization) yapabilmesi için zorunludur.
     */

    public String getIcerik() { return icerik; }
    public void setIcerik(String icerik) { this.icerik = icerik; }

    public String getMedyaUrl() { return medyaUrl; }
    public void setMedyaUrl(String medyaUrl) { this.medyaUrl = medyaUrl; }

    public String getMedyaBase64() { return medyaBase64; }
    public void setMedyaBase64(String medyaBase64) { this.medyaBase64 = medyaBase64; }

    public String getMedyaTipi() { return medyaTipi; }
    public void setMedyaTipi(String medyaTipi) { this.medyaTipi = medyaTipi; }
}
