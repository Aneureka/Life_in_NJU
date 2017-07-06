package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public boolean login(String username, String password) throws RemoteException {

		boolean accountMatched = false;

		// 读取文件，判断用户名与密码是否与已有的匹配
		try {

			// 获得文件，若文件不存在，返回false
			File accountList = new File("accountList.txt");

			if (!accountList.exists()) {
				return false;
			}

			// 读取文件
			FileReader accountListReader = new FileReader(accountList);
			BufferedReader br = new BufferedReader(accountListReader);
			String line = null;

			// 判断用户名与密码是否与文件内的某一个用户名密码匹配
			while ((line = br.readLine()) != null) {
				
				List<String> accountLine = Arrays.asList(line.split(" "));

				if (accountLine.get(0).equals(username) && accountLine.get(1).equals(password)) {
					accountMatched = true;
				}

			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return accountMatched;

	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

	@Override
	public boolean signUp(String username, String password) {

		boolean accountAlreadyExisted = false;

		// （创建并）读取文件，判断用户名是否已经存在
		try {

			// 创建文件
			File accountList = new File("accountList.txt");
			if (!accountList.exists()) {
				accountList.createNewFile();
			}

			// 读取文件
			FileReader accountListReader = new FileReader(accountList);
			BufferedReader br = new BufferedReader(accountListReader);
			String line = null;

			// 判断用户名是否已经存在
			while ((line = br.readLine()) != null) {

				List<String> accountLine = Arrays.asList(line.split(" "));

				if (accountLine.get(0).equals(username)) {
					accountAlreadyExisted = true;
				}
			}

			br.close();

			if (accountAlreadyExisted) {
				return false;
			}

			else {
				// 写入新用户名及密码，中间用空格隔开
				FileWriter accountListWriter = new FileWriter(accountList, true);
				accountListWriter.write(username + " " + password + "\n");
				accountListWriter.close();
				
				// 为新用户创建文件夹作为保存文件的路径
				File accountDir = new File(username);
				if(!accountDir.exists()){
					accountDir.mkdirs();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		

		return true;
	}

}
