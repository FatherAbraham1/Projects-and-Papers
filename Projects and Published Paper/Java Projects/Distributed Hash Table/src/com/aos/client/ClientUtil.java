package com.aos.client;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Scanner;

import com.aos.bean.RMINetworkProperties;

public class ClientUtil {
	private RMINetworkProperties networkProperties;
	private static Scanner in;
	private Hashtable<Integer, Connections> connections;
	public ClientUtil() {
		networkProperties = new RMINetworkProperties();
		in = new Scanner(System.in);
		connections = new Hashtable<Integer, Connections>();
	}

	public static void main(String[] args) {
		int choice = -1;
		ClientUtil util = new ClientUtil();
		do {
			System.out.println("\n* * * * *  MENU* * * * * *");
			System.out.println("1. Put\t2. Delete");
			System.out.println("3. View\t4. Exit");
			System.out.println("Enter your choice");
			String choiceInp = in.next();
			try {
				choice = Integer.parseInt(choiceInp);
				switch (choice) {
				case 1:
					util.add();
					break;
				case 2:
					util.del();
					break;
				case 3:
					util.get();
					break;
				case 4:
					System.out.println("THANK YOU!");
					System.exit(0);
					break;
				default:
					System.out
							.println("Wrong input. Please enter correct option.");
					break;
				}
			} catch (NumberFormatException exception) {
				System.out.println("Please enter correct choice");
				choice = 0;
			}
		} while (choice != 4);
	}

	private void get() {
		String key;
		key = getInput("key");
		int hash = computeHash(key);

		Connections connection = checkExistingConnections(hash);
		if (connection != null) {
			try {
				if(connection.get(key))
					connections.remove(connection.getRmiId());
			} catch (RemoteException e) {
				e.printStackTrace();
			}		
		}	
	}

	private void del() {
		String key;
		key = getInput("key");
		int hash = computeHash(key);
		Connections connection = checkExistingConnections(hash);
		if (connection != null) {
			try {
				if (!connection.del(key))
					connections.remove(connection.getRmiId());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("Failed to delete Key : " + key);	
	}

	private String getInput(String key) {
		System.out.println("Enter " + key + ": ");
		String a;
		if (key.equals("key")) {
			do {
				a = in.next();
				if (a.length() > 24)
					System.out
							.println("Key size should not exceed more than 24 bytes.\n Please re-enter the key");
			} while (a.length() > 24);
		} else {
			do {
				a = in.next();
				if (a.length() > 1000)
					System.out
							.println("Value size should not exceed more than 1000 bytes.\n Please re-enter the value");
			} while (a.length() > 1000);
		}
		return a;
	}

	private void add() {
		String key, value;
		key = getInput("key");
		value = getInput("value");
		int hash = computeHash(key);
		Connections connection = checkExistingConnections(hash);
		if (connection != null) {
			try {
				if(!connection.add(key, value))
					connections.remove(connection.getRmiId());				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else
			System.out.println("Failed to add Key : " + key + "\t Value : "
					+ value);
	}

	private Connections checkExistingConnections(int hash) {
		Integer RmiId = networkProperties.getID(hash);
		if (connections.containsKey(RmiId)) {
			System.out.println("Using previous connection");
		} else
		{
			System.out.println("Existing connections : "+connections);
			Connections connection = new Connections(RmiId,
					networkProperties.getPort(hash),
					networkProperties.getIP(hash));
			if (connection.isAlive()) {
				connections.put(RmiId, connection);
				System.out.println("New connection created");
				System.out.println("All connections : "+connections);
			}
		}
		return connections.get(RmiId);
	}

	private int computeHash(String key) {
		int hash = key.hashCode() % 8;
		if (hash < 0)
			hash += 8;
		return hash;
	}
}
