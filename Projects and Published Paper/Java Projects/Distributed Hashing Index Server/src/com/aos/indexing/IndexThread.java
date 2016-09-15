package com.aos.indexing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class IndexThread implements Runnable {
	Socket socket;
	IndexingServer indServer;
	Index index;
	private PrintWriter writer;

	public IndexThread(Socket requestPeer, IndexingServer indexingServer,
			Index ind) {
		socket = requestPeer;
		indServer = indexingServer;
		index = ind;
	}

	@Override
	public void run() {
		readRequest();
	}

	private void readRequest() {
		String requestLine;
		BufferedReader stdIn;
		try {
			stdIn = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			requestLine = stdIn.readLine();
			String[] reqParam = requestLine.split(" ");
			String[] peerPort = reqParam[0].split("/");
			System.out.println(requestLine);
			if (reqParam[1].equals("Search")) {
				sendServers(reqParam[2]);
			} else if (reqParam[1].equals("Register"))
				register(reqParam[2],reqParam[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendServers(String string) {
		String message="";
		if(index!=null&&index.get(string)!=null)
		for(String ip:index.get(string))
			message=message+" "+ip;
		sendMessage(message);
	}

	private void register(String string, String reqParam) {
		index.put(string,reqParam);
	}
	
	private void sendMessage(String message) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		    oos.writeObject(message);
			
			
	//		writer = new PrintWriter(socket.getOutputStream(), true);
			System.out.println(message);
//			writer.println(message);
	//		writer.flush();
			//writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}