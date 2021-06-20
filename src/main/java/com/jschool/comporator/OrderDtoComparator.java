package com.jschool.comporator;

import com.jschool.DTO.OrderDTO;

import java.util.Comparator;

public class OrderDtoComparator implements Comparator<OrderDTO> {

    @Override
    public int compare(OrderDTO o1, OrderDTO o2) {
        if (o1.getDateOfOrder().after(o2.getDateOfOrder()))
            return 1;
        if (o1.getDateOfOrder().before(o2.getDateOfOrder()))
            return -1;
        return 0;
    }
}
