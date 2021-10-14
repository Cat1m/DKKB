package com.hungduy.honghunghospital.Model.setModel;

public class setKhaiBao {
    private String mucdich;
    private String khaibaoyte;
    private String ghichu;

    public setKhaiBao() {
    }

    public setKhaiBao(String mucdich, String khaibaoyte, String ghichu) {
        this.mucdich = mucdich;
        this.khaibaoyte = khaibaoyte;
        this.ghichu = ghichu;
    }

    public String getMucdich() {
        return mucdich;
    }

    public void setMucdich(String mucdich) {
        this.mucdich = mucdich;
    }

    public String getKhaibaoyte() {
        return khaibaoyte;
    }

    public void setKhaibaoyte(String khaibaoyte) {
        this.khaibaoyte = khaibaoyte;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }
}
