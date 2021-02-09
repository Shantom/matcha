package com.shantom.matcha.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shantom.matcha.entities.PeriodPackage;
import com.shantom.matcha.entities.ResultData;
import com.shantom.matcha.entities.SimpleData;
import com.shantom.matcha.utils.HttpUtils;
import com.shantom.matcha.utils.Metrics;
import com.shantom.matcha.utils.NumUtils;
import com.shantom.matcha.utils.UrlEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class CashQuestionInvestmentService {
    @Autowired
    private HttpUtils httpUtils;

    private final List<String> metricsForQ1 = Metrics.getMetricsForQ1();

    private final int years = 11;

    /**
     * 为某个股票向理杏仁请求相关数据
     * @param stock
     * @return
     */
    public String getMetricsForTenYears(String stock){
        PeriodPackage pack = new PeriodPackage();
        pack.setStockCodes(Collections.singletonList(stock));
        pack.setEndDate(new Date());

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.YEAR, -years);
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
        list.sort(Comparator.comparing(ResultData::getDate).reversed());
        return list;
    }

    public Map<String, Object> getResultMap(List<String> stocks){
        // 获取数据
        Map<String, List<ResultData>> data = new HashMap<>();
        int min = years;
        for (String stock : stocks) {
            String str = getMetricsForTenYears(stock);
            List<ResultData> resultData = parseResult(str);
            min = Math.min(min, resultData.size());
            data.put(stock, resultData);
        }
        // 截断
        for (List<ResultData> list : data.values()) {
            list.subList(min, list.size()).clear();
        }


        int left = 0;
        Map<String, Object> resp = new HashMap<>();
        Map<String, Double> netpop = new HashMap<>();
        Map<String, Double> freecash = new HashMap<>();
        Map<String, Double> expend = new HashMap<>();
        for (String m : metricsForQ1) {
            Map<String, Object> mmap = new HashMap<>();
            for (Map.Entry<String, List<ResultData>> entry : data.entrySet()) {
                List<ResultData> resultDataList = entry.getValue();
                List<String> ds = new ArrayList<>();
                for (ResultData resultData : resultDataList) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(resultData.getDate());
                    int year = c.get(Calendar.YEAR);
                    left = Math.max(left, year);
                    ds.add(NumUtils.ToHuman(resultData.getValue(m)));
                }
                double total;
                if(Metrics.getNetpop().equals(m)){
                    total = resultDataList.get(0).getValue(m)-resultDataList.get(resultDataList.size()-1).getValue(m);
                }else {
                    total = resultDataList.stream().mapToDouble(c -> c.getValue(m)).sum();
                }
                // 存总和
                if(Metrics.getExpend().equals(m)){
                    expend.put(entry.getKey(), total);
                }else if(Metrics.getFreecash().equals(m)){
                    freecash.put(entry.getKey(), total);
                }else if(Metrics.getNetpop().equals(m)){
                    netpop.put(entry.getKey(), total);
                }
                ds.add(NumUtils.ToHuman(total));
                mmap.put(entry.getKey(), ds);
            }
            resp.put(m, mmap);
        }

        // 计算小熊基本值
        Map<String, String> basic = new HashMap<>();
        for (String stock : stocks) {
            basic.put(stock, NumUtils.ToPercent(freecash.get(stock)/expend.get(stock)));
        }
        resp.put("basic", basic);
        //计算小熊增长值
        Map<String, String> inc = new HashMap<>();
        for (String stock : stocks) {
            inc.put(stock, NumUtils.ToPercent(netpop.get(stock)/expend.get(stock)));
        }
        resp.put("inc", inc);

        // 表头
        List<String> fields = new ArrayList<>();
        for (int i=left;i>left-min;i--){
            fields.add(String.valueOf(i));
        }
        resp.put("fields", fields);
        return resp;
    }

}
