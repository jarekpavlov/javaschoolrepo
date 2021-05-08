package com.jschool.count;

public class JoinCountByClient implements Comparable<JoinCountByClient> {

    private Long client_id;
    private Double resultAmount;

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Double getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(Double resultAmount) {
        this.resultAmount = resultAmount;
    }

    @Override
    public String toString() {
        return "JoinCountByClient{" +
                "client_id=" + client_id +
                ", resultAmount=" + resultAmount +
                '}';
    }

    @Override
    public int compareTo(JoinCountByClient that) {
        int result = -(this.resultAmount.compareTo(that.getResultAmount()));
        if (result == 0) {
            result = this.client_id.compareTo(that.getClient_id());
        }
        return result;
    }
}
