package com.jschool.count;

public class JoinCountByProduct implements  Comparable<JoinCountByProduct> {

    private Long product_id;
    private Double resultAmount;

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Double getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(Double resultAmount) {
        this.resultAmount = resultAmount;
    }


    @Override
    public int compareTo(JoinCountByProduct that) {
        int result = -(this.resultAmount.compareTo(that.getResultAmount()));
        if (result == 0){
            result = this.product_id.compareTo(that.product_id);
        }
        return result;
    }
}
