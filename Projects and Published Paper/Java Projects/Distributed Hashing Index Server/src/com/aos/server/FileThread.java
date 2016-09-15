package com.aos.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileThread implements Runnable {
	Socket peer;
	ServerSocket fileServer;
	String selfUploadDirectory;
	
	public FileThread(ServerSocket servsock, Socket peerSocket, String path) {
		peer = peerSocket;
		selfUploadDirectory = path;
		fileServer=servsock;
	}

	@Override
	public void run() {
		int packetsize = 1024;
		File myFile;
		String requestLine;
		BufferedReader stdIn;
		try {
			ObjectInputStream ois = new ObjectInputStream(peer.getInputStream());
			requestLine = (String) ois.readObject();
			
			System.out.println("Request received for file : "+ requestLine);
			
			myFile = new File(selfUploadDirectory + "/" + requestLine);
			System.out.println("Uploading file : " + requestLine);
			double nosofpackets = Math.ceil(((int) myFile.length())
					/ packetsize);
			ObjectOutputStream oos = new ObjectOutputStream(
					peer.getOutputStream());
			oos.writeObject(nosofpackets);

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(myFile));
			for (double i = 0; i < nosofpackets + 1; i++) {
				byte[] mybytearray = new byte[packetsize];
				bis.read(mybytearray, 0, mybytearray.length);
				System.out.println("Packet:" + (i + 1));
				OutputStream os = peer.getOutputStream();
				os.write(mybytearray, 0, mybytearray.length);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
