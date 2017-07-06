import static org.junit.Assert.*;

import org.junit.Test;


public class ALU1 {
	ALU alu = new ALU();
	@Test
	public void test2() {
		assertEquals("00000",alu.integerRepresentation("0", 5));
		assertEquals("11111",alu.integerRepresentation("-1", 5));
		assertEquals("00101",alu.integerRepresentation("5", 5));
		assertEquals("11111001",alu.integerRepresentation("-7", 8));
	}
	@Test
	public void test5() {
		assertEquals("0",alu.integerTrueValue("0"));
	    assertEquals("17",alu.integerTrueValue("010001"));
		assertEquals("-7",alu.integerTrueValue("11111001"));
		assertEquals("3",alu.integerTrueValue("0011"));
		assertEquals("-1",alu.integerTrueValue("1"));
		assertEquals("-1",alu.integerTrueValue("111111"));
	}
	
	
	@Test
	public void test7() {
		assertEquals("00000",alu.negation("11111"));
		assertEquals("11111",alu.negation("00000"));
		assertEquals("00101",alu.negation("11010"));
		assertEquals("11111001",alu.negation("00000110"));
	}
	@Test
	public void test8() {
		assertEquals("00000",alu.leftShift("01111", 5));
		assertEquals("11111",alu.leftShift("11111", 0));
		assertEquals("00100",alu.leftShift("00010", 1));
		assertEquals("11111000",alu.leftShift("01011111", 3));
	}
	@Test
	public void test9() {
		assertEquals("110111001",alu.ariRightShift("101110011", 1));
		assertEquals("11111",alu.ariRightShift("11111", 0));
		assertEquals("00001",alu.ariRightShift("00010", 1));
		assertEquals("00000",alu.ariRightShift("00001", 3));
	}
	@Test
	public void test10() {
		assertEquals("010111001",alu.logRightShift("101110011", 1));
		assertEquals("11111",alu.logRightShift("11111", 0));
		assertEquals("00001",alu.logRightShift("00010", 1));
		assertEquals("00000",alu.logRightShift("00001", 3));
		assertEquals("00010",alu.logRightShift("10001", 3));
	}
	@Test
	public void test11() {
		assertEquals("00",alu.fullAdder('0', '0', '0'));
		assertEquals("01",alu.fullAdder('1', '0', '0'));
		assertEquals("10",alu.fullAdder('1', '1', '0'));
		assertEquals("01",alu.fullAdder('0', '0', '1'));
		assertEquals("10",alu.fullAdder('1', '0', '1'));
		assertEquals("11",alu.fullAdder('1', '1', '1'));
	}
//	@Test
//	public void test12() {
//		assertEquals("000101001",alu.claAdder("00001100", "00011100", '1'));
//		assertEquals("000110100",alu.claAdder("00100001","00010010",'1'));
//		assertEquals("100000000",alu.claAdder("10000000","10000000",'0'));
//	}
	@Test
	public void test13() {
//		assertEquals("01111100111",alu.integerAddition("1000101","0100010",10));
		assertEquals("01111",alu.integerAddition("1101","0010",4));
//		assertEquals("000111",alu.integerAddition("0101","0010",5));
//		assertEquals("011111",alu.integerAddition("1101","0010",5));
//		assertEquals("010111",alu.integerAddition("1101","1010",5));
//		assertEquals("110001",alu.integerAddition("1101","1010",'1',5));
		assertEquals("011110100111011100110010100101001",alu.integerAddition(
				"11001010001010010101001010010101",
				"00101010110001010001001010010100",32));
		assertEquals("11010",alu.integerAddition("0111","0011",4));
		assertEquals("00100",alu.integerAddition("0100","0000",4));
		assertEquals("10101",alu.integerAddition("1000","1101",4));
		assertEquals("00000",alu.integerAddition("1001","0111",4));
	}
	@Test
	public void test14() {
		assertEquals("11011",alu.integerSubtraction("0101","1010",4));
		assertEquals("000001011",alu.integerSubtraction("0101","1010",8));
		assertEquals("00100",alu.integerSubtraction("0111","0011",4));
		assertEquals("00100",alu.integerSubtraction("0100","0000",4));
		assertEquals("01011",alu.integerSubtraction("1000","1101",4));
		assertEquals("10011",alu.integerSubtraction("1001","0110",4));
	}
	//添加溢出位，暂时将4改为8
	@Test
	public void test15() {
		assertEquals("000000110",alu.integerMultiplication("0011", "0010", 8));
		assertEquals("011111000",alu.integerMultiplication("1110", "0100", 8));
		assertEquals("000001000",alu.integerMultiplication("0100", "0010", 8));
		assertEquals("000000110",alu.integerMultiplication("1101", "1110", 8));
	}
	//添加溢出位
	@Test
	public void test16() {
		assertEquals("000100001",alu.integerDivision("0111", "0011", 4));
		assertEquals("011100001",alu.integerDivision("0111", "1101", 4));
		assertEquals("011101111",alu.integerDivision("1001", "0011", 4));
		assertEquals("000101111",alu.integerDivision("1001", "1101", 4));
		assertEquals("000110000",alu.integerDivision("0110", "0010", 4));
		assertEquals("000110000",alu.integerDivision("1010", "1110", 4));
		assertEquals("011010000",alu.integerDivision("0110", "1110", 4));
		assertEquals("011010000",alu.integerDivision("1010", "0010", 4));
	}
	@Test
	public void test3() {
		assertEquals("11000000111000000000000000000000",alu.floatRepresentation("-7", 8,23));
		assertEquals("10111111110000000000000000000000",alu.floatRepresentation("-1.5",8,23));
//		assertEquals("01000011110000000001000010100100",alu.floatRepresentation("384.13", 8,23));
		assertEquals("00111111010000000000000000000000",alu.floatRepresentation("0.75", 8,23));
		assertEquals("11000010100000101000000000000000",alu.floatRepresentation("-65.25", 8,23));
		assertEquals("0100110101100101",alu.floatRepresentation("217.36", 6,9));
//		assertEquals("0100010000110011",alu.floatRepresentation("8.79", 6,9));
		assertEquals("0100101010101101",alu.floatRepresentation("85.643", 6,9));
//		assertEquals("0011101011110110",alu.floatRepresentation("0.37", 6,9));
//		assertEquals("0100100100111011",alu.floatRepresentation("51.658", 6,9));
//		assertEquals("0100100100111010",alu.floatRepresentation("51.607",6,9));
//		assertEquals("01111111100000000000000000000000",alu.floatRepresentation(Double.MAX_VALUE+"", 8,23));
//		assertEquals("01111111100000000000000000000000",alu.floatRepresentation(Double.MAX_VALUE/2+"", 8,23));
//		assertEquals("01111111100000000000000000000000",alu.floatRepresentation(Double.MAX_VALUE/100+"", 8,23));
//		assertEquals("11111111100000000000000000000000",alu.floatRepresentation(-Double.MAX_VALUE+"", 8,23));
//		assertEquals("01111111100000000000000000000001",alu.floatRepresentation("NaN", 8,23));
//		assertEquals("00000000000000000000000000000000",alu.floatRepresentation("0", 8,23));
//		assertEquals("00000000100000000000000000000000",alu.floatRepresentation(Float.MIN_NORMAL+"", 8,23));
//		assertEquals("00000000000000000000000000000010",alu.floatRepresentation(Float.MIN_VALUE*2+"", 8,23));
	}
//	@Test
//	public void test4() {
//		assertEquals("0"
//				+ "11111111111"
//				+ "0000000000000000000000000000000000000000000000000001",
//				alu.ieee754("NaN", 64));
////		assertEquals("0"
////				+ "11111111110"
////				+ "1111111111111111111111111111111111111111111111111111",
////				alu.ieee754(Double.MAX_VALUE+"", 64));
////		assertEquals("0"
////				+ "00000000001"
////				+ "0000000000000000000000000000000000000000000000000000",
////				alu.ieee754(Double.MIN_NORMAL+"", 64));
////		assertEquals("0"
////				+ "00000000000"
////				+ "0000000000000000000000000000000000000000000000000001",
////				alu.ieee754(Double.MIN_VALUE+"", 64));
//	}
	@Test
	public void test6() {
		assertEquals("0",alu.floatTrueValue(
				"0"
				+ "00000000"
				+ "00000000000000000000000", 8, 23));
		assertEquals("0",alu.floatTrueValue(
				"1"
				+ "00000000"
				+ "00000000000000000000000", 8, 23));
		assertEquals("+Inf",alu.floatTrueValue(
				"0"
				+ "11111111"
				+ "00000000000000000000000", 8, 23));
		assertEquals("-Inf",alu.floatTrueValue(
				"1"
				+ "11111111"
				+ "00000000000000000000000", 8, 23));
		assertEquals("NaN",alu.floatTrueValue(
				"0"
				+ "11111111"
				+ "00000000000000000000100", 8, 23));
		assertEquals("NaN",alu.floatTrueValue(
				"1"
				+ "11111111"
				+ "00000001111000000000001", 8, 23));
		assertEquals("384.1300048828125",alu.floatTrueValue(
				"0"
				+ "10000111"
				+ "10000000001000010100100", 8, 23));
		assertEquals("85.625",alu.floatTrueValue(
						"0"
						+ "100101"
						+ "010101101", 6, 9));
		assertEquals("-65.25",alu.floatTrueValue(
				"1"
				+ "10000101"
				+ "00000101000000000000000", 8, 23));
	}
	@Test
	public void test17() {
		//缁堜簬瀹屾垚浜嗭紝澶紑蹇冧簡o(*锟ｂ柦锟�*)銉�
		assertEquals(
				"0"+alu.floatRepresentation("3", 8,23),
				alu.floatAddition(
						alu.floatRepresentation("1", 8,23), 
						alu.floatRepresentation("2", 8,23)
						, 8,23, 0));

//		assertEquals(
//				"0"+alu.floatRepresentation("1.4", 8,23),
//				alu.floatAddition(
//						alu.floatRepresentation("1.1", 8,23),
//						alu.floatRepresentation("0.3", 8,23)
//						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("7.368", 8,23),
				alu.floatAddition(
						alu.floatRepresentation("1.256", 8,23), 
						alu.floatRepresentation("6.112", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("-7.368", 8,23),
				alu.floatAddition(
						alu.floatRepresentation("-1.256", 8,23), 
						alu.floatRepresentation("-6.112", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("-64.5", 8,23),
				alu.floatAddition(
						alu.floatRepresentation("0.75 ", 8,23), 
						alu.floatRepresentation("-65.25", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("0.9375", 8,23),
				alu.floatAddition(
						alu.floatRepresentation("0.5 ", 8,23), 
						alu.floatRepresentation("0.4375", 8,23)
						,8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("0.0625", 8,23),
				alu.floatAddition(
						alu.floatRepresentation("0.5 ",  8,23), 
						alu.floatRepresentation("-0.4375",  8,23)
						,  8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("-63.5",  8,23),
				alu.floatAddition(
						alu.floatRepresentation("1.75 ",  8,23), 
						alu.floatRepresentation("-65.25", 8,23)
						,  8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("85.875", 6,9),
				alu.floatAddition(
						alu.floatRepresentation("85.643",6,9), 
						alu.floatRepresentation("0.37", 6,9)
						, 6,9, 0));
//		assertEquals( 
//				"0"+alu.floatRepresentation("86",6,9),
//				alu.floatAddition(
//						alu.floatRepresentation("85.643",6,9), 
//						alu.floatRepresentation("0.37", 6,9)
//						, 6,9,6));
		assertEquals(
				"0"+alu.floatRepresentation("226", 6,9),
				alu.floatAddition(
						alu.floatRepresentation("217.36", 6,9), 
						alu.floatRepresentation("8.79", 6,9)
						, 6,9, 0));
		assertEquals(
				"0"+alu.floatRepresentation("226", 6,9),
				alu.floatAddition(
						alu.floatRepresentation("217.36", 6,9), 
						alu.floatRepresentation("8.79", 6,9)
						, 6,9,6));
		assertEquals(
				"0"+alu.floatRepresentation("208.5", 6,9),
				alu.floatAddition(
						alu.floatRepresentation("217.36", 6,9), 
						alu.floatRepresentation("-8.79", 6,9)
						,6,9, 0));
//		assertEquals(
//				"0"+alu.floatRepresentation("208.5",6,9),
//				alu.floatAddition(
//						alu.floatRepresentation("217.36",6,9), 
//						alu.floatRepresentation("-8.79",6,9)
//						, 6,9,6));
		assertEquals(
				"0"+alu.floatRepresentation("0.0625", 6,9),
				alu.floatAddition(
						alu.floatRepresentation("51.658", 6,9), 
						alu.floatRepresentation("-51.607", 6,9)
						, 6,9, 0));
		assertEquals(
				"0"+alu.floatRepresentation("0.0625", 6,9),
				alu.floatAddition(
						alu.floatRepresentation("51.658", 6,9), 
						alu.floatRepresentation("-51.607", 6,9)
						, 6,9,6));
//		assertEquals(
//				alu.floatRepresentation(Float.MAX_VALUE+"", 8,23)+"0",
//				alu.floatAddition(
//						alu.floatRepresentation(Float.MAX_VALUE+"", 8,23), 
//						alu.floatRepresentation("-51.607", 8,23)
//						, 8,23,0));
//		assertEquals(
//				alu.floatRepresentation(Float.MAX_VALUE+"", 8,23)+"0",
//				alu.floatAddition(
//						alu.floatRepresentation(Float.MAX_VALUE+"", 8,23), 
//						alu.floatRepresentation("51.607", 8,23)
//						, 8,23,0));
//		assertEquals(
//				"0"	+ "00000000"
//						+ "000"
//						+ "0000"
//						+ "0000"
//						+ "0000"
//						+ "0000"
//						+ "0001"+"1",
//				alu.floatAddition(
//						alu.floatRepresentation(Float.MIN_VALUE*2+"", 8,23), 
//						"1"	+ "00000000"
//								+ "000"
//								+ "0000"
//								+ "0000"
//								+ "0000"
//								+ "0000"
//								+ "0001"
//						, 8,23,0));
						
	}
	@Test
	public void test18() {
		assertEquals(
				"0"+alu.floatRepresentation("3", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("1", 8,23), 
						alu.floatRepresentation("-2", 8,23)
						, 8,23, 0));
//		assertEquals(
//				"0"+alu.floatRepresentation("1.4", 8,23),
//				alu.floatSubtraction(
//						alu.floatRepresentation("1.1", 8,23), 
//						alu.floatRepresentation("-0.3", 8,23)
//						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("7.368", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("1.256", 8,23), 
						alu.floatRepresentation("-6.112", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("-7.368", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("-1.256", 8,23), 
						alu.floatRepresentation("6.112", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("-64.5", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("0.75 ", 8,23), 
						alu.floatRepresentation("65.25", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("0.9375", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("0.5 ", 8,23), 
						alu.floatRepresentation("-0.4375", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("0.0625", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("0.5 ", 8,23), 
						alu.floatRepresentation("0.4375", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("-63.5", 8,23),
				alu.floatSubtraction(
						alu.floatRepresentation("1.75 ", 8,23), 
						alu.floatRepresentation("65.25", 8,23)
						, 8,23, 0));
		assertEquals(
				"0"+alu.floatRepresentation("85.875", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("85.643", 6,9), 
						alu.floatRepresentation("-0.37", 6,9)
						, 6,9, 0));
//		assertEquals(
//				"0"+alu.floatRepresentation("86", 6,9),
//				alu.floatSubtraction(
//						alu.floatRepresentation("85.643", 6,9), 
//						alu.floatRepresentation("-0.37", 6,9)
//						, 6,9, 6));
		assertEquals(
				"0"+alu.floatRepresentation("226", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("217.36", 6,9), 
						alu.floatRepresentation("-8.79", 6,9)
						, 6,9, 0));
		assertEquals(
				"0"+alu.floatRepresentation("226", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("217.36", 6,9), 
						alu.floatRepresentation("-8.79", 6,9)
						, 6,9, 6));
		assertEquals(
				"0"+alu.floatRepresentation("208.5", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("217.36", 6,9), 
						alu.floatRepresentation("8.79", 6,9)
						, 6,9, 0));
//		assertEquals(
//				"0"+alu.floatRepresentation("208.5", 6,9),
//				alu.floatSubtraction(
//						alu.floatRepresentation("217.36", 6,9), 
//						alu.floatRepresentation("8.79", 6,9)
//						, 6,9, 6));
		assertEquals(
				"0"+alu.floatRepresentation("0.0625", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("51.658", 6,9), 
						alu.floatRepresentation("51.607", 6,9)
						, 6,9, 0));
		assertEquals(
				"0"+alu.floatRepresentation("0.0625", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("51.658", 6,9), 
						alu.floatRepresentation("51.607", 6,9)
						, 6,9, 6));
		assertEquals(
				"0"+alu.floatRepresentation("-51.607", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("0", 6,9), 
						alu.floatRepresentation("51.607", 6,9)
						, 6,9, 6));
		assertEquals(
				"0"+alu.floatRepresentation("51.607", 6,9),
				alu.floatSubtraction(
						alu.floatRepresentation("0", 6,9), 
						alu.floatRepresentation("-51.607", 6,9)
						, 6,9, 6));
						
	}
	//结果前加"0"+
	@Test
	public void test19(){
		assertEquals("0"+alu.floatRepresentation("0.21875", 8, 23),alu.floatMultiplication(
				alu.floatRepresentation("0.5", 8, 23), 
				alu.floatRepresentation("0.4375", 8, 23), 8, 23));
		assertEquals("0"+alu.floatRepresentation("0.328125", 8, 23),alu.floatMultiplication(
				alu.floatRepresentation("0.75", 8, 23), 
				alu.floatRepresentation("0.4375", 8, 23), 8, 23));
		assertEquals("0"+alu.floatRepresentation("-0.328125", 8, 23),alu.floatMultiplication(
				alu.floatRepresentation("-0.75", 8, 23), 
				alu.floatRepresentation("0.4375", 8, 23), 8, 23));
		assertEquals("0"+alu.floatRepresentation("0.328125", 8, 23),alu.floatMultiplication(
				alu.floatRepresentation("-0.75", 8, 23), 
				alu.floatRepresentation("-0.4375", 8, 23), 8, 23));
		assertEquals("0"+alu.floatRepresentation("-0.328125", 8, 23),alu.floatMultiplication(
				alu.floatRepresentation("0.75", 8, 23), 
				alu.floatRepresentation("-0.4375", 8, 23), 8, 23));
		assertEquals("0"+alu.floatRepresentation("-48.9375", 8, 23),alu.floatMultiplication(
				alu.floatRepresentation("0.75", 8, 23), 
				alu.floatRepresentation("-65.25",8, 23), 8, 23));
//		assertEquals("0"+alu.floatRepresentation(Float.MAX_VALUE+"", 8,23),alu.floatMultiplication(
//				alu.floatRepresentation("1.25", 8,23),
//				alu.floatRepresentation(Float.MAX_VALUE+"", 8,23), 8,23));
//		assertEquals(alu.floatRepresentation(Float.MAX_VALUE+"", 8,23),alu.floatMultiplication(
//				alu.floatRepresentation("1", 8,23), 
//				alu.floatRepresentation(Float.MAX_VALUE+"", 8,23), 8,23));
//		assertEquals(alu.floatRepresentation(Float.MIN_VALUE+"", 8,23),alu.floatMultiplication(
//				alu.floatRepresentation("1", 8,23), 
//				alu.floatRepresentation(Float.MIN_VALUE+"", 8,23), 8,23));
//		assertEquals(
//				"0"
//				+ "00000000"
//				+ "000"
//				+ "0000"
//				+ "0000"
//				+ "0000"
//				+ "0000"
//				+ "0001",alu.floatMultiplication(
//				alu.floatRepresentation("0.5", 8,23), 
//				alu.floatRepresentation(Float.MIN_VALUE*2+"", 8,23), 8,23));
		assertEquals(
				"01"+ "00000000"+ "110"+ "0001"+ "1110"+ "0000"+ "0000"+ "0000",
				alu.floatMultiplication(
						"0"+ "00111111"+ "100"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000",
						"1"+ "01000000"+ "000"	+ "0010"+ "1000"+ "0000"+ "0000"+ "0000", 8, 23));
		assertEquals(
				"01"+ "00000000"+ "0110"+ "0001"+ "1110"+ "0000"+ "0000"+ "000",
				alu.floatMultiplication(
						"0"+ "00111110"+ "100"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000",
						"1"+ "01000000"+ "000"	+ "0010"+ "1000"+ "0000"+ "0000"+ "0000", 8, 23));
		// 前两位是01 还是11
//		assertEquals(
//				"01"+ "11111111"+ "0000"+ "0000"+ "0000"+ "0000"+ "0000"+ "000",
//				alu.floatMultiplication(
//						"0"+ "11111111"+ "100"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000",
//						"1"+ "11000000"+ "000"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000", 8, 23));
		
//		assertEquals(
//				"00"+ "11111111"+ "0000"+ "0000"+ "0000"+ "0000"+ "0000"+ "000",
//				alu.floatMultiplication(
//						"0"+ "11111111"+ "100"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000",
//						"0"+ "11000000"+ "000"	+ "0000"+ "0000"+ "0000"+ "0000"+ "0000", 8, 23));
	}
	@Test
	public void test20() {
		assertEquals(
				"0"+alu.floatRepresentation("0.875",8, 23),
				alu.floatDivision(
						alu.floatRepresentation("0.4375",8, 23), 
						alu.floatRepresentation("0.5", 8, 23), 8, 23));
		assertEquals(
				"0"+alu.floatRepresentation("-0.875", 8, 23),
				alu.floatDivision(
						alu.floatRepresentation("-0.4375",8, 23), 
						alu.floatRepresentation("0.5", 8, 23),8, 23));
		assertEquals(
				"0"+alu.floatRepresentation("0.875", 8, 23),
				alu.floatDivision(
						alu.floatRepresentation("-0.4375", 8, 23), 
						alu.floatRepresentation("-0.5", 8, 23), 8, 23));
		assertEquals(
				"0"+alu.floatRepresentation("-0.875", 8, 23),
				alu.floatDivision(
						alu.floatRepresentation("0.4375", 8, 23), 
						alu.floatRepresentation("-0.5",8, 23), 8, 23));
		assertEquals(
				"0"+alu.floatRepresentation("0", 8, 23),
				alu.floatDivision(
						alu.floatRepresentation("0", 8, 23), 
						alu.floatRepresentation("-0.5", 8, 23), 8, 23));
//		assertEquals(
//				"00"+ "11111111"
//						+ "00000000000000000000000",
//				alu.floatDivision(
//						alu.floatRepresentation("0.75", 8, 23), 
//						alu.floatRepresentation("0", 8, 23),8, 23));
//		assertEquals(
//				"01"+ "11111111"
//						+ "00000000000000000000000",
//				alu.floatDivision(
//						alu.floatRepresentation("-0.75", 8, 23), 
//						alu.floatRepresentation("0", 8, 23), 8, 23));
		assertEquals(
				"01"+ "01111000"+ "011"	+ "1100"+ "0101"+ "0010"+ "0110"+ "0100",
				alu.floatDivision(
						alu.floatRepresentation("0.75", 8, 23), 
						alu.floatRepresentation("-65.25", 8, 23),8, 23));
		assertEquals(
				"0"+alu.floatRepresentation("-0.01149425283074379", 8, 23),
				alu.floatDivision(						
						alu.floatRepresentation("0.75", 8, 23), 
						alu.floatRepresentation("-65.25", 8, 23), 8, 23));
		assertEquals(
				"00"
				+ "00000000"
				+ "11100000000000000000000",
				alu.floatDivision(
						"0"	+ "00000001"
								+ "11000000000000000000000",
						"0"
						+ "10000000"
						+ "00000000000000000000000", 
						8, 23));
		assertEquals(
				"00"
				+ "00000000"
				+ "01001001001001001001001",
				alu.floatDivision(
						"0"	+ "00000001"
								+ "00000000000000000000000",
						"0"
						+ "10000000"
						+ "11000000000000000000000", 
						8, 23));
		assertEquals(
				"00"
				+ "11111111"
				+ "00000000000000000000000",
				alu.floatDivision(
						"0"	+ "10000001"
								+ "11100000000000000000000",
						"0"
						+ "00000001"
						+ "11000000000000000000000", 
						8, 23));
	}
//	@Test
//	public void test1() {
//		assertEquals("11",alu.calculation("5+6="));
//		assertEquals("-1",alu.calculation("5+(-6)="));
//		assertEquals("1",alu.calculation("-5+6="));
//		assertEquals("-11",alu.calculation("-5+(-6)="));
//		assertEquals("1",alu.calculation("(-5)+6="));
//		assertEquals("-11",alu.calculation("(-5)+(-6)="));
//		
//		assertEquals("-1",alu.calculation("5-6="));
//		assertEquals("11",alu.calculation("5-(-6)="));
//		assertEquals("-11",alu.calculation("-5-6="));
//		assertEquals("1",alu.calculation("-5-(-6)="));
//		assertEquals("-11",alu.calculation("(-5)-6="));
//		assertEquals("1",alu.calculation("(-5)-(-6)="));
//		
//		assertEquals("30",alu.calculation("5*6="));
//		assertEquals("-30",alu.calculation("5*(-6)="));
//		assertEquals("-30",alu.calculation("-5*6="));
//		assertEquals("30",alu.calculation("-5*(-6)="));
//		assertEquals("-30",alu.calculation("(-5)*6="));
//		assertEquals("30",alu.calculation("(-5)*(-6)="));
//		
//		assertEquals("2",alu.calculation("7/3="));
//		assertEquals("-2",alu.calculation("7/(-3)="));
//		assertEquals("-2",alu.calculation("-7/3="));
//		assertEquals("2",alu.calculation("-7/(-3)="));
//		assertEquals("-2",alu.calculation("(-7)/3="));
//		assertEquals("2",alu.calculation("(-7)/(-3)="));
//		assertEquals("11.5",alu.calculation("5.5+6="));
//		assertEquals("-1.5",alu.calculation("5+(-6.5)="));
//		assertEquals("0.5",alu.calculation("-5.5+6="));
//		assertEquals("-11.5",alu.calculation("-5.5+(-6)="));
//		assertEquals("1.5",alu.calculation("(-5)+6.5="));
//		assertEquals("-11.5",alu.calculation("(-5.5)+(-6)="));
//		
//		assertEquals("-1.0",alu.calculation("5.5-6.5="));
//		assertEquals("12.0",alu.calculation("5.5-(-6.5)="));
//		assertEquals("-12.0",alu.calculation("-5.5-6.5="));
//		assertEquals("1.0",alu.calculation("-5.5-(-6.5)="));
//		assertEquals("-12.0",alu.calculation("(-5.5)-6.5="));
//		assertEquals("1.0",alu.calculation("(-5.5)-(-6.5)="));
//		
//		assertEquals("35.75",alu.calculation("5.5*6.5="));
//		assertEquals("-32.5",alu.calculation("5*(-6.5)="));
//		assertEquals("-33.0",alu.calculation("-5.5*6="));
//		assertEquals("32.5",alu.calculation("-5*(-6.5)="));
//		assertEquals("-35.75",alu.calculation("(-5.5)*6.5="));
//		assertEquals("33.0",alu.calculation("(-5.5)*(-6)="));
//		
//		assertEquals("2.3333332538604736",alu.calculation("7.0/3="));
//		assertEquals("-2.5",alu.calculation("7.5/(-3)="));
//		assertEquals("-0.21875",alu.calculation("-7/32.0="));
//		assertEquals("2.5999999046325684",alu.calculation("-7.8/(-3)="));
//		assertEquals("-2.4666666984558105",alu.calculation("(-7.4)/3="));
//		assertEquals("2.3333332538604736",alu.calculation("(-7)/(-3.0)="));
//	}
}
