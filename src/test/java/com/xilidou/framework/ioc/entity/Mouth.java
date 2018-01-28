package com.xilidou.framework.ioc.entity;

public class Mouth {
    private Hand hand;
    private String count;

    public void speak() {
        System.out.println(count + "++++++++++say hello world+++++++++" + hand);
    }

    @Override
    public String toString() {
        return "Mouth{" +
                "hand=" + hand.getNumber() +
                ", count='" + count + '\'' +
                '}';
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
