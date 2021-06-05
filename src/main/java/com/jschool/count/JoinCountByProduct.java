package com.jschool.count;

public class JoinCountByProduct implements  Comparable<JoinCountByProduct> {

    private Long product_id;
    private Double resultAmount;
    private Long quantity;
    private String brand;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
