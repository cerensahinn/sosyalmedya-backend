package com.cerensahin.sosyalmedya.ortak.exception.medya;

public class MedyaCokBuyukException extends RuntimeException {
    private final long size;
    public MedyaCokBuyukException(long size) {
        super("Medya boyutu çok büyük");
        this.size = size;
    }
    public long getSize() { return size; }
}
