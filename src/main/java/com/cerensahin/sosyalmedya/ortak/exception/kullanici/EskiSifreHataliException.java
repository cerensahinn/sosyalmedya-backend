package com.cerensahin.sosyalmedya.ortak.exception.kullanici;

public class EskiSifreHataliException extends RuntimeException {
    //Ben RuntimeException sınıfını genişleterek (extend ederek) kendi özel hata sınıfımı tanımlıyorum.
    public EskiSifreHataliException() { super("Mevcut şifre doğru değil"); }
    //throw new EskiSifreHataliException();
    //Bu satır fırlatıldığında, Spring bunu yakalayıp "Mevcut şifre doğru değil" mesajını dönecek.

    //Programın akışını kesip kullanıcıya anlamlı bir mesaj döndürmek içindir.
    //
    //Bu durumda RuntimeException kullanmak doğru seçimdir.
}
