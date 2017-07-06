package rmi;

import java.rmi.Remote;

import service.ExecuteService;
import service.IOService;
import service.UserService;

public class RemoteHelper {
	private Remote remote;
	private static RemoteHelper remoteHelper = new RemoteHelper();
	
	public static RemoteHelper getInstance(){
		return remoteHelper;
	}
	
	private RemoteHelper() {
	}
	
	public void setRemote(Remote remote){
		this.remote = remote;
	}
	
	public IOService getIOService(){
		return (IOService)remote;
	}
	
	public UserService getUserService(){
		return (UserService)remote;
	}
	
	public ExecuteService getExecuteService(){
		return (ExecuteService)remote;
	}
	
}
