package com.alacriti.merchant.util;

import java.util.UUID;

public class OrderReferenceGenerator {

    private OrderReferenceGenerator() {
    }

    public static String generate() {

        return "ORD-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();
    }
}