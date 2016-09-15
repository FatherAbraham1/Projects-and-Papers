package com.aos.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.aos.bean.HashingInterface;

public class Connections {
	private Registry registry;
	private String ServerIP;
	private Integer RmiId;
	private int RMIPort;
	private HashingInterface hashingInterface;
	private boolean alive = false;

	public Connections(int id, int port, String IP) {
		ServerIP = IP;
		RMIPort = port;
		RmiId = id;
		connect();
	}

	public void connect() {
		System.out.println("Trying to connect to server IP: " + ServerIP
				+ "; Port: " + RMIPort + "; ID: " + RmiId);
		try {
			registry = LocateRegistry.getRegistry(ServerIP, RMIPort);
			hashingInterface = (HashingInterface) registry.lookup(RmiId
					.toString());
			alive = true;
		} catch (java.rmi.ConnectException e) {
			System.out.println("Not able to connect to server");
			alive = false;
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.out
					.println("Server not up. Please try again after some time..");
			e.printStackTrace();
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean add(String key, String value) throws RemoteException {
		try {
			if (hashingInterface != null) {
				if (hashingInterface.put(key, value))
					System.out.println("Key : " + key + "\t Value : " + value
							+ " added successfully");
				else
					System.out
							.println("Key "
									+ key
									+ " already exists! To overwrite it's value, try deleting existing one.");
				return true;
			}
		} catch (RemoteException e) {
			System.out.println("Connecion lost.. Trying to reconnect..");
			connect();
			if (isAlive()) {
				if (hashingInterface.put(key, value))
					System.out.println("Key : " + key + "\t Value : " + value
							+ " added successfully");
				else
					System.out
							.println("Key "
									+ key
									+ " already exists! To overwrite it's value, try deleting existing one.");
				return true;
			} else
				System.out
						.println("Server no longer available. Please try after some time");
		}
		return false;
	}

	public boolean del(String key) throws RemoteException {
		try {
			if (hashingInterface != null) {
				if (hashingInterface.del(key))
					System.out.println("Key : " + key + " deleted successfully");
				else
					System.out.println("Failed to delete Key : " + key);
				return true;
			}
		} catch (RemoteException e) {
			System.out.println("Connecion lost.. Trying to reconnect..");
			connect();
			if (isAlive()) {
				if (hashingInterface.del(key))
					System.out.println("Key : " + key + " deleted successfully");
				else
					System.out.println("Failed to delete Key : " + key);
				return true;
			} else
				System.out
						.println("Server no longer available. Please try after some time");
		}
		return false;
	}

	public boolean get(String key) throws RemoteException {
		String value;
		try {
			if(hashingInterface!=null){
			value=hashingInterface.get(key);
			if( value!=null)
				System.out.println("Value Found!!\nResult : Key : " + key
						+ "\t Value : " + value);
			else
				System.out.println("Key : " + key + " not found");
			return true;
			}
			else
				System.out.println("Failed to search Key : " + key);
		} catch (RemoteException e) {
			System.out.println("Connecion lost.. Trying to reconnect..");
			connect();
			if (isAlive()) {
				value=hashingInterface.get(key);
				if( value!=null)
					System.out.println("Value Found!!\nResult : Key : " + key
							+ "\t Value : " + value);
				else
					System.out.println("Key : " + key + " not found");
				return true;				
			}			
			else
				System.out
						.println("Server no longer available. Please try after some time");
		}
		return false;
	}
	
	public Integer getRmiId() {
		return RmiId;
	}

	public void setRmiId(Integer rmiId) {
		RmiId = rmiId;
	}

	@Override
	public String toString() {
		return "Connections [ServerIP=" + ServerIP + ", RmiId=" + RmiId
				+ ", RMIPort=" + RMIPort + ", alive=" + alive + "]";
	}

}
