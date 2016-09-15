package com.aos.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class HashServer extends UnicastRemoteObject implements HashingInterface,Serializable {
	private static final long serialVersionUID = 12698297698L;
	private ConcurrentHashMap<String, String> hashtable;
	private int self;
	public HashServer(int id) throws RemoteException {
		super();
		self=id;
		FileInputStream fin;
		ObjectInputStream inputStream;
		try {
			fin = new FileInputStream("HashtableServer"+self+".txt");
			inputStream = new ObjectInputStream(fin);
			hashtable = (ConcurrentHashMap<String,String>) inputStream.readObject();
			System.out.println(hashtable);
		} catch (FileNotFoundException e) {
			System.out.println("Creating new Distributed Hash Table");
			hashtable = new ConcurrentHashMap<String, String>();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.print("You might need to delete existing registry");
		}
	}

	@Override
	public boolean put(String key, String value) throws RemoteException {
		if (hashtable.containsKey(key))
			return false;
		hashtable.put(key, value);
		save();
		return true;
	}

	@Override
	public String get(String key) throws RemoteException {
		return hashtable.get(key);
	}

	@Override
	public boolean del(String key) throws RemoteException {
		if (hashtable.containsKey(key)){
			hashtable.remove(key);	
			save();
			return true;
		}		
		return false;
	}

	@Override
	public void save() throws RemoteException{
		try {
			FileOutputStream fout = new FileOutputStream("HashtableServer"+self+".txt");
			ObjectOutputStream outputStream = new ObjectOutputStream(fout);
			outputStream.writeObject(hashtable);
			outputStream.flush();
			System.out.println("Hash Table Status :"+this.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		Iterator<String> i=hashtable.keySet().iterator();
		String hash="";
			while(i.hasNext()){
				String temp=i.next();
				hash=hash+"\n"+"Key : "+temp+"\tValue : "+hashtable.get(temp);
			}	
		return hash;
	}	
}
