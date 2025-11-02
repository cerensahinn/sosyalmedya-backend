package com.cerensahin.sosyalmedya.ortak;

import java.security.MessageDigest; //Bu sınıf Java’nın güvenlik (security) kütüphanesinden gelir
//ve hashleme algoritmalarını (MD5, SHA-256, SHA-512, vb.) uygulamak için kullanılır.
import java.nio.charset.StandardCharsets; //Bu sınıf karakter kodlamalarını (encoding) temsil eder.
//Yani metni (örneğin "123456") bayt dizisine çevirirken hangi karakter setini kullanacağını söyler.

public class SifreGizleme {
    public static String sha256(String metin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //getInstance("SHA-256") → “SHA-256” adındaki algoritmayı kullan
            //SHA-256, veriyi geri döndürülemez şekilde 256-bit’lik (64 karakterlik) bir hash’e çevirir.

            byte[] d = md.digest(metin.getBytes(StandardCharsets.UTF_8));
           // metin.getBytes(StandardCharsets.UTF_8)
//→ Girilen metni ("123456") UTF-8 karakter setiyle byte (ikili sayı dizisine) çevirir.
                   // Çünkü şifreleme algoritmaları metinle değil bayt verisiyle çalışır.
                  //  md.digest(...)
//→ SHA-256 algoritmasını çalıştırır ve sonucu byte dizisi olarak döner.
            //Yani d artık şifrelenmiş veriyi (hash sonucu) tutar ama bu hâl hâlâ sayısal (byte) formdadır, insanlar okuyamaz.
            // Örnek:
            //[45, -67, 12, 88, ...] gibi bir dizidir.

            String sonuc = "";
            //Bu satır boş bir metin oluşturur.
            //Birazdan bu sonuc içine şifrelenmiş veriyi (byte dizisini) insan tarafından okunabilir hale getirerek yazacağız.
            for (byte b : d) {
                //Bu döngü, d dizisindeki her byte değerini tek tek alır ve hexadecimal (16’lık sistem)
                // formuna çevirir.
                sonuc = sonuc + String.format("%02x", b);
                //String.format("%02x", b)
                //→ Her byte’ı iki haneli hexadecimal string’e dönüştür.
                //(%02x = “2 haneli küçük harfli hex sayı” demek.)
                //sonuc = sonuc + ...
                //→ Bu küçük parçaları sırayla birleştirerek büyük bir string oluştur.
                //Sonunda sonuc şu hale gelir:
                //"8d969eef6ecad3c29a3a629280e686cf"
            }
            return sonuc;
        } catch (Exception e) {
            //Bu blok, try kısmında bir hata olursa devreye girer.
            //Örneğin:
            //Bilgisayarın Java sürümü SHA-256’ı desteklemiyorsa,
            //veya MessageDigest düzgün çalışmadıysa,
            //o zaman program çökmesin diye burada özel bir hata fırlatılır.
            //throw new RuntimeException("SHA-256 desteklenmiyor");
            //→ Yeni bir hata oluştur ve “SHA-256 desteklenmiyor” mesajını göster.

            throw new RuntimeException("SHA-256 desteklenmiyor");
        }
        //Neden SHA-256 sonucu hexadecimal (16’lık sistem) olarak gösteriliyor?
//SHA-256 algoritması, metni sayılardan oluşan ikili (binary) bir veri haline getirir.
//Yani sonuç bit’lerden (0 ve 1’lerden) oluşur. 01011010 10101101 11000001 00101100 ...
//Bu haliyle insanlar için okunması zordur.
//Bu yüzden bu ikili veriyi daha okunabilir bir forma çevirmek gerekir.
//Hexadecimal (16’lık sistem) bu amaç için yaygın olarak kullanılır.


    }
}
