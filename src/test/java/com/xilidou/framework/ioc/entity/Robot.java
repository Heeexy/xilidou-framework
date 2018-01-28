package com.xilidou.framework.ioc.entity;

public class Robot {

    private Hand hand;

    private Mouth mouth;

    private String code;

    public void show() {
        System.out.println("编号:" + code);
        hand.waveHand();
        mouth.speak();
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Mouth getMouth() {
        return mouth;
    }

    public void setMouth(Mouth mouth) {
        this.mouth = mouth;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
