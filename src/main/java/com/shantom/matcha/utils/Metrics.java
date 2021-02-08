package com.shantom.matcha.utils;

import java.util.Arrays;
import java.util.List;

public class Metrics {

    public static List<String> getMetricsForQ1(){
        return Arrays.asList(getExpend(),getNetpop(),getFreecash());
    }

    public static String getExpend(){
        return Metrics.getMetric(Metrics.G.YEAR, Metrics.E.ACCU, Metrics.TF.EXPEND);
    }

    public static String getNetpop(){
        return Metrics.getMetric(Metrics.G.YEAR, Metrics.E.ACCU, Metrics.TF.NETPOP);
    }

    public static String getFreecash(){
        return Metrics.getMetric(Metrics.G.YEAR, Metrics.E.ACCU, Metrics.TF.FREECASH);
    }


    public static String getMetric(String G, String E, String TF){
        return G+"."+TF+"."+E;
    }

    public static class G {
        public final static String YEAR = "y";
        public final static String QUARTER = "q";
        public final static String HALFYEAR = "hy";
    }

    public static class E {
        /**
         * 累积
         */
        public final static String ACCU = "t";
        /**
         * 当期
         */
        public final String CUR = "c";

    }

    public static class TF {
        /**
         * 资本开支（购建固定资产、无形资产及其他长期资产所支付的现金）
         */
        public final static String EXPEND = "cfs.cpfpfiaolta";
        /**
         * 归属于母公司普通股股东的净利润
         */
        public final static String NETPOP = "ps.npatoshopc";
        /**
         * 自由现金流量
         */
        public final static String FREECASH = "m.fcf";

    }
}
