//请不要修改本文件名
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExecuteService extends Remote {
	
	/**
	 * 我们将通过此方法测试你的解析器功能，请不要修改方法名，参数类型，返回参数类型
	 * @param code bf源代码
	 * @return 运行结果
	 * @throws RemoteException
	 */
	public String execute(String code, String param) throws RemoteException;
}
