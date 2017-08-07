package com.example.starxder.meal.Bean;

/**
 * Created by Administrator on 2017/7/31.
 */

public class TypeIncome {
    String dinnerIncome;
    String lunchIncome;
    String totalIncome;

    public TypeIncome(String dinnerIncome, String lunchIncome, String totalIncome) {
        this.dinnerIncome = dinnerIncome;
        this.lunchIncome = lunchIncome;
        this.totalIncome = totalIncome;
    }

    public String getDinnerIncome() {
        return dinnerIncome;
    }

    public void setDinnerIncome(String dinnerIncome) {
        this.dinnerIncome = dinnerIncome;
    }

    public String getLunchIncome() {
        return lunchIncome;
    }

    public void setLunchIncome(String lunchIncome) {
        this.lunchIncome = lunchIncome;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }
}
