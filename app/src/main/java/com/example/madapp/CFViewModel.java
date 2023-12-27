package com.example.madapp;

import androidx.lifecycle.ViewModel;

public class CFViewModel extends ViewModel {

    private Double AnsQ1;
    private Double AnsQ2;
    private Double AnsQ3;

    public Double getAnsQ1() {
        return AnsQ1;
    }

    public void setAnsQ1(Double AnsQ1) {
        this.AnsQ1 = AnsQ1;
    }

    public Double getAnsQ2() {
        return AnsQ2;
    }

    public void setAnsQ2(Double AnsQ2) {
        this.AnsQ2 = AnsQ2;
    }

    public Double getAnsQ3() {
        return AnsQ3;
    }

    public void setAnsQ3(Double AnsQ3) {
        this.AnsQ3 = AnsQ3;
    }

    public Double getTotal() {
        double total = 0.0;

        if (AnsQ1 != null) {
            total += (AnsQ1*0.7130)/1000;
        }

        if (AnsQ2 != null) {
            total += (AnsQ2*2.3000)/1000;
        }

        if (AnsQ3 != null) {
            total += (AnsQ3*11.38)/1000;
        }

        return total;
    }
}
