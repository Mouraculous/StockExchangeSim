package com.acme.acmetrade.validators;

import com.acme.acmetrade.domain.Order;
import com.acme.acmetrade.domain.OrderType;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OrderValidator implements Validator {
    private static final int MIN_AMOUNT_OF_ORDERS = 0;

    @Override
    public boolean supports(Class testedClass) {
        return Order.class.equals(testedClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;

        if (order.getAmount() <= MIN_AMOUNT_OF_ORDERS) {
            errors.rejectValue("amount", "min.amount.field");
        }

        if (
                !order.getOrderType().equals(OrderType.LIMIT)
                && !order.getOrderType().equals(OrderType.MARKET)
                ) {
            errors.rejectValue("orderType", "order.type.field");
        }
    }
}
