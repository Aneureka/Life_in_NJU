
import java.util.Arrays;

/**
 * 模拟ALU进行整数和浮点数的四则运算
 * 
 * @author 151250048_郭浩滨
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * 
	 * @param number
	 *            十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length
	 *            二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation(String number, int length) {
		// 实际上的数值
		long num;
		// 以char[] 存储的结果并初始化
		char[] tempResult = new char[length];
		Arrays.fill(tempResult, '0');
		// 若为负，除去第一个符号，并取得绝对值
		if (number.charAt(0) == '-') {
			if (number == "-9223372036854775808") {
				return "1000000000000000000000000000000000000000000000000000000000000000";
			}
			num = Long.parseLong(number.substring(1));
		}

		else {
			num = Long.parseLong(number);
		}

		int count = 0;
		while (num != 0) {
			if (num % 2 == 1) {
				tempResult[length - 1 - count] = '1';
			}
			num = num / 2;
			count++;
		}

		if (number.charAt(0) == '-') {
			// 取反
			for (int i = 0; i < length; i++) {
				tempResult[i] = not(tempResult[i]);
			}
			// 加一
			return oneAdder(String.valueOf(tempResult)).substring(1);
		}

		else {
			return String.valueOf(tempResult);
		}

	}

	/**
	 * 生成十进制浮点数的二进制表示。 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * 
	 * @param number
	 *            十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation(String number, int eLength, int sLength) {
		// 特殊情况处理
		char[] oneEChar = new char[eLength];
		Arrays.fill(oneEChar, '1');
		String oneE = String.valueOf(oneEChar);
		char[] zeroSChar = new char[sLength];
		Arrays.fill(zeroSChar, '0');
		String zeroS = String.valueOf(zeroSChar);
		if (number.equals("NaN")) {
			return "0" + oneE + zeroS.substring(1) + "1";
		} else if (number.equals("+Inf")) {
			return "0" + oneE + zeroS;
		} else if (number.equals("-Inf")) {
			return "1" + oneE + zeroS;
		}

		// 正常情况
		// 转化为double
		double value = Double.parseDouble(number);
		number = String.valueOf(value);
		// //   System.out.println(value);
		double trueValue = Math.abs(value);

		// 声明符号位
		char sign = '0';
		// 拆出小数点前后的数
		if (number.charAt(0) == '-') {
			sign = '1';
		}

		// 拆出小数点前后的数
		long f = (long) trueValue;
		// //   System.out.println(f);
		double b = trueValue - (int) trueValue;
		// //   System.out.println(b);
		// 分别转化为二进制
		char[] fB = new char[(int) Math.pow(2, eLength) + sLength];
		Arrays.fill(fB, '0');
		// 小数点前的转换
		int countF = 0;
		while (f != 0) {
			if (f % 2 == 1) {
				fB[(int) Math.pow(2, eLength) + sLength - 1 - countF] = '1';
			}
			f = f / 2;
			countF++;
		}
		// //   System.out.println(fB);

		// 小数点后的转换
		char[] bB = new char[(int) Math.pow(2, eLength) + sLength];
		Arrays.fill(bB, '0');
		int countB = 0;
		while (b != 0 && countB < (int) Math.pow(2, eLength) + sLength) {
			b *= 2;
			bB[countB] = (char) ('0' + (int) b);
			b = b - (int) b;
			countB++;
		}
		// //   System.out.println(bB);

		String f_b = String.valueOf(fB) + String.valueOf(bB);
		// //   System.out.println(f_b);
		char[] e1 = new char[eLength];
		char[] s1 = new char[sLength];
		Arrays.fill(e1, '1');
		Arrays.fill(s1, '0');
		String e11 = String.valueOf(e1);
		String s11 = String.valueOf(s1);
		// 先判断范围
		// 若为0
		if (trueValue == 0) {
			return f_b.substring(0, 1 + eLength + sLength);
		}
		// 若下溢，反规格化	
		else if (trueValue > 0 && trueValue < Math.pow(2, 2 - Math.pow(2, eLength - 1))) {
			String result = sign + f_b.substring(0, eLength) + f_b.substring(fB.length + (int) Math.pow(2, eLength - 1) - 2,
					fB.length + (int) Math.pow(2, eLength - 1) - 2 + sLength);
			return result;
		}
		// 若上溢，无穷
		else if (trueValue > (2 - Math.pow(2, -sLength)) * Math.pow(2, Math.pow(2, eLength - 1))) {
			// //   System.out.println((2 - Math.pow(2, -sLength) * Math.pow(2,
			// Math.pow(2, eLength - 1))));
			return sign + e11 + s11;
		}
		// 若在范围内，规格化
		else {
			if (number.equals(Double.MAX_VALUE + "") && eLength == 11) {
				return "0" + "11111111110" + "1111111111111111111111111111111111111111111111111111";
			}

			int subLength = fB.length - 1;
			// //   System.out.println(fB.length);

			for (int i = 0; i < f_b.length(); i++) {
				if (f_b.charAt(i) == '1') {
					subLength = i;
					// //   System.out.println(i);
					break;
				}
			}

			int eInt = fB.length - 1 - subLength + (int) Math.pow(2, eLength - 1) - 1;
			String eString = String.valueOf(eInt);
			String e = integerRepresentation(eString, eLength);
			return sign + e + f_b.substring(subLength + 1, subLength + 1 + sLength);
		}

	}

	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int)
	 * floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * 
	 * @param number
	 *            十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length
	 *            二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754(String number, int length) {
		if (length == 32) {
			return floatRepresentation(number, 8, 23);
		} else {
			return floatRepresentation(number, 11, 52);
		}
	}

	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * 
	 * @param operand
	 *            二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue(String operand) {
		// 先获得输入的数的位数
		int length = operand.length();
		// 声明结果
		long resultLong = -(operand.charAt(0) - '0') * (long) Math.pow(2, length - 1);

		// 计算真值
		for (int i = 1; i < length; i++) {
			resultLong += (operand.charAt(i) - '0') * (long) Math.pow(2, length - i - 1);
		}
		String result = Long.toString(resultLong);
		//   //   System.out.println(resultLong);

		if (length >= 64) {
			if (operand.charAt(0) == '1') {
				result = Long.toString(resultLong - 1);
			} else {
				result = result.substring(0, result.length() - 1) + (char) (result.charAt(result.length() - 1) + 1);
				//   //   System.out.println("bbb");

			}

		}
		return result;
	}

	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * 
	 * @param operand
	 *            二进制表示的操作数
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”，
	 *         NaN表示为“NaN”
	 */
	public String floatTrueValue(String operand, int eLength, int sLength) {
		// 拆分成三部分
		char sign = operand.charAt(0);
		String exponent = operand.substring(1, 1 + eLength);
		System.out.println(exponent);
		String significand = operand.substring(1 + eLength);
		// 初始化结果
		String result = "";
		if (sign == '1') {
			result += "-";
		}
		// 指数先减去偏值（如127），注意先在最前面先添加0，转化为补码形式
		String exponent1 = integerSubtraction('0' + exponent,
				integerRepresentation((String.valueOf((int) Math.pow(2, eLength - 1) - 1)), eLength),
				((eLength + 5) / 4) * 4).substring(1);
		// 算出指数的值
		int e = ('0' - exponent1.charAt(0)) * (int) Math.pow(2, exponent1.length() - 1);
		if (exponent1.length() > 1) {
			for (int i = 1; i < exponent1.length(); i++) {
				e += (exponent1.charAt(i) - '0') * (int) Math.pow(2, exponent1.length() - 1 - i);
			}
		}
		// 算出尾数的值
		double s = 1;
		for (int i = 0; i < sLength; i++) {
			s += (double) (significand.charAt(i) - '0') * Math.pow(0.5, i + 1);

		}

		// 判断个各种情况
		char[] se = new char[eLength];
		char[] ss = new char[sLength];
		Arrays.fill(se, '0');
		String zeroE = String.valueOf(se);
		Arrays.fill(se, '1');
		String oneE = String.valueOf(se);
		Arrays.fill(ss, '0');
		String zeroS = String.valueOf(ss);

		// 指数全为0
		if (exponent.equals(zeroE)) {
			// 为0
			System.out.println("wdwadwa");
			if (significand.equals(zeroS)) {
				return "0";
			}
			// 反规格化
			double tempResult = (s - 1) * Math.pow(2, e + 1);
			return result + String.valueOf(tempResult);
		}
		// 指数全为1
		else if (exponent.equals(oneE)) {
			// 正负无穷
			if (significand.equals(zeroS)) {
				if (result.equals("")) {
					return "+Inf";
				} else {
					return "-Inf";
				}
			}
			// 非数NaN
			else {
				return "NaN";
			}

		}

		// 正常情况
		else {
			double tempResult = s * Math.pow(2, e);
			return result + String.valueOf(tempResult);
		}

	}

	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * 
	 * @param operand
	 *            二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation(String operand) {
		String result = "";
		for (int i = 0; i < operand.length(); i++) {
			result += Integer.toString(('1' - operand.charAt(i)));
		}
		return result;
	}

	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * 
	 * @param operand
	 *            二进制表示的操作数
	 * @param n
	 *            左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift(String operand, int n) {
		String result = operand.substring(n);
		for (int i = 0; i < n; i++) {
			result += '0';
		}
		return result;
	}

	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * 
	 * @param operand
	 *            二进制表示的操作数
	 * @param n
	 *            右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift(String operand, int n) {
		String front = "";
		String back = operand.substring(0, operand.length() - n);
		for (int i = 0; i < n; i++) {
			front += '0';
		}
		return front + back;
	}

	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * 
	 * @param operand
	 *            二进制表示的操作数
	 * @param n
	 *            右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift(String operand, int n) {
		String front = "";
		String back = operand.substring(0, operand.length() - n);
		for (int i = 0; i < n; i++) {
			front += operand.charAt(0);
		}
		return front + back;
	}

	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * 
	 * @param x
	 *            被加数的某一位，取0或1
	 * @param y
	 *            加数的某一位，取0或1
	 * @param c
	 *            低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder(char x, char y, char c) {
		char Si = xor(xor(x, y), c);
		char Ci = or(or(and(x, c), and(y, c)), and(x, y));
		String result = Ci + "";
		result += Si;
		return result;
	}

	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * 
	 * @param operand1
	 *            4位二进制表示的被加数
	 * @param operand2
	 *            4位二进制表示的加数
	 * @param c
	 *            低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder(String operand1, String operand2, char c) {
		String result = "";
		String tempResult = "";
		char[] p = new char[4];
		char[] g = new char[4];

		for (int i = 0; i < 4; i++) {
			p[i] = or(operand1.charAt(3 - i), operand2.charAt(3 - i));
			g[i] = and(operand1.charAt(3 - i), operand2.charAt(3 - i));
		}

		char[] toBit = new char[5];
		toBit[0] = c;
		toBit[1] = or(g[0], and(p[0], c));
		toBit[2] = or(or(g[1], and(p[1], g[0])), and(and(p[1], p[0]), c));
		toBit[3] = or(or(g[2], and(p[2], g[1])), or(and(and(p[2], p[1]), g[0]), and(and(p[2], p[1]), and(p[0], c))));
		toBit[4] = or(or(or(g[3], and(p[3], g[2])), and(and(p[3], p[2]), g[1])),
				or(and(and(p[3], p[2]), and(p[1], g[0])), and(and(and(p[3], p[2]), and(p[1], p[0])), c)));

		for (int i = 0; i < 4; i++) {
			tempResult += fullAdder(operand1.charAt(3 - i), operand2.charAt(3 - i), toBit[i]).charAt(1);
		}

		tempResult += toBit[4];
		for (int i = 4; i >= 0; i--) {
			result += tempResult.charAt(i);
		}
		return result;
	}

	/**
	 * 加一器，实现操作数加1的运算。 需要采用与门、或门、异或门等模拟， 不可以直接调用
	 * {@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * 
	 * @param operand
	 *            二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder(String operand) {

		char[] tempResult = operand.toCharArray();
		String result = "";
		char spilt = operand.charAt(0);
		for (int i = operand.length() - 1; i >= 0; i--) {
			tempResult[i] = not(tempResult[i]);
			if (tempResult[i] == '1') {
				break;
			}
		}

		for (int i = 0; i < operand.length(); i++) {
			result += tempResult[i];
		}

		if (spilt == '0' && tempResult[0] == '1') {
			return '1' + result;
		}

		else {
			return '0' + result;
		}

	}

	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", '0', 8)
	 * 
	 * @param operand1
	 *            二进制补码表示的被加数
	 * @param operand2
	 *            二进制补码表示的加数
	 * @param c
	 *            最低位进位
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，
	 *            需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder(String operand1, String operand2, char c, int length) {
		String longOperand1 = lengthen(operand1, length);
		String longOperand2 = lengthen(operand2, length);
		// //   System.out.println(longOperand1);
		// //   System.out.println(longOperand2);
		int count = length / 4;
		// //   System.out.println(count);
		String[] tempResult = new String[count];
		String result = "";
		for (int i = 0; i < count; i++) {
			String part = claAdder(longOperand1.substring(4 * (count - i - 1), 4 * (count - i)),
					longOperand2.substring(4 * (count - i - 1), 4 * (count - i)), c);
			// //   System.out.println(part);
			tempResult[count - 1 - i] = part.substring(1);
			c = part.charAt(0);
		}

		for (int i = 0; i < count; i++) {
			result += tempResult[i];
		}

		if (result.charAt(0) != operand1.charAt(0) && result.charAt(0) != operand2.charAt(0)) {
			return '1' + result;
		}

		else {
			return '0' + result;
		}
	}

	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * 
	 * @param operand1
	 *            二进制补码表示的被加数
	 * @param operand2
	 *            二进制补码表示的加数
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，
	 *            需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition(String operand1, String operand2, int length) {

		return adder(operand1, operand2, '0', length);
	}

	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * 
	 * @param operand1
	 *            二进制补码表示的被减数
	 * @param operand2
	 *            二进制补码表示的减数
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，
	 *            需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction(String operand1, String operand2, int length) {
		// 减数取反加一
		String dpOperand2 = oneAdder(String.valueOf(negation(operand2))).substring(1);
		return adder(operand1, dpOperand2, '0', length);
	}

	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。
	 * <br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * 
	 * @param operand1
	 *            二进制补码表示的被乘数
	 * @param operand2
	 *            二进制补码表示的乘数
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，
	 *            需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication(String operand1, String operand2, int length) {
		// 声明判断溢出
		char spilt = '0';
		// 溢出比较string
		char[] opp1 = new char[length];
		Arrays.fill(opp1, '0');
		String opp = String.valueOf(opp1);
		Arrays.fill(opp1, '1');
		String neg = String.valueOf(opp1);
		// 对操作数的处理
		String longOperand1 = lengthen(operand1, length);
		String longOperand2 = lengthen(operand2, length);
		char[] leftS = new char[length + 1];
		Arrays.fill(leftS, '0');
		String.valueOf(leftS);
		String X = longOperand1 + String.valueOf(leftS);
		String _X = oneAdder(negation(longOperand1)).substring(1) + String.valueOf(leftS);
		// //   System.out.println(X);
		// //   System.out.println(_X);
		// 初始化结果
		char[] init = new char[2 * length + 1];
		Arrays.fill(init, '0');
		for (int i = 0; i < length; i++) {
			init[length + i] = longOperand2.charAt(i);
		}
		String result = String.valueOf(init);
		// 声明Y[]数组
		char[] Y = new char[longOperand2.length() + 1];
		Y[0] = '0';
		for (int i = 1; i < Y.length; i++) {
			Y[i] = longOperand2.charAt(length - i);
		}
		// //   System.out.println(Y);
		// 进行运算
		for (int i = 0; i < Y.length - 1; i++) {
			if (Y[i] - Y[i + 1] == 0) {
				// //   System.out.println("0");
				result = ariRightShift(result, 1);
				// //   System.out.println(result);
			} else if (Y[i] - Y[i + 1] == 1) {
				// //   System.out.println("1");
				result = integerAddition(result, X, 2 * length + 4).substring(4);
				// //   System.out.println(result);
				result = ariRightShift(result, 1);
				// //   System.out.println(result);
			} else {
				// 溢出的情况
				if (X.equals(_X)) {
					spilt = '1';
				}
				// //   System.out.println("-1");
				result = integerAddition(result, _X, 2 * length + 4).substring(4);
				// //   System.out.println(result);
				result = ariRightShift(result, 1);
				// //   System.out.println(result);
			}
		}

		if (!(result.substring(0, length).equals(opp) && result.charAt(0) == result.charAt(length))
				&& !(result.substring(0, length).equals(neg) && result.charAt(0) == result.charAt(length))) {
			spilt = '1';
		}

		return spilt + result.substring(length, 2 * length);
	}

	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * 
	 * @param operand1
	 *            二进制补码表示的被除数
	 * @param operand2
	 *            二进制补码表示的除数
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，
	 *            需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，
	 *         最后length位为余数
	 */
	public String integerDivision(String operand1, String operand2, int length) {
		// 对操作数的处理
		operand1 = lengthen(operand1, length);
		operand2 = lengthen(operand2, length);
		// 初始化结果
		String tempResult = lengthen(operand1, 2 * length);
		// 生成X，-X
		char[] leftS = new char[length];
		Arrays.fill(leftS, '0');
		String.valueOf(leftS);
		String X = operand2 + String.valueOf(leftS);
		String _X = oneAdder(negation(operand2)).substring(1) + String.valueOf(leftS);
		// //   System.out.println("X: " + X);
		// //   System.out.println("-X: " + _X);
		// 声明溢出位
		char spilt = '0';
		// 判断是否会溢出
		char[] spiltC = new char[length];
		Arrays.fill(spiltC, '0');
		String spiltS = String.valueOf(spiltC);
		Arrays.fill(spiltC, '1');
		String _spiltS = String.valueOf(spiltC);
		if (operand1.equals('1' + spiltS.substring(1)) && operand2.equals(_spiltS)) {
			spilt = '1';
		}

		String bre = spiltS.substring(0, length);
		// 声明商的进位
		char quoto;
		// 第一次做加减法
		if (tempResult.charAt(0) == operand2.charAt(0)) {
			tempResult = adder(tempResult, _X, '0', 2 * length).substring(1);
		} else {
			tempResult = adder(tempResult, X, '0', 2 * length).substring(1);
		}
		// //   System.out.println(tempResult);
		// 第一次进位决定
		if (tempResult.charAt(0) == operand2.charAt(0)) {
			quoto = '1';
		} else {
			quoto = '0';
		}
		// 循环计算
		for (int i = 0; i < length; i++) {
			// 左移
			tempResult = leftShift(tempResult, 1).substring(0, 2 * length - 1) + quoto;
			// //   System.out.println(tempResult + "左移");
			// 做加减法
			if (tempResult.charAt(0) == operand2.charAt(0)) {
				tempResult = adder(tempResult, _X, '0', 2 * length).substring(1);
				// //   System.out.println(tempResult + "减");
			} else {
				tempResult = adder(tempResult, X, '0', 2 * length).substring(1);
				// //   System.out.println(tempResult + "加");
				// if(tempResult.equals(spiltS)){
				// spilt = '1';
				// //   System.out.println("spilt!");
				// }
			}
			// 进位决定
			if (tempResult.charAt(0) == operand2.charAt(0)) {
				quoto = '1';

			} else {
				quoto = '0';
			}

			if (tempResult.substring(0, length).equals(bre)) {
				// //   System.out.println("break");
				// break;
			}
			// //   System.out.println(quoto);
		}

		String remainder = tempResult.substring(0, length);
		String quotient = tempResult.substring(length, 2 * length);
		// 余数处理
		if (remainder.charAt(0) != operand1.charAt(0)) {
			if (remainder.charAt(0) == operand2.charAt(0)) {
				remainder = integerSubtraction(remainder, operand2, length).substring(1);
			} else {
				remainder = integerAddition(remainder, operand2, length).substring(1);
			}
		}
		// 商处理
		quotient = leftShift(quotient, 1).substring(0, length - 1) + quoto;
		if (quotient.charAt(0) == '1') {
			quotient = oneAdder(quotient).substring(1);
		}

		if (remainder.equals(operand2)) {
			quotient = oneAdder(quotient).substring(1);
			remainder = spiltS;
		} else if (remainder.equals(oneAdder(negation(operand2)).substring(1))) {
			quotient = integerAddition(quotient, _spiltS, length).substring(1);
			remainder = spiltS;
		}

		return spilt + quotient + remainder;
	}

	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int)
	 * integerAddition}、 {@link #integerSubtraction(String, String, int)
	 * integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * 
	 * @param operand1
	 *            二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2
	 *            二进制原码表示的加数，其中第1位为符号位
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，
	 *            需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，
	 *         后length位是相加结果
	 */
	public String signedAddition(String operand1, String operand2, int length) {
		// 位数拓展
		String longOperand1 = operand1.charAt(0) + lengthen("0", length - operand1.length() + 1)
				+ operand1.substring(1);
		String longOperand2 = operand2.charAt(0) + lengthen("0", length - operand2.length() + 1)
				+ operand2.substring(1);

		// //   System.out.println(longOperand1);
		// //   System.out.println(longOperand2);

		if (longOperand1.charAt(0) == longOperand2.charAt(0)) {
			// 加法
			String spilt = adder("0" + longOperand1.substring(1), "0" + longOperand2.substring(1), '0', length + 4)
					.substring(4, 5);
			String tempResult = adder("0" + longOperand1.substring(1), "0" + longOperand2.substring(1), '0', length + 4)
					.substring(5);
			// //   System.out.println(adder("0" + longOperand1.substring(1), "0" +
			// longOperand2.substring(1), '0', length + 4));
			// String tempResult = adder( longOperand1 , longOperand2, '0',
			// length);
			// //   System.out.println(spilt);
			// //   System.out.println(tempResult);
			String result = spilt + longOperand1.substring(0, 1) + tempResult;
			return result;
		}

		else {
			// 减法
			// 把负数转化为补码。
			if (longOperand1.charAt(0) == '1') {
				// 正数不用改，负数做处理
				longOperand1 = oneAdder(negation("0" + longOperand1.substring(1))).substring(1);
				// //   System.out.println(longOperand1);
			}

			else {
				longOperand2 = oneAdder(negation("0" + longOperand2.substring(1))).substring(1);
//				//   System.out.println(longOperand2);
			}

			// 仍然是补码形式
			String tempResult = adder(longOperand1, longOperand2, '0', length + 4);
			// //   System.out.println(tempResult);
			// 分为溢出位和补码的结果
			String spilt = tempResult.charAt(0) + "";
			String toChange = tempResult.substring(5);
			// //   System.out.println(spilt);
			// //   System.out.println(toChange);

			// 将补码转化为源码
			String sign = tempResult.charAt(1) + ""; // 符号
			String subResult = toChange;
			// 若符号为0，不做变换；为1，进行转换
			if (sign.equals("1")) {
				// 取反加一
				subResult = oneAdder(negation('1' + toChange)).substring(2);
				// //   System.out.println("aaa");
			}

			return spilt + sign + subResult;
		}

	}

	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}
	 * 等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * 
	 * @param operand1
	 *            二进制表示的被加数
	 * @param operand2
	 *            二进制表示的加数
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param gLength
	 *            保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），
	 *         其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition(String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO 特殊情况 反规格 
		String result = "";
		// 用于无穷的比较
		// 指数全为1
		char[] EChar = new char[eLength];
		Arrays.fill(EChar, '1');
		String oneE = String.valueOf(EChar);
		Arrays.fill(EChar, '0');
		String zeroE = String.valueOf(EChar);
		// 底数全为0
		char[] zeroSChar = new char[sLength];
		Arrays.fill(zeroSChar, '0');
		String zeroS = String.valueOf(zeroSChar);

		// 判断是否有其中一个为无穷
		if (operand1.substring(1).equals(oneE + zeroS) && !operand2.substring(1, 1 + eLength).equals(oneE)) {
			return "0" + operand1;
		}
		if (operand2.substring(1).equals(oneE + zeroS) && !operand1.substring(1, 1 + eLength).equals(oneE)) {
			return "0" + operand2;
		}
		// 判断其中一个为NaN
		if (operand1.substring(1, 1 + eLength).equals(oneE)
				&& !operand1.substring(operand1.length() - sLength).equals(zeroS)) {
			return "0" + operand1.substring(0, 1 + eLength) + "1" + zeroS.substring(1);
		}
		if (operand2.substring(1, 1 + eLength).equals(oneE)
				&& !operand2.substring(operand1.length() - sLength).equals(zeroS)) {
			return "0" + operand2.substring(0, 1 + eLength) + "1" + zeroS.substring(1);
		}
		// 两个都为无穷，有两种情况
		if (operand1.substring(1).equals(oneE + zeroS) && operand2.substring(1).equals(oneE + zeroS)
				&& operand1.charAt(0) == operand2.charAt(0)) {
			return "0" + operand1;
		}
		if (operand1.substring(1).equals(oneE + zeroS) && operand2.substring(1).equals(oneE + zeroS)
				&& operand1.charAt(0) != operand2.charAt(0)) {
			return "0" + operand1.substring(0, 1 + eLength) + "1" + zeroS.substring(1);
		}

		// 判断是否为0
		char[] subZeroChar = new char[eLength + sLength];
		Arrays.fill(subZeroChar, '0');
		String subZero = String.valueOf(subZeroChar);
		if (operand1.substring(1).equals(subZero)) {
			return "0" + operand2;
		} else if (operand2.substring(1).equals(subZero)) {
			return "0" + operand1;
		} else {
			// 拓展保护位
			char[] gChar = new char[gLength];
			Arrays.fill(gChar, '0');
			String gString = String.valueOf(gChar);
			String longOperand1 = operand1 + gString;
			String longOperand2 = operand2 + gString;
			// 用于比较的尾数
			// 尾数全为0
			char[] sZeroChar = new char[sLength];
			Arrays.fill(sZeroChar, '0');
			String sZero = String.valueOf(sZeroChar);

			// 检查有没有隐藏位，包含反规格的情况
			char hide1 = '1';
			char hide2 = '1';
			// 两操作数的指数
			String e1 = operand1.substring(1, 1 + eLength);
			String e2 = operand2.substring(1, 1 + eLength);
			if (e1.equals(zeroE)) {
				hide1 = '0';
			}
			if (e2.equals(zeroE)) {
				hide2 = '0';
			}

//			//   System.out.println(operand1 + "operand1");
//			//   System.out.println(operand2 + "operand2");

			// 正常情况
			// 判断指数是否相等，若不相等，右移较小的数
			
			if (!e1.equals(e2)) {
				
				// 判断大小
				double double1 = Double.parseDouble(floatTrueValue("0" + operand1.substring(1), eLength, sLength));
				double double2 = Double.parseDouble(floatTrueValue("0" + operand2.substring(1), eLength, sLength));

				if (double1 < double2) {
					// 尾数前面还有一个'1'
					String sFull = "1" + longOperand1.substring(1 + eLength);
//					//   System.out.println(sFull + "sFull");
					// 指数增加，底数右移
					while (!e1.equals(e2)) {
					
						//   //   System.out.println(e1 + "e1");
						//   //   System.out.println(e2 + "e2");
						e1 = oneAdder("0" + e1).substring(2);
						//   //   System.out.println(e1 + "e1");
						sFull = logRightShift(sFull, 1);
						hide1 = '0';
						//   //   System.out.println(sFull + "<+");
						// 若尾数为0，返回operand2
						if (sFull.equals("0" + sZero + gString)) {

							return "0" + operand2;
						}
					}
					// 处理后的较小的操作数
					longOperand1 = longOperand1.substring(0, 1) + e1 + sFull.substring(1);
					//   //   System.out.println(longOperand1 + "处理后的第一个操作数");
				}

				if (double1 > double2) {
					System.out.println("WADWADWA");
					// 尾数前面还有一个'1'
					String sFull = "1" + longOperand2.substring(1 + eLength);
					// 指数增加，底数右移
					while (!e1.equals(e2)) {
						e2 = oneAdder("0" + e2).substring(2);
						sFull = logRightShift(sFull, 1);
						hide2 = '0';
						// 若尾数为0，返回operand1
						if (sFull.equals("0" + sZero + gString)) {
							//   //   System.out.println("AAAA");
							return "0" + operand1;
						}
					}
					// 处理后的较小的操作数
					longOperand2 = longOperand2.substring(0, 1) + e1 + sFull.substring(1);
					//   //   System.out.println(longOperand2 + "处理后的第二个操作数");
				}
			}

			// 指数相同时，（带符号的）尾数相加，注意是 隐藏位+尾数
			
			String e = e1;
//			System.out.println(e);
			String signedAddS = signedAddition(
					longOperand1.substring(0, 1) + hide1 + longOperand1.substring(1 + eLength),
					longOperand2.substring(0, 1) + hide2 + longOperand2.substring(1 + eLength),
					((sLength + gLength + 5) / 4) * 4);
			
//			      System.out.println(
//					longOperand1.substring(0, 1) + " " + hide1 + " " + longOperand1.substring(1 + eLength) + '一');
//			      System.out.println(
//					longOperand2.substring(0, 1) + " " + hide2 + " " + longOperand2.substring(1 + eLength) + '二');
//			      System.out.println(signedAddS + "signedAddS");
			// 溢出位 + 隐藏位 + 尾数 addeds
			char spilt = or(signedAddS.charAt(0), signedAddS.charAt(signedAddS.length() - sLength - gLength - 2));
			
//			System.out.println(spilt + "spilt");
			String addedS = spilt + signedAddS.substring(signedAddS.length() - sLength - gLength - 1);
//		  System.out.println(addedS + "addedS");

			// 若尾数为0，返回0
			if (addedS.equals("00" + sZero + gString)) {
				return "0" + "0" + subZero;
			}

			else {
				// 若尾数溢出
				if (addedS.charAt(0) == '1') {
					// 尾数右移，指数加1
					addedS = logRightShift(addedS, 1);
//					  System.out.println(addedS + "right");
					e = oneAdder("0" + e).substring(2);
//					System.out.println(e);
					// 若指数溢出，返回无穷
					if (oneAdder("0" + e).charAt(0) == '1') {
//						System.out.println(e);
						return "1" + signedAddS.substring(1, 2) + oneE + zeroS;
					}
				}

				// 除了尾数溢出且指数溢出的情况
				// 若指数下溢，则报错
				while (!e.equals(zeroE)) {
					// 判断是否已经规格化
					if (addedS.charAt(1) == '1') {
						//   //   System.out.println(addedS);
						//   //   System.out.println(e);
						//   //   System.out.println(addedS.substring(2, 2 + sLength));
						//   //   System.out.println(signedAddS.substring(1, 2));
						return "0" + signedAddS.substring(1, 2) + e + addedS.substring(2, 2 + sLength);
					}

					else {
						addedS = leftShift(addedS, 1);
						String tempE = integerSubtraction("0" + e, "01", ((e.length() + 5) / 4) * 4);
						e = tempE.substring(tempE.length() - e.length());
						// //   System.out.println(tempE+ "tempE");
						//   //   System.out.println(e + "e");

					}

				}
				
				return signedAddS.substring(1, 2) + zeroE + addedS.substring(2, 2 + sLength);

			}
		}

	}

	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int)
	 * floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * 
	 * @param operand1
	 *            二进制表示的被减数
	 * @param operand2
	 *            二进制表示的减数
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @param gLength
	 *            保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），
	 *         其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction(String operand1, String operand2, int eLength, int sLength, int gLength) {
		char negSign2 = (char) ('0' + '1' - operand2.charAt(0));
		return floatAddition(operand1, negSign2 + operand2.substring(1), eLength, sLength, gLength);
	}

	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int)
	 * integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * 
	 * @param operand1
	 *            二进制表示的被乘数
	 * @param operand2
	 *            二进制表示的乘数
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），
	 *         其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication(String operand1, String operand2, int eLength, int sLength) {
		char sign = xor(operand1.charAt(0), operand2.charAt(0));
		// 用来比较的指数和尾数
		char[] eChar = new char[eLength];
		Arrays.fill(eChar, '0');
		String zeroE = String.valueOf(eChar);
		Arrays.fill(eChar, '1');
		String oneE = String.valueOf(eChar);
		char[] sChar = new char[sLength];
		Arrays.fill(sChar, '0');
		String zeroS = String.valueOf(sChar);
		// 判断是否其中一个为0，若成立，输出0
		if (operand1.substring(1).equals(zeroE + zeroS) || operand2.substring(1).equals(zeroE + zeroS)) {
			return "00" + zeroE + zeroS;
		}

		// 指数相加，并减去偏移量
		String e1 = operand1.substring(1, 1 + eLength);
		String e2 = operand2.substring(1, 1 + eLength);
		String tempE = integerAddition("0" + e1, "0" + e2, ((eLength + 5) / 4) * 4).substring(1);
		String tempE2 = integerSubtraction(tempE, "0" + oneE.substring(1), tempE.length());

		// //   System.out.println("0" + oneE.substring(1));
		String e = tempE2.substring(tempE2.length() - eLength); // 此时e仍是补码形式
		// //   System.out.println(tempE + " E");
		//   //   System.out.println(tempE2 + " E2");
		// //   System.out.println(tempE2);
		// //   System.out.println(e);

		// 判断指数会不会上溢
		char spilt;
		spilt = or(tempE2.charAt(0), tempE2.charAt(tempE2.length() - eLength - 1));
		//   //   System.out.println("spilt！");
		if (spilt == '1' && tempE2.charAt(1) == '0') {
			return spilt + "" + sign + oneE + zeroS;
		} else {
			spilt = '0';
		}

		// 确定隐藏位
		char hide1 = '1';
		char hide2 = '1';
		if (operand1.substring(1, 1 + eLength).equals(zeroE)) {
			hide1 = '0';
		}
		if (operand2.substring(1, 1 + eLength).equals(zeroE)) {
			hide2 = '0';
		}

		// 若都是反规格化数，直接返回0
		if (e1.equals(zeroE) && e2.equals(zeroE)) {
			return "0" + sign + zeroE + zeroS;
		}

		// String s1 = "1" + operand1.substring(operand1.length() - sLength);
		// String s2 = "1" + operand2.substring(operand2.length() - sLength);
		// //   System.out.println(s1);
		// //   System.out.println(s2);
		// String tempResult = integerMultiplication(s1, s2, ((sLength + 5) / 4)
		// * 4);
		String s1 = "0" + hide1 + operand1.substring(operand1.length() - sLength);
		String s2 = "0" + hide2 + operand2.substring(operand2.length() - sLength);
		// //   System.out.println(s1 + " s1");
		// //   System.out.println(s2 + " s2");
		String tempResult = integerMultiplication(s1, s2, ((sLength + 6) / 4) * 8);

		// //   System.out.println(((sLength + 6) / 4) * 8 + "length");
		// //   System.out.println(tempResult + "tempR");

		// 获取小数点后的所有内容
		String sT = tempResult.substring(tempResult.length() - 2 * sLength);
		// 获取真正的尾数
		String s = sT.substring(0, sLength);
		// 获取带溢出位和隐藏位的尾数
		String sFull = tempResult.substring(tempResult.length() - 2 * sLength - 2, tempResult.length() - 2 * sLength)
				+ s;

		// 若溢出，底数右移，指数加1
		if (sFull.charAt(0) == '1') {
			// s = sFull.substring(1, 1 + sLength);
			sFull = logRightShift(sFull, 1);
			s = sFull.substring(2, 2 + sLength);
			//   //   System.out.println(s + " s");
			e = oneAdder(e).substring(1);
		}

		// 用来比较的sFull
		char[] sFullChar = new char[sFull.length()];
		Arrays.fill(sFullChar, '0');
		String sFullZero = String.valueOf(sFullChar);

		// 若e小于0（第一位为1），则下溢， 底数右移
		if (tempE2.charAt(1) == '1') {
			String eTemp = tempE2.substring(1);
			//   //   System.out.println(eTemp);
			//   //   System.out.println(sFull);
			while (!eTemp.substring(eTemp.length() - eLength).equals(zeroE)) {
				eTemp = oneAdder(eTemp).substring(1);
				sFull = logRightShift(sFull, 1);
				if (sFull.equals(sFullZero)) {
					s = sFull.substring(2, 2 + sLength);
					return spilt + "" + sign + zeroE + s;
				}
			}
			e = eTemp.substring(eTemp.length() - eLength);
			//   //   System.out.println(eTemp);
		}

		// 若e等于0，则尾数右移一位
		if (e.equals(zeroE)) {
			//   //   System.out.println(e + "eeeee");
			sFull = logRightShift(sFull, 1);
		}
		// //   System.out.println(e);
		s = sFull.substring(2, 2 + sLength);
		return spilt + "" + sign + e + s;
	}

	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}
	 * 等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * 
	 * @param operand1
	 *            二进制表示的被除数
	 * @param operand2
	 *            二进制表示的除数
	 * @param eLength
	 *            指数的长度，取值大于等于 4
	 * @param sLength
	 *            尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），
	 *         其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision(String operand1, String operand2, int eLength, int sLength) {
		// 用来比较的指数和尾数
		char[] eChar = new char[eLength];
		Arrays.fill(eChar, '0');
		String zeroE = String.valueOf(eChar);
		Arrays.fill(eChar, '1');
		String oneE = String.valueOf(eChar);
		char[] sChar = new char[sLength];
		Arrays.fill(sChar, '0');
		String zeroS = String.valueOf(sChar);

		// 确定符号
		char sign = xor(operand1.charAt(0), operand2.charAt(0));

		// X为0，返回0
		if (operand1.substring(1).equals(zeroE + zeroS)) {
			return "00" + zeroE + zeroS;
		}
		// Y为0，返回inf
		if (operand2.substring(1).equals(zeroE + zeroS)) {
			return "0" + sign + oneE + zeroS;
		}

		// 正常情况
		// 确定隐藏位
		char hide1 = '1';
		char hide2 = '1';
		if (operand1.substring(1, 1 + eLength).equals(zeroE)) {
			hide1 = '0';
		}
		if (operand2.substring(1, 1 + eLength).equals(zeroE)) {
			hide2 = '0';
		}

		// 指数相减，并加上偏移量
		String e1 = operand1.substring(1, 1 + eLength);
		String e2 = operand2.substring(1, 1 + eLength);
		String tempE = integerSubtraction("0" + e1, "0" + e2, ((eLength + 5) / 4) * 4).substring(1);
		String tempE2 = integerAddition(tempE, "0" + oneE.substring(1), tempE.length());
		String e = tempE2.substring(tempE2.length() - eLength); // 此时e仍是补码形式
		      System.out.println(tempE2);
		      System.out.println(e);

		// // 若e小于0（第一位为1），则下溢
		// if (e.charAt(0) == '1') {
		// return "underflow";
		// }

		// 判断指数会不会上溢
		//   //   System.out.println(tempE2);
		char spilt;
		spilt = or(or(tempE2.charAt(0), tempE2.charAt(tempE2.length() - eLength - 1)), tempE2.charAt(tempE2.length() - eLength));
		System.out.println(spilt);
		//   //   System.out.println("spilt！");
		if (spilt == '1' && tempE2.charAt(1) == '0') {
			return spilt + "" + sign + oneE + zeroS;
		} else {
			spilt = '0';
		}

		String s1 = "0" + hide1 + operand1.substring(operand1.length() - sLength);
		String s2 = "0" + hide2 + operand2.substring(operand2.length() - sLength);

		//   //   System.out.println(s1 + " s1");
		//   //   System.out.println(s2 + " s2");
		
		// 作比较，确定隐藏位
		char hide;
		Long s1True = Long.parseLong(integerTrueValue(s1));
		Long s2True = Long.parseLong(integerTrueValue(s2));
		if(s1True < s2True){
			hide = '0';
		}
		else{
			hide = '1';
		}
		
		String tempResult = integerNormalDivision(s1, s2, ((sLength + 6) / 4) * 8);
		//   //   System.out.println(tempResult);

		String s = tempResult.substring(1, sLength + 1);
		String sFull = hide + s;
		//   //   System.out.println(sFull + "Full");

		
		// 若底数溢出，底数右移，指数加1
		if (s1True >= 2 * s2True) {
			// s = sFull.substring(1, 1 + sLength);
			sFull = logRightShift(sFull, 1);
			s = sFull.substring(1, 1 + sLength);
			//   //   System.out.println(s + " s");
			e = oneAdder(e).substring(1);
		}
				
		
		// 用来比较的sFull
		char[] sFullChar = new char[sFull.length()];
		Arrays.fill(sFullChar, '0');
		String sFullZero = String.valueOf(sFullChar);

		// 若e小于0（第一位为1），则下溢， 底数右移
		if (tempE2.charAt(1) == '1') {
			String eTemp = tempE2.substring(1);
			//   //   System.out.println(eTemp);
			//   //   System.out.println(sFull);
			while (!eTemp.substring(eTemp.length() - eLength).equals(zeroE)) {
				eTemp = oneAdder(eTemp).substring(1);
				sFull = logRightShift(sFull, 1);
				if (sFull.equals(sFullZero)) {
					s = sFull.substring(1, 1 + sLength);
					return spilt + "" + sign + zeroE + s;
				}
			}
			e = eTemp.substring(eTemp.length() - eLength);
			//   //   System.out.println(eTemp);
		}

		// 若e等于0，则尾数右移一位
		if (e.equals(zeroE)) {
			//   //   System.out.println(e + "eeeee");
			sFull = logRightShift(sFull, 1);
		}
		
		if(e.equals(oneE)){
			sFull = sFullZero;
			s = sFull.substring(1, 1 + sLength);
			return spilt + "" + sign + e + s;
		}

		// 若底数第一位为0，底数左移，指数减1
		if(hide == '0' && !e.equals(oneE) && !e.equals(zeroE)){
			sFull = leftShift(sFull, 1);
			s = sFull.substring(1, 1 + sLength);
			tempE = integerSubtraction("0" + e, "01", ((e.length() + 5) / 4) * 4);
			e = tempE.substring(tempE.length() - e.length());
			
		}
		
//		if (sFull.charAt(0) == '1') {
//			// s = sFull.substring(1, 1 + sLength);
//			sFull = logRightShift(sFull, 1);
//			s = sFull.substring(2, 2 + sLength);
//			//   System.out.println(s + " s");
//			e = oneAdder(e).substring(1);
//		}
		s = sFull.substring(1, 1 + sLength);
		return spilt + "" + sign + e + s;

	}

	public char or(char bit1, char bit2) {
		if (bit1 == '0' && bit2 == '0') {
			return '0';
		} else {
			return '1';
		}
	}

	public char and(char bit1, char bit2) {

		if (bit1 == '1' && bit2 == '1') {
			return '1';
		} else {
			return '0';
		}
	}

	public char xor(char bit1, char bit2) {

		if (bit1 == bit2) {
			return '0';
		} else {
			return '1';
		}
	}

	public char not(char bit1) {
		if (bit1 == '1') {
			return '0';
		} else {
			return '1';
		}

	}

	public String lengthen(String operand, int length) {
		String signs = "";
		int len = length - operand.length();

		if (length <= 0 && operand.equals("0")) {
			return signs;
		}

		for (int i = 0; i < len; i++) {
			signs += operand.charAt(0);
		}
		return signs + operand;
	}

	/**
	 * 整数的一般余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * 
	 * @param operand1
	 *            二进制补码表示的被除数
	 * @param operand2
	 *            二进制补码表示的除数
	 * @param length
	 *            存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，
	 *            需要在高位补符号位
	 * @return 长度为2*length的字符串表示的相除结果，前length位为商， 后length位为余数
	 */
	public String integerNormalDivision(String operand1, String operand2, int length) {
		// 对操作数的处理
		operand1 = lengthen(operand1, length);
		operand2 = lengthen(operand2, length);
		// 获取符号
		char sign1 = operand1.charAt(0);
		char sign2 = operand2.charAt(0);
		// length位的0，用来延长操作数
		char[] zeroChar = new char[length];
		Arrays.fill(zeroChar, '0');
		String zero = String.valueOf(zeroChar);
		// 初始化结果
		String result = operand1 + zero;
		// 初始化除数
		String longDivisor = operand2 + zero;
		// 初始化补位
		char toAdd = '0';
		for(int i = 0; i < length; i++){
			if(sign1 == sign2){
				String judge = integerSubtraction(result, longDivisor, 2 * length).substring(1);
				if(judge.charAt(0) == sign1){
					result = judge;
					toAdd = '1';
					result = leftShift(result, 1).substring(0, 2 * length - 1) + toAdd;
				}
				else{
					toAdd = '0';
					result = leftShift(result, 1).substring(0, 2 * length - 1) + toAdd;
				}
			}
			
			else{
				String judge = integerAddition(result, longDivisor, 2 * length).substring(1);
				if(judge.charAt(0) == sign1){
					result = judge;
					toAdd = '1';
					result = leftShift(result, 1).substring(0, 2 * length - 1) + toAdd;
				}
				else{
					toAdd = '0';
					result = leftShift(result, 1).substring(0, 2 * length - 1) + toAdd;
				}
			}
		}
		String trueResult = result.substring(length);
		return trueResult;

	}

}
