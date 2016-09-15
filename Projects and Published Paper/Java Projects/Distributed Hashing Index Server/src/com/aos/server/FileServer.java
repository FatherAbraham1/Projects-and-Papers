package com.aos.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class FileServer {
	private static int selfPort;
	private static String path = "";
	private Properties properties;

	public FileServer() {
		FileInputStream fileInputStream;
		properties = new Properties();
		try {
			fileInputStream = new FileInputStream("FileServerProperties.txt");
			properties.load(fileInputStream);
			fileInputStream.close();

			selfPort = Integer.parseInt(properties.getProperty("SelfPort"));
			path = properties.getProperty("SelfDirectory");

		} catch (FileNotFoundException e) {
			System.err
					.print("File server properties file is not given. Please give properties file.");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("File server properties not set. Please set the properties or check whether file is located at correct address in order to begin.");
			System.exit(0);
		}
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		FileServer fileServer = new FileServer();
		fileServer.start();
	}

	private void start() {
		System.out.println("File server started to listen on port : "
				+ selfPort);

		try {
			ServerSocket servsock = new ServerSocket(selfPort);
			while (true) {
				Socket peer = servsock.accept();
				Thread t = new Thread(new FileThread(servsock,peer, path));
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}