package com.hungduy.honghunghospital.Model.getModel;

public class dataBaseToken {
    private String username;
    private String tokenfcm;
    private String tokenfiam;

    public dataBaseToken() {
    }

    public dataBaseToken(String username, String tokenfcm, String tokenfiam) {
        this.username = username;
        this.tokenfcm = tokenfcm;
        this.tokenfiam = tokenfiam;
    }

    public String getTokenfcm() {
        return tokenfcm;
    }

    public void setTokenfcm(String tokenfcm) {
        this.tokenfcm = tokenfcm;
    }

    public String getTokenfiam() {
        return tokenfiam;
    }

    public void setTokenfiam(String tokenfiam) {
        this.tokenfiam = tokenfiam;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
