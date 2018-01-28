package com.xilidou.framework.ioc.entity;

public class Hand {

    private Mouth mouth;
    private String number;

    public void waveHand() {
        System.out.println(number + "-------挥一挥手--------" + mouth);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "mouth=" + mouth.getCount() +
                ", number='" + number + '\'' +
                '}';
    }

    public Mouth getMouth() {
        return mouth;
    }

    public void setMouth(Mouth mouth) {
        this.mouth = mouth;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
