package com.shantom.matcha.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Data
@ToString
@Component
@ConfigurationProperties(prefix = "pp")
public class PrivateProperties {
    public List<String> tokens = Arrays.asList("21e8053c-80e9-4869-add7-27fb201c8345");
}
