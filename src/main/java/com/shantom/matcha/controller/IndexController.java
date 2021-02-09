package com.shantom.matcha.controller;

import com.shantom.matcha.entities.ResultData;
import com.shantom.matcha.services.CashQuestionInvestmentService;
import com.shantom.matcha.utils.Metrics;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
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
    public String q1(Model model, @RequestParam("stock") String stocks){
        Map<String, Object> resultMap = service.getResultMap(Arrays.asList(stocks.split(",")));

        model.addAttribute("fields", resultMap.get("fields"));
        model.addAttribute("expend", resultMap.get(Metrics.getExpend()));
        model.addAttribute("netpop", resultMap.get(Metrics.getNetpop()));
        model.addAttribute("freecash", resultMap.get(Metrics.getFreecash()));
        model.addAttribute("basic", resultMap.get("basic"));
        model.addAttribute("inc", resultMap.get("inc"));

        return "q1/q1";
    }
}
