package com.aos.indexing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Index implements Serializable {
	private static final long serialVersionUID = 12698297698L;
	private ConcurrentHashMap<String, ArrayList<String>> hashtable;
	private int self;

	public Index(int id) {
		super();
		self = id;
		FileInputStream fin;
		ObjectInputStream inputStream;
		try {
			fin = new FileInputStream("HashtableServer" + self + ".txt");
			inputStream = new ObjectInputStream(fin);
			hashtable = (ConcurrentHashMap<String, ArrayList<String>>) inputStream
					.readObject();
			System.out.println(hashtable);
		} catch (FileNotFoundException e) {
			System.out.println("Creating new Distributed Hash Table");
			hashtable = new ConcurrentHashMap<String, ArrayList<String>>();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.print("You might need to delete existing registry");
		}
	}

	public boolean put(String key, String value) {
		if (hashtable.containsKey(key)) {
			ArrayList<String> ips = hashtable.get(key);
			if (!ips.contains(value))
				ips.add(value);
		} else {
			ArrayList<String> ips = new ArrayList<String>();
			ips.add(value);
			hashtable.put(key, ips);
		}
		save();
		return true;
	}

	public ArrayList<String> get(String key) {
		return hashtable.get(key);
	}

	public void save() {
		try {
			FileOutputStream fout = new FileOutputStream(
					"HashtableServer" + self + ".txt");
			ObjectOutputStream outputStream = new ObjectOutputStream(fout);
			outputStream.writeObject(hashtable);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toString() {
		Iterator<String> i = hashtable.keySet().iterator();
		String hash = "";
		while (i.hasNext()) {
			String temp = i.next();
			hash = hash + "\n" + "Key : " + temp + "\tValue : "
					+ hashtable.get(temp);
		}
		return hash;
	}
}
