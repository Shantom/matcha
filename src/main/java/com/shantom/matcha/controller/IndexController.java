package com.shantom.matcha.controller;

import com.shantom.matcha.entities.ResultData;
import com.shantom.matcha.services.CashQuestionInvestmentService;
import com.shantom.matcha.utils.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private CashQuestionInvestmentService service;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/s/q1")
    public String q1(Model model, String stock){
        String str = service.getMetricsForTenYears(stock);
        List<ResultData> resultData = service.parseResult(str);
        Map<String, Object> resultMap = service.getResultMap(resultData);

        model.addAttribute("expend", resultMap.get(Metrics.getExpend()));
        model.addAttribute("netpop", resultMap.get(Metrics.getNetpop()));
        model.addAttribute("freecash", resultMap.get(Metrics.getFreecash()));

        return "q1/q1";
    }
}
