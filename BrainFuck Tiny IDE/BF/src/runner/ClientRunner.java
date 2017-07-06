package runner;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.RemoteHelper;
import service.IOService;
import ui.MainFrame;

/**
 * @author GuoHaobin
 * 用户对接方面，实现了登入，登出，注册功能，用户注册后为其生成以用户名命名的文件夹，供其存储文件，这里预先存储用户名为"admin"，密码为"123456"。
 * 里面预先存储了"Helloworld"，"一位数加法"，"一位数乘法"的测试代码。
 * 用户登录后，Account按钮会变成以用户名命名的按钮，用户可以双击以登出，当鼠标移到用户名按钮时会给出气泡提示。
 * 操作体验方面，未登录时，用户可以进行新建、运行等操作，但不能进行打开文件，保存文件等操作，因为其没有申请自己的存储空间。
 * 实现版本功能，用户可以多次保存不同版本的代码。
 * 实现了undo，redo功能，逻辑基本与eclipse（模拟对象）相同，第一次undo需要操作两次，针对这点，第一次点击undo时会给出提示，当用户再次点击时提示消失。
 * Yeah！
 **/

public class ClientRunner {
	private RemoteHelper remoteHelper;
	
	public ClientRunner() {
		linkToServer();
		initGUI();
	}
	
	private void linkToServer() {
		try {
			remoteHelper = RemoteHelper.getInstance();
			remoteHelper.setRemote(Naming.lookup("rmi://localhost:8888/DataRemoteObject"));
			System.out.println("linked");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	
	private void initGUI() {
		MainFrame mainFrame = new MainFrame();
	}

	
	public static void main(String[] args){
		ClientRunner cr = new ClientRunner();
		
	}
}
