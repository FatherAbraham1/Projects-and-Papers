package com.aos.server;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.aos.bean.HashServer;
import com.aos.bean.RMINetworkProperties;


public class ServerUtil {

	public static void main(String[] args) {
		RMINetworkProperties networkProperties=new RMINetworkProperties();
		int self = networkProperties.getSelID();
		int RMIPort = networkProperties.getPort(self);
		String RMI= networkProperties.getID(self).toString();
		try {
			HashServer hashServer = new HashServer(networkProperties.getID(self));
			Registry registry = LocateRegistry.createRegistry(RMIPort);
			registry.bind(RMI,hashServer);
			System.out.println("Server has started on port "+RMIPort+" RMI ID : "+RMI);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	} 
}