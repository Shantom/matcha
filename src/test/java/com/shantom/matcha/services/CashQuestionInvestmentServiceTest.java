package com.shantom.matcha.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shantom.matcha.entities.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CashQuestionInvestmentServiceTest {
    @Autowired
    private CashQuestionInvestmentService service;

    @Test
    public void test(){
        service.getMetricsForTenYears("000895");
    }

    @Test
    public void test2(){
        String res = service.getMetricsForTenYears("000895");
        List<ResultData> resultData = service.parseResult(res);
        System.out.println(resultData);


    }

    @Test
    public void test3(){
        List<Integer> collect = IntStream.range(10, 1).boxed().collect(Collectors.toList());
        System.out.println(collect);
    }


}