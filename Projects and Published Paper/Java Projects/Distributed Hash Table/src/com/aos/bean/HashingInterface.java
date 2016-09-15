package com.aos.bean;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface HashingInterface extends Remote{

	public String get(String key)throws RemoteException;
	public boolean put(String key,String value) throws RemoteException;
	public boolean del(String key)throws RemoteException;
	public void save()throws RemoteException;
}
