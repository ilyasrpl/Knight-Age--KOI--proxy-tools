package com.KoiProxy;

public class Encryption {
    public byte[] key;
    public int checkPoint;
    public boolean isEnabled;
    public Encryption(){
        this.isEnabled = false;
        this.checkPoint = 0;
        this.key = new byte[]{0};
    }

    public void setKey(byte[] keys) {
        this.key = keys;
        this.isEnabled = true;
    }

    public byte swap(byte msg) {
        byte result = (byte) (msg ^ this.key[this.checkPoint]);
        this.checkPoint = (this.checkPoint + 1) % this.key.length;
        return result;
    }
}
