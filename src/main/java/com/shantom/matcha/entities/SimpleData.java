package com.shantom.matcha.entities;

import lombok.Data;

import java.util.List;

@Data
public class SimpleData {
    private String stock;
    private List<Double> vals;
}
