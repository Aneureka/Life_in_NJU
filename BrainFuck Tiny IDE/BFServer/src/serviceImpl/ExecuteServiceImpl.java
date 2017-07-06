//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {

		// 作为容器，code操作的对象
		ArrayList<Integer> container = new ArrayList<>();
		// 作为容器，保存result的Integer形式
		ArrayList<Integer> resultOfInteger = new ArrayList<>();
		// 保存result
		String result = "";
		// 容器中，code操作的位置
		int indexOfContainer = 0;
		// 输入中，code操作的位置
		int indexOfParam = 0;
		// 标记 [ 的位置， 不需要标记 ]
		ArrayList<Integer> circleIndex = new ArrayList<>();

		// 初始化container
		container.add(0);

		for (int i = 0; i < code.length(); i++) {

			switch (code.charAt(i)) {
			case '>':
				// 判断是否超出右边界，若超出边界，则add
				if (indexOfContainer + 1 > container.size() - 1) {
					container.add(0);
				}
				// 右移（若出界先创建后右移）		
				indexOfContainer++;
				break;

			case '<':
				// 判断是否超出左边界，若超出边界，报错，并return错误信息
				if (indexOfContainer - 1 < 0) {
					return "Wrong!";
				}
				// 左移（若出界不会进行左移操作）
				indexOfContainer--;
				break;

			case '+':
				// 提取Integer值，+1，然后先remove，再add（插入），减法同理
				int tempAdd = container.get(indexOfContainer);
				tempAdd++;
				container.remove(indexOfContainer);
				container.add(indexOfContainer, tempAdd);
				break;

			case '-':
				int tempMinus = container.get(indexOfContainer);
				tempMinus--;
				container.remove(indexOfContainer);
				container.add(indexOfContainer, tempMinus);
				break;

			case '.':
				// 把当前指针所指的值（Integer类型）输出到result里
				resultOfInteger.add(container.get(indexOfContainer));
				break;
			case ',':
				// 解析为ASCII码
				char input = param.charAt(indexOfParam);
				int inputToInt = (int) input;

				// 若没到结尾，指针+1
				if (indexOfParam < param.length() - 1) {
					indexOfParam++;
				}

				// 传到container
				container.remove(indexOfContainer);
				container.add(indexOfContainer, inputToInt);
				break;

			case '[':
				// 添加 [ 的位置
				circleIndex.add(i);
				break;

			case ']':
				// 先判断是不是为0，若为0，去掉circleIndex最后一个值，并向前一步
				if (container.get(indexOfContainer) == 0) {
					circleIndex.remove(circleIndex.size() - 1);
					break;
				}
				// 若不为0，返回到原来的地方
				else {
					i = circleIndex.get(circleIndex.size() - 1);
					break;
				}

			default:
				System.out.println("Wrong input at " + i);
				break;

			}
		}

		for (int index : resultOfInteger) {
			result += (char) index;
		}

		return result;

	}

}
