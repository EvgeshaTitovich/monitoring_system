package com.psy.entity;

import com.psy.Application;
import com.psy.Application.Info;

public class Indication {


    private int id;
    private double p ;
    private double q;
    private double u;
    private double i;
    private double phi;
    private double v;

    public Indication(){

    }

    @Override
    public String toString() {
        return "Indication{" +
                "id=" + id +
                ", p=" + p +
                ", q=" + q +
                ", u=" + u +
                ", i=" + i +
                ", phi=" + phi +
                ", v=" + v +
                '}';
    }

    public Indication(int id, Double p, Double q, Double u, Double i, Double phi, Double v) {
        this.id = id;
        this.p = p;
        this.q = q;
        this.u = u;
        this.i = i;
        this.phi = phi;
        this.v = v;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getP() {
        return p;
    }

    public void setP(Double p) {
        this.p = p;
    }

    public Double getQ() {
        return q;
    }

    public void setQ(Double q) {
        this.q = q;
    }

    public Double getU() {
        return u;
    }

    public void setU(Double u) {
        this.u = u;
    }

    public Double getI() {
        return i;
    }

    public void setI(Double i) {
        this.i = i;
    }

    public Double getPhi() {
        return phi;
    }

    public void setPhi(Double phi) {
        this.phi = phi;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

}
