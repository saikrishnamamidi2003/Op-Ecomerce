package com.alacriti.merchant.util;

import java.util.UUID;

public final class PaymentReferenceGenerator {

    private PaymentReferenceGenerator() {
    }

    public static String generate() {

        return "PAY-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase();

    }

}