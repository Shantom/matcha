package com.shantom.matcha.entities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class PeriodPackage extends BasePackage{
    @JSONField(format="yyyy-MM-dd")
    private Date startDate;
    @JSONField(format="yyyy-MM-dd")
    private Date endDate;
}
