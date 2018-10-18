package com.mea.domain;

/**
 * Created by rongbin.xie on 2017/7/12.
 */
public class KNNBean  implements Comparable<KNNBean>{
    double c1;
    double c2;
    double c3;

    double distance;
    String type;

    public KNNBean(double c1, double c2, double c3, String type) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.type = type;
    }

    @Override
    public int compareTo(KNNBean arg0) {
        return Double.valueOf(this.distance).compareTo(arg0.distance);
    }
}
