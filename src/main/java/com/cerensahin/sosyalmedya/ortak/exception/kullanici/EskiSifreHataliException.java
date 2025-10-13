package com.cerensahin.sosyalmedya.ortak.exception.kullanici;

public class EskiSifreHataliException extends RuntimeException {
    public EskiSifreHataliException() { super("Mevcut şifre doğru değil"); }
}
