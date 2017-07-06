import static org.junit.Assert.*;

import org.junit.Test;

public class Alu_test {
	ALU alu = new ALU();
	
	@Test
	public void testtttttt() {
		assertEquals("00001001",alu.integerRepresentation("9", 8));
		assertEquals("01000001001101100000",alu.floatRepresentation("11.375", 8, 11));
		assertEquals("01000001001101100000000000000000",alu.ieee754("11.375", 32));
		assertEquals("9",alu.integerTrueValue("00001001"));
		assertEquals("11.375",alu.floatTrueValue("01000001001101100000", 8, 11));
		assertEquals("11110110" ,alu.negation("00001001"));
		assertEquals("00100100" ,alu.leftShift("00001001", 2));
		assertEquals("00111101" ,alu.logRightShift("11110110", 2));
		assertEquals("11111101" ,alu.ariRightShift("11110110", 2));
		assertEquals("10" ,alu.fullAdder('1', '1', '0'));
		assertEquals("01011" ,alu.claAdder("1001", "0001", '1'));
		assertEquals("000001010" ,alu.oneAdder("00001001"));
		assertEquals("000000111" ,alu.adder("0100", "0011", '0', 8));
		assertEquals("000000001" ,alu.integerSubtraction("0100", "0011", 8));
		assertEquals("00000000100000001" ,alu.integerDivision("0100", "0011", 8));
		assertEquals("0100000111" ,alu.signedAddition("1100", "1011", 8));
		assertEquals("000111111101110000" ,alu.floatAddition("00111111010100000", "00111111001000000", 8, 8, 4));
		assertEquals("000111110010000000" ,alu.floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 4));
		assertEquals("000111110011000000" ,alu.floatMultiplication("00111110111000000", "00111111000000000", 8, 8));
		assertEquals("000111111011000000" ,alu.floatDivision("00111110111000000", "00111111000000000", 8, 8));

	}
}
