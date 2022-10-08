package com.example.khedutmitra;

public class APMC {

    String state;
    String district;
    String market;

    public APMC(String state, String district, String market) {
        this.state = state;
        this.district = district;
        this.market = market;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }
}
