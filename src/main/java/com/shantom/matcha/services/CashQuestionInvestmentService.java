package com.shantom.matcha.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shantom.matcha.entities.PeriodPackage;
import com.shantom.matcha.entities.ResultData;
import com.shantom.matcha.entities.SimpleData;
import com.shantom.matcha.utils.HttpUtils;
import com.shantom.matcha.utils.Metrics;
import com.shantom.matcha.utils.UrlEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
public class CashQuestionInvestmentService {
    @Autowired
    private HttpUtils httpUtils;

    private final List<String> metricsForQ1 = Metrics.getMetricsForQ1();


    /**
     * 为某个股票向理杏仁请求相关数据
     * @param stock
     * @return
     */
    public String getMetricsForTenYears(String stock){
        int years = -4;
        PeriodPackage pack = new PeriodPackage();
        pack.setStockCodes(Collections.singletonList(stock));
        pack.setEndDate(new Date());

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, years);
        Date time = instance.getTime();
        pack.setStartDate(time);

        pack.setMetricsList(metricsForQ1);

        String param = JSON.toJSONString(pack);
        JSONObject jsonObject = JSON.parseObject(param);

        String res = "";
        try {
            res = httpUtils.httpPost(jsonObject, UrlEnum.ANF);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 处理理杏仁返回的json串
     * @param str
     * @return
     */
    public List<ResultData> parseResult(String str){
        List<ResultData> list = new ArrayList<>();
        JSONObject resObject = JSON.parseObject(str);
        JSONArray dataArray = resObject.getJSONArray("data");
        for (Object data : dataArray) {
            if(!((JSONObject) data).containsKey("y")&&
                    !((JSONObject) data).containsKey("hy")&&
                    !((JSONObject) data).containsKey("q")){
                continue;
            }
            ResultData resultData = JSON.parseObject(((JSONObject) data).toJSONString(), ResultData.class);
            list.add(resultData);
        }
        return list;
    }

    public Map<String, Object> getResultMap(List<ResultData> resultDataList){
        Map<String, Object> map = new HashMap<>();
        for (String m : metricsForQ1) {
            Map<String, Object> mmap = new HashMap<>();

            List<Double> ds = new ArrayList<>();
            for (ResultData resultData : resultDataList) {
                Calendar c = Calendar.getInstance();
                c.setTime(resultData.getDate());
                int year = c.get(Calendar.YEAR);
                ds.add(resultData.getValue(m));
            }
            mmap.put("000895", ds);
            map.put(m, mmap);
        }
        return map;
    }

}
