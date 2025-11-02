package com.cerensahin.sosyalmedya.service;
//Bu sınıf, senin projenin token üretiminden sorumlu güvenli yardımcı sınıfıdır.
import java.security.SecureRandom; //Güvenli (kriptografik seviyede) rastgele sayı üretmek için kullanılır.
//🔹 Normal Random sınıfına göre çok daha güçlüdür çünkü tahmin edilmesi çok zordur.
//🔹 Token gibi güvenlik amaçlı değerler üretmek için zorunludur.
import java.util.Base64;
//verileri okunabilir string haline dönüştürmek için kullanılır.
//🔹 Çünkü rastgele byte’ları doğrudan göstermek mümkün değildir.
//🔹 Örnek: byte[] → "A9D3kLmF..." gibi okunabilir bir formata çevrilir.

public class TokenUretici {
    private static final SecureRandom RASTGELE = new SecureRandom();

    public static String uret(int baytUzunlugu)
            //int baytUzunlugu → token’ın ne kadar uzun olacağını belirler.
    //(örneğin 32 → 32 bayt = ~43 karakterlik string)
            //Bu metot dışarıdan çağrılarak istenen uzunlukta token oluşturur.
    {
        byte[] buf = new byte[baytUzunlugu];
        //Token için rastgele değerlerin saklanacağı boş bir dizi oluşturur.
        //Bilgisayar biliminde “buffer”, geçici olarak veriyi tuttuğun bir yerdir.
        //Özellikle byte dizileriyle çalışırken, bu dizilere genellikle “buf” adı verilir.
        RASTGELE.nextBytes(buf);
        //Az önce oluşturduğumuz buf dizisini rastgele sayılarla doldurur.
        //SecureRandom her byte’a 0–255 arası rastgele değer atar.
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
        //Base64.getUrlEncoder() → Baytları URL-dostu bir karakter dizisine çevirir
        //(örneğin /, +,  gibi URL’de sorun çıkaran karakterleri kullanmaz).
        //.withoutPadding() → Base64 çıktısındaki gereksiz = karakterlerini kaldırır
        //(örneğin "A7b==” yerine "A7b" döner).
        //.encodeToString(buf) → Bayt dizisini okunabilir bir String haline getirir.
    }
}
