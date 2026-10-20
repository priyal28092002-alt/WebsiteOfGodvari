package com.example.TypeCasting;

import java.math.BigDecimal;

public class TypeCasting {
	public static void main(String[] args) {
		TypeCastPojo ref = new TypeCastPojo();
		
		ref.setAmt(Double.MAX_VALUE);
		
		System.out.println(ref.getAmt());
		
		BigDecimal bigDec1 = new BigDecimal(ref.getAmt());
		System.out.println(bigDec1);
		System.out.println(bigDec1.toString());
		System.out.println(bigDec1.toPlainString());
		
		BigDecimal bigDec2 = BigDecimal.valueOf(ref.getAmt());
		System.out.println(bigDec2.toString());
		System.out.println(bigDec2.toPlainString());
	}
}
