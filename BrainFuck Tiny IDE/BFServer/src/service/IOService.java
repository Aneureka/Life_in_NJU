//需要客户端的Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName, String time)throws RemoteException;
	
	public List<String> readFile(String userId, String fileName)throws RemoteException;
	
	public List<String> readFileList(String userId)throws RemoteException;
	
	public List<String> readVersionList(String userId, String fileName)throws RemoteException;

	public List<String> readFileVersion(String userId, String fileName, String versionName)throws RemoteException;
}
