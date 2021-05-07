package com.jschool.count;

import com.jschool.domain.Client;

public class ClientCount implements Comparable<ClientCount> {

    private Client client;
    private Integer quantity;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ClientCount{" +
                "client=" + client +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int compareTo(ClientCount that) {
        int comparedValue = this.quantity.compareTo(that.getQuantity());
        if (comparedValue == 0){
            comparedValue = this.getClient().getId().compareTo(that.getClient().getId());
        }
        return comparedValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientCount that = (ClientCount) o;
        return this.getClient().getId().equals(that.getClient().getId());
    }
}
