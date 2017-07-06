import static org.junit.Assert.assertEquals;

import java.util.Scanner;

public class runALU {
	public static void main(String[] args){
		ALU alu = new ALU();
		String a = "0110100011000";
		String b = "0110100011100";
		char c = '0';
		int d = 4;
//		int e = 23;
//	System.out.println(alu.floatAddition(
//				alu.floatRepresentation(Float.MAX_VALUE+"", 8, 23), 
//				alu.floatRepresentation("-51.607", 8, 23)
//				, 8, 23, 0));
//		System.out.println(alu.floatRepresentation(Float.MAX_VALUE+"", 8, 23) + "WANTEDRESULT");
//		System.out.println(alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 4));
//		System.out.println(alu.floatRepresentation("86", 6, 9) + "WANTEDRESULT");
	
		//assertEquals("0.005859375", this.alu.floatTrueValue("000001100", 4, 4));
//		System.out.println(alu.floatRepresentation("0.875", 8, 23));
		System.out.println(alu.floatTrueValue("000001100", 4, 4));
//	//	System.out.println(alu.integerDivision("0110", "0000", 4));
//		System.out.println(alu.floatMultiplication("0"+ "00111110"+ "100"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000",
//				"1"+ "01000000"+ "000"	+ "0010"+ "1000"+ "0000"+ "0000"+ "0000", 8, 23));	
//		System.out.println(alu.floatDivision(alu.floatRepresentation("0.4375", 8, 23), 
//				alu.floatRepresentation("0.5", 8, 23), 8, 23) + "result");
//		
//		System.out.println(alu.floatRepresentation("0.4375", 8, 23));
//		System.out.println(alu.floatRepresentation("0.5", 8, 23));
//		System.out.println(alu.integerDivision("0111000000000000000000000", "0100000000000000000000000", 28));
//		System.out.println(alu.integerDivision("1000", "1111", 4));
//		System.out.println(alu.integerTrueValue("0111000000000000000000000"));
//		System.out.println(alu.integerTrueValue("0100000000000000000000000"));
//		System.out.println(alu.integerTrueValue("0000011000000000000000000000"));
//		System.out.println(alu.floatMultiplication("0"+ "11111111"+ "100"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000",
//				"1"+ "11000000"+ "000"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000", 8, 23));
//		System.out.println(alu.floatMultiplication(alu.floatRepresentation("0.5", 8, 23), 
//				alu.floatRepresentation("0.4375", 8, 23), 8, 23));
//		System.out.println(alu.floatRepresentation("0.21875", 8, 23));
//		System.out.println(alu.integerMultiplication("1001", "1010", 4));
//		System.out.println(alu.integerDivision("01100000", "00100000", 8));
//		System.out.println(alu.floatDivision(
//				alu.floatRepresentation("0.75", 8, 23), 
//				alu.floatRepresentation("-65.25", 8, 23), 8, 23));
//		System.out.println(alu.floatRepresentation("0", 8, 23));
//		System.out.println(alu.integerTrueValue("01000000000000000000000000000000000000000000000000000000000000000"));
//		System.out.println(alu.floatRepresentation("0.0146484375", 4,4));
//		System.out.println(alu.floatTrueValue("00000000000000000000000000000010",8, 23));
//        System.out.println(alu.integerDivision("1010", "0101", 4));
//        System.out.println(alu.integerDivision("1010", "0110", 4));
//        System.out.println(alu.integerDivision("1010", "0111", 4));
//        System.out.println(alu.integerDivision("1010", "1011", 4));
//        System.out.println(alu.integerDivision("1010", "1010", 4));
//        System.out.println(alu.integerDivision("1010", "1001", 4));



//		System.out.println(alu.integerNormalDivision("0000000000000000000001000", "0000000000000000000000100", 28));
		
//		System.out.println(alu.floatMultiplication("01000000001000000000000000000000", "0000000000100000000000000000000", 8, 23));
	}
}