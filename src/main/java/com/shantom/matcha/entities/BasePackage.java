package com.shantom.matcha.entities;

import com.alibaba.fastjson.annotation.JSONField;
import com.shantom.matcha.utils.PrivateProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class BasePackage {
    private String token;
    private List<String> stockCodes;
    private List<String> metricsList;

    public BasePackage() {
        token = new PrivateProperties().tokens.get(0);
    }

    @Override
    public String toString() {
        return "BasePackage{" +
                "token='" + token + '\'' +
                ", stockCodes=" + stockCodes +
                ", metricsList=" + metricsList +
                '}';
    }
}
