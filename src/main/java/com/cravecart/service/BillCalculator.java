package com.cravecart.service;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class BillCalculator {
 public static final BigDecimal TAX_RATE=new BigDecimal("0.05");
 public static final BigDecimal DELIVERY_CHARGE=new BigDecimal("30.00");
 public BigDecimal tax(BigDecimal subtotal){return subtotal.multiply(TAX_RATE).setScale(2,RoundingMode.HALF_UP);}
 public BigDecimal tax(BigDecimal subtotal,BigDecimal rate){return subtotal.multiply(rate).setScale(2,RoundingMode.HALF_UP);}
 public BigDecimal total(BigDecimal subtotal){return subtotal.add(tax(subtotal)).add(DELIVERY_CHARGE).setScale(2,RoundingMode.HALF_UP);}
}
