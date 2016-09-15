package com.aos.indexing;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.aos.bean.NetworkProperties;

public class IndexingServer {
	private NetworkProperties networkProperties;
	private int selfID;
	private Index index;
	private ServerSocket serverSocket;
	public IndexingServer() {
		networkProperties=new NetworkProperties();
		selfID=networkProperties.getSelfID();
		networkProperties = new NetworkProperties();
		index=new Index(selfID);
	}
	
	public static void main(String[] args) {
		IndexingServer server=new IndexingServer();
		server.start();
	}
	
	public void start() {
		System.out.println("Indexing Server"+selfID+" started");
		try {
			serverSocket = new ServerSocket(networkProperties.getPort(selfID));
			while (true) {
				Socket requestPeer = serverSocket.accept();
				Thread t = new Thread(new IndexThread(requestPeer, this, index));				
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
