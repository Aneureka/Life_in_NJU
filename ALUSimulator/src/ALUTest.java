import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/*
 *@(#)ALUTest.java 2016��5��25��
 *
 *Copyright 2016 Zyz,All rights reserved.
 */

/**
 *
 * @author ����
 */
public class ALUTest {
	private ALU alu;

	@Before
	public void setUp() throws Exception {
		alu = new ALU();
	}

	@Test
	public void testIntegerRepresentation() {
		assertEquals("000100", this.alu.integerRepresentation("4", 6));
		assertEquals("1100", this.alu.integerRepresentation("-4", 4));
		assertEquals("1010", this.alu.integerRepresentation("-6", 4));
		assertEquals("1111110000", this.alu.integerRepresentation("-16", 10));
		assertEquals("00001001", this.alu.integerRepresentation("9", 8));
	}

	@Test
	public void testFloatRepresentation() {
	//	assertEquals("000001100", this.alu.floatRepresentation("0.005859375", 4, 4));
		assertEquals("0011111010111100010000", this.alu.floatRepresentation("0.36768", 8, 13));
		assertEquals("01000010111101100111100010001010", this.alu.floatRepresentation("123.235435", 8, 23));
		assertEquals("11000001001101100000", this.alu.floatRepresentation("-11.375", 8, 11));
		assertEquals("01000001001101100000", this.alu.floatRepresentation("11.375", 8, 11));
		// assertEquals("001110110", this.alu.floatRepresentation("1.4", 4, 4));
		// assertEquals("010100010", this.alu.floatRepresentation("9.3", 4, 4));
	}

	@Test
	public void testIeee754() {
		assertEquals("01000001001101100000000000000000", this.alu.ieee754("11.375", 32));
		assertEquals("01000001001101100000000000000000", this.alu.ieee754("11.375", 32));
	}

	@Test
	public void testIntegerTrueValue() {
		assertEquals("31", this.alu.integerTrueValue("011111"));
		assertEquals("-1", this.alu.integerTrueValue("111111"));
		assertEquals("240", this.alu.integerTrueValue("000011110000"));
		assertEquals("9", this.alu.integerTrueValue("00001001"));
	}

	@Test
	public void testFloatTrueValue() {
		assertEquals("0.005859375", this.alu.floatTrueValue("000001100", 4, 4));
		assertEquals("11.375", this.alu.floatTrueValue("01000001001101100000000000000000", 8, 23));
		assertEquals("+Inf", this.alu.floatTrueValue("01111111100000000000000000000000", 8, 23));
		assertEquals("-Inf", this.alu.floatTrueValue("11111111100000000000000000000000", 8, 23));
		assertEquals("NaN", this.alu.floatTrueValue("11111111100000000000000100000000", 8, 23));
		assertEquals("11.375", this.alu.floatTrueValue("01000001001101100000", 8, 11));
	}

	@Test
	public void testNegation() {
		assertEquals("1", this.alu.negation("0"));
		assertEquals("00", this.alu.negation("11"));
		assertEquals("0101010101", this.alu.negation("1010101010"));
		assertEquals("11110110", this.alu.negation("00001001"));
	}

	@Test
	public void testLeftShift() {
		assertEquals("00100100", this.alu.leftShift("00001001", 2));
		assertEquals("000", this.alu.leftShift("000", 2));
		assertEquals("100", this.alu.leftShift("001", 2));
		assertEquals("00100100", this.alu.leftShift("00001001", 2));
	}

	@Test
	public void testLogRightShift() {
		assertEquals("000", this.alu.logRightShift("001", 2));
		assertEquals("00000010", this.alu.logRightShift("00001001", 2));
		assertEquals("00111101", this.alu.logRightShift("11110110", 2));
		assertEquals("00111101", this.alu.logRightShift("11110110", 2));
	}

	@Test
	public void testAriRightShift() {
		assertEquals("00000010", this.alu.ariRightShift("00001001", 2));
		assertEquals("11111101", this.alu.ariRightShift("11110110", 2));
		assertEquals("11111101", this.alu.ariRightShift("11110110", 2));
	}

	@Test
	public void testFullAdder() {
		assertEquals("10", this.alu.fullAdder('1', '1', '0'));
		assertEquals("01", this.alu.fullAdder('1', '0', '0'));
		assertEquals("00", this.alu.fullAdder('0', '0', '0'));
		assertEquals("11", this.alu.fullAdder('1', '1', '1'));
		assertEquals("10", this.alu.fullAdder('1', '1', '0'));

	}

	@Test
	public void testClaAdder() {
		assertEquals("01011", this.alu.claAdder("1001", "0001", '1'));
		assertEquals("10000", this.alu.claAdder("1111", "0001", '0'));
		assertEquals("01000", this.alu.claAdder("0111", "0001", '0'));
		assertEquals("01011", this.alu.claAdder("1001", "0001", '1'));
	}

	@Test
	public void testOneAdder() {
		assertEquals("000001010", this.alu.oneAdder("00001001"));
		assertEquals("010001010", this.alu.oneAdder("10001001"));
		assertEquals("11000", this.alu.oneAdder("0111"));
		assertEquals("00000", this.alu.oneAdder("1111"));
		assertEquals("000001010", this.alu.oneAdder("00001001"));
	}

	@Test
	public void testAdder() {
		assertEquals("000000111", this.alu.adder("0100", "0011", '0', 8));
		assertEquals("000001000", this.alu.adder("0100", "0011", '1', 8));
		assertEquals("11000", this.alu.adder("0100", "0011", '1', 4));
		assertEquals("00111", this.alu.adder("0100", "0011", '0', 4));
		assertEquals("01111", this.alu.adder("1100", "0011", '0', 4));
		assertEquals("000000111", this.alu.adder("0100", "0011", '0', 8));
	}

	@Test
	public void testIntegerAddition() {
		assertEquals("000000111", this.alu.integerAddition("0100", "0011", 8));
		assertEquals("00111", this.alu.integerAddition("0100", "0011", 4));
		assertEquals("11010", this.alu.integerAddition("0111", "0011", 4));
		assertEquals("000000111", this.alu.integerAddition("0100", "0011", 8));
	}

	@Test
	public void testIntegerSubtraction() {
		assertEquals("000000001", this.alu.integerSubtraction("0100", "0011", 8));
		assertEquals("00001", this.alu.integerSubtraction("0100", "0011", 4));
		assertEquals("11000", this.alu.integerSubtraction("0100", "1100", 4));
		assertEquals("000001000", this.alu.integerSubtraction("0100", "1100", 8));
		assertEquals("111111000", this.alu.integerSubtraction("01111100", "10000100", 8));
		assertEquals("000000001", this.alu.integerSubtraction("0100", "0011", 8));
	}

	@Test
	public void testIntegerMultiplication() {
		assertEquals("000001100", this.alu.integerMultiplication("0100", "0011", 8));
		assertEquals("10001", this.alu.integerMultiplication("0111", "0111", 4));
		assertEquals("011110100", this.alu.integerMultiplication("1100", "0011", 8));
		assertEquals("000001100", this.alu.integerMultiplication("0100", "0011", 8));
	}

	@Test
	public void testIntegerDivision() {
		assertEquals("011101111", this.alu.integerDivision("1001", "0011", 4));
		assertEquals("011011111", this.alu.integerDivision("1001", "0010", 4));
		// assertEquals("011100000", this.alu.integerDivision("1010", "0011",
		// 4));
		assertEquals("000100000", this.alu.integerDivision("0110", "0011", 4));
	//	assertEquals("101111111", this.alu.integerDivision("1000", "1111", 4));
		assertEquals("00000000100000001", this.alu.integerDivision("0100", "0011", 8));
	}

	@Test
	public void testSignedAddition() {
		assertEquals("0100000111", this.alu.signedAddition("1100", "1011", 8));
		assertEquals("0000010010", this.alu.signedAddition("01011", "0111", 8));
		assertEquals("0000000111", this.alu.signedAddition("0111", "0000", 8));
		assertEquals("010111", this.alu.signedAddition("1100", "1011", 4));
		assertEquals("000000", this.alu.signedAddition("01000", "11000", 4));
		assertEquals("010001", this.alu.signedAddition("01000", "11001", 4));
		assertEquals("000001", this.alu.signedAddition("0001", "1000", 4));
		assertEquals("0100000111", this.alu.signedAddition("1100", "1011", 8));
	}

	@Test
	public void testFloatAddition() {
		assertEquals("000111111101110000", this.alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 8));
		assertEquals("0001110010", this.alu.floatAddition("001101010", "001010100", 4, 4, 8));
		assertEquals("0000110000", this.alu.floatAddition("001101010", "101101000", 4, 4, 4));
		assertEquals("0000100000", this.alu.floatAddition("001101001", "101101000", 4, 4, 4));
		assertEquals("0001011010", this.alu.floatAddition("001011001", "000010000", 4, 4, 4));
		assertEquals("0001011000", this.alu.floatAddition("001011001", "100010000", 4, 4, 4));
		assertEquals("0000100000", this.alu.floatAddition("001101001", "101101000", 4, 4, 8));
	//	assertEquals("1000000000", this.alu.floatAddition("001101010", "101101010", 4, 4, 4));
		assertEquals("101111111100000000", this.alu.floatAddition("01111111000100000", "01111111001000000", 8, 8, 8));
		assertEquals("000111111101110000", this.alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 4));
	}

	@Test
	public void testFloatSubtraction() {
		assertEquals("101111111100000000",
				this.alu.floatSubtraction("01111111000100000", "11111111001000000", 8, 8, 8));
		assertEquals("000111110010000000",
				this.alu.floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 4));
	}

	@Test
	public void testFloatMultiplication() {
		assertEquals("000111110011000000",
				this.alu.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
		assertEquals("0011101111", this.alu.floatMultiplication("011101111", "001110000", 4, 4));
	//	assertEquals("0100000000", this.alu.floatMultiplication("011101111", "100000000", 4, 4));
	//	assertEquals("0000001111", this.alu.floatMultiplication("011001111", "000000001", 4, 4));	
	//	assertEquals("1000000000", this.alu.floatMultiplication("001111111", "000000001", 4, 4));
		assertEquals("001000001010100000101000111101010",
				this.alu.floatMultiplication(alu.ieee754("1.4", 32), alu.ieee754("9.3", 32), 8, 23));
		assertEquals("000111110011000000",
				this.alu.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
	}

	@Test
	public void testFloatDivision() {
		assertEquals("000111111111000000", this.alu.floatDivision("00111111011000000", "00111111000000000", 8, 8));
		assertEquals("1111110000", this.alu.floatDivision("011101100", "101100000", 4, 4));
		//assertEquals("0001010101", this.alu.floatDivision("001110000", "010001000", 4, 4));
		assertEquals("0001110000", this.alu.floatDivision("011101111", "011101111", 4, 4));
		assertEquals("000111111011000000", this.alu.floatDivision("00111110111000000", "00111111000000000", 8, 8));
	}

}
