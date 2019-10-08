package com.example.navbarapplication;

public class SensorData {

    private String name,direction;
    private int value;

    SensorData(){}

    public SensorData(String name, String direction, int value) {
        this.name = name;
        this.direction = direction;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
