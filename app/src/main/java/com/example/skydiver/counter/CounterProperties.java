package com.example.skydiver.counter;

/**
 * Created by root on 31.03.15.
 */
public class CounterProperties {
    private short replyCount;
    private short plusCount;
    private short minusCount;

    public CounterProperties(short replyCount, short plusCount, short minusCount) {
        this.replyCount = replyCount;
        this.plusCount = plusCount;
        this.minusCount = minusCount;
    }

    public CounterProperties() {
    }

    public short getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(short replyCount) {
        this.replyCount = replyCount;
    }

    public short getPlusCount() {
        return plusCount;
    }

    public void setPlusCount(short plusCount) {
        this.plusCount = plusCount;
    }

    public short getMinusCount() {
        return minusCount;
    }

    public void setMinusCount(short minusCount) {
        this.minusCount = minusCount;
    }

    @Override
    public String toString() {
        return replyCount + "\n" + plusCount + "\n" + minusCount;
    }
}
