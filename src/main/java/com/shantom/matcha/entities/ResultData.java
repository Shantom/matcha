package com.shantom.matcha.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@Slf4j
public class ResultData {
    private String currency;
    private Date standardDate;
    private Date date;
    private Date reportDate;
    private String reportType;
    private String stockCode;
    private String y;
    private String q;
    private String hy;

    /**
     * 形如q.cfs.cpfpfiaolta.t
     * @param metric
     * @return
     */
    public Double getValue(String metric){
        String[] fields = metric.split("\\.");
        JSONObject jsonObject;
        if("y".equals(fields[0])){
            jsonObject = JSON.parseObject(y);
        }else if("q".equals(fields[0])){
            jsonObject = JSON.parseObject(q);
        }else if("hy".equals(fields[0])){
            jsonObject = JSON.parseObject(hy);
        }else {
            log.error("Wrong data format, {}",metric);
            return null;
        }
        return jsonObject.getJSONObject(fields[1]).getJSONObject(fields[2]).getDouble(fields[3]);
    }
}
