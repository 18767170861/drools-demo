package org.wmc.droolsdemo.entity;

import lombok.Data;

@Data
public class Sensor {

    private String description;

    private Double temp;

    public Sensor() {
    }

    public Sensor(String description, Double temp) {
        this.description = description;
        this.temp = temp;
    }
}
