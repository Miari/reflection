package com.boroday.reflection.testClasses;

public class ClassWithDifferentDataTypes {
    private byte privateByte = 5;
    public byte publicByte = 5;
    private int privateInteger = 5;
    public int publicInteger = 5;
    private short privateShort = 5;
    public short publicShort = 5;
    private long privateLong = 5;
    public long publicLong = 5;
    private double privateDouble = 5.0;
    public double publicDouble = 5.0;
    private float privateFloat = 5.0f;
    public float publicFloat = 5.0f;
    private char privateChar = 'a';
    public char publicChar = 'a';
    private boolean privateBoolean = true;
    public boolean publicBoolean = true;
    private String privateString = "Hi!";
    public String publicString = "Hi!";

    public ClassWithDifferentDataTypes(){

    }

    public byte getPrivateByte() {
        return privateByte;
    }

    public byte getPublicByte() {
        return publicByte;
    }

    public int getPrivateInteger() {
        return privateInteger;
    }

    public int getPublicInteger() {
        return publicInteger;
    }

    public short getPrivateShort() {
        return privateShort;
    }

    public short getPublicShort() {
        return publicShort;
    }

    public long getPrivateLong() {
        return privateLong;
    }

    public long getPublicLong() {
        return publicLong;
    }

    public double getPrivateDouble() {
        return privateDouble;
    }

    public double getPublicDouble() {
        return publicDouble;
    }

    public float getPrivateFloat() {
        return privateFloat;
    }

    public float getPublicFloat() {
        return publicFloat;
    }

    public char getPrivateChar() {
        return privateChar;
    }

    public char getPublicChar() {
        return publicChar;
    }

    public boolean getPrivateBoolean() {
        return privateBoolean;
    }

    public boolean getPublicBoolean() {
        return publicBoolean;
    }

    public String getPrivateString() {
        return privateString;
    }

    public String getPublicString() {
        return publicString;
    }
}
