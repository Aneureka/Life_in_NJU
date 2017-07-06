import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ALUTest3 {
	String s;
	ALU alu;
	//
	@Before
	public void setUp() throws Exception {
		alu = new ALU();
	}
	

	@Test
	public void test0() {
		assertEquals("00000000000000000000000000000000", 
				alu.floatRepresentation("0.0", 8,23));
	}
//	@Test
//	public void test1(){
//		assertEquals("10000000000000000000000000000000", 
//				alu.floatRepresentation("-0.0", 8,23));
//	}
	@Test
	public void test2(){
		assertEquals("00001011000110111100010011010111", 
				alu.floatRepresentation("0.00000000000000000000000000000003", 8,23));
	}
	@Test
	public void test3(){
		assertEquals("00000000000000000000000000000000", 
				alu.floatRepresentation("0.00000000000000000000000000000000000000000000000003", 8,23));
	}
	@Test
	public void test4(){
		assertEquals("00000000000000000000000000000010", 
				alu.floatRepresentation("0.000000000000000000000000000000000000000000003", 8,23));
	}
//	@Test
//	public void test5(){
//		//TODO Unknown who is right
//		assertEquals("01000000010111110100110001101001", 
//				alu.floatRepresentation("3.489038584", 8,23));
//	}
//	@Test
//	public void test6(){
//		assertEquals("11000110000111000100000111010101", 
//				alu.floatRepresentation("-10000.458", 8,23));
//	}
	@Test
	public void test7(){
		assertEquals("0000000000000000000000000000000000000000000000000000000000000000",
			alu.floatRepresentation("0.0", 11,52)	);
	}
	@Test
	public void test8(){
		assertEquals("0011111111101000010011001000100011001111000101011000001110100111", 
			alu.floatRepresentation("0.75934257932759837523",11,52)	);
	}
	@Test
	public void test9(){
		assertEquals("1100101111011011", alu.floatRepresentation("-3.482", 3, 12));
	}

	public static class ALUTest13 {
        ALU alu;
        @Before
        public void setUp() throws Exception {
            alu=new ALU();
        }

    //	@Test
    //	public void test0() {
    //		assertEquals("00", alu.integerAddition("0", "0", 1));
    //	}
    //	@Test
    //	public void test1(){
    //		assertEquals("10", alu.integerAddition("1", "1", 1));
    //	}
    //	@Test
    //	public void test2(){
    //		//TODO ����ϸ���pdfʵ�֣�����һ���������ѵ�bug
    //		assertEquals("10", alu.integerAddition("1", "1",  1));
    //	}
        @Test
        public void test3(){
            assertEquals("01001", alu.integerAddition("1", "1010", 4));
        }
        @Test
        public void test4(){
            assertEquals("01000", alu.integerAddition("1", "1001", 4));
        }
        @Test
        public void test5(){
            assertEquals("000000", alu.integerAddition("110", "00010", 5));
        }
        @Test
        public void test6(){
            assertEquals("1100000", alu.integerAddition("011111", "01",  6));
        }
        @Test
        public void test7(){
            assertEquals("010000000000", alu.integerAddition("10000000001", "1", 11));
        }
    }
}
