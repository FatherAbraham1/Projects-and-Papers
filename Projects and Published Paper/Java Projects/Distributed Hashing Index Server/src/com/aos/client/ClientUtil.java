package com.aos.client;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import com.aos.bean.NetworkProperties;

public class ClientUtil {
	private static Scanner in;
	private NetworkProperties networkProperties;
	private static Socket indexingServer;
	InetAddress selfIP;

	private static int selfPort;
	private Properties properties;

	public ClientUtil() {
		in = new Scanner(System.in);
		networkProperties = new NetworkProperties();
		try {
			selfIP = getFirstNonLoopbackAddress();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void fileServerProperties() {
		FileInputStream fileInputStream;
		properties = new Properties();
		try {
			fileInputStream = new FileInputStream("FileServerProperties.txt");
			properties.load(fileInputStream);
			fileInputStream.close();

			selfPort = Integer.parseInt(properties.getProperty("SelfPort"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out
					.println("File server properties file is not given. Please give properties file.");
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

	public static void main(String[] args) {
		ClientUtil client = new ClientUtil();
		int choice = -1;
		String fileName;
		do {
			System.out.println("\n* * * * * MENU * * * * * *");
			System.out.println("1. Register \t2. Search");
			System.out.println("3. Download \t4. Exit");
			System.out.println("Enter your choice");
			String choiceInp = in.next();
			try {
				choice = Integer.parseInt(choiceInp);
				switch (choice) {
				case 1:
					client.fileServerProperties();
					fileName = client.acceptFilename();
					
					client.register(fileName);
					break;
				case 2:
					fileName = client.acceptFilename();
					String response = client.search(fileName);
					String[] servers = response.split(" ");
					if (servers.length == 1)
						System.out.println("File not found");
					else {
						System.out.println("File found on :");
						for (int i = 1; i < servers.length; i++)
							System.out.println(servers[i]);
					}
					break;
				case 3:
					fileName = client.acceptFilename();
					client.obtain(fileName);
					break;
				case 4:
					System.out.println("THANK YOU!");
					System.exit(0);
					break;
				default:
					System.out
							.println("Invalid choice! Please enter correct input");
					break;
				}
			} catch (NumberFormatException exception) {
				System.err.println("Please enter valid choice");
				choice = 0;
			}
		} while (choice != -1);
	}

	public String search(String fileName) {
		// Getting file servers with file available.
		String response = "";
		PrintWriter writer;
		int server1ID = computeHash1(fileName);
		int server2ID = computeHash2(fileName);
		ObjectInputStream ois;
		String message = networkProperties.getIP(networkProperties.getSelfID())
				+ "/"
				+ networkProperties.getPort(networkProperties.getSelfID())
				+ " Search " + fileName;
		try {
			indexingServer = new Socket(networkProperties.getIP(server1ID),
					networkProperties.getPort(server1ID));
			writer = new PrintWriter(indexingServer.getOutputStream());
			writer.println(message);
			writer.flush();
			ois = new ObjectInputStream(indexingServer.getInputStream());
			response = (String) ois.readObject();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err
					.print("Server not available. Connecting to other server for resilience version");
			try {
				// Connecting to other server
				indexingServer = new Socket(networkProperties.getIP(server2ID),
						networkProperties.getPort(server2ID));
				writer = new PrintWriter(indexingServer.getOutputStream());
				writer.println(message);
				writer.flush();
				ois = new ObjectInputStream(indexingServer.getInputStream());
				response = (String) ois.readObject();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return response;
	}

	public void register(String fileName) {
		PrintWriter writer;
		int server1ID = computeHash1(fileName);
		int server2ID = computeHash2(fileName);

		String message;
		try {
			message = getFirstNonLoopbackAddress().toString() + "/" + selfPort
					+ " Register " + fileName;
			indexingServer = new Socket(networkProperties.getIP(server1ID),
					networkProperties.getPort(server1ID));
			writer = new PrintWriter(indexingServer.getOutputStream());
			writer.println(message);
			writer.flush();
			indexingServer = new Socket(networkProperties.getIP(server2ID),
					networkProperties.getPort(server2ID));
			writer = new PrintWriter(indexingServer.getOutputStream());
			writer.println(message);
			writer.flush();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String acceptFilename() {
		System.out.println("Enter file name");
		String fileName = in.next();
		return fileName;
	}

	public int obtain(String fileName) {
		// Getting file servers with file available.
		String response = search(fileName);

		// Choosing download server
		int serverCh;
		String[] servers = response.split(" ");
		if (servers.length > 2) {
			for (int i = 1; i < servers.length; i++)
				System.out.println("File server " + i + " : " + servers[i]);
			System.out.println("Enter choice of server");
			String serverChoice = in.next();
			serverCh = Integer.parseInt(serverChoice);
		} else {
			serverCh = 1;
		}
		if (servers.length == 1)
		{	System.out.println("File not found");
		
			return 0;
		}
		System.out
				.println("Downloading from file server: " + servers[serverCh]);
		try {
			// Connecting to file server
			String fileServer[] = servers[serverCh].split("/");

			System.out.println(fileServer[0]);
			System.out.println(fileServer[1]);
			Socket serverConnection = new Socket(fileServer[1],
					Integer.parseInt(fileServer[2]));
			// Sending filename to the server

			ObjectOutputStream oos = new ObjectOutputStream(
					serverConnection.getOutputStream());
			oos.writeObject(fileName);

			System.out.println("File name sent");
			// reading size of file
			ObjectInputStream ois = new ObjectInputStream(
					serverConnection.getInputStream());
			double nosofpackets = (double) ois.readObject();

			// Downloading from file server
			int packetsize = 1024;
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			for (double i = 0; i < nosofpackets + 1; i++) {
				InputStream is = serverConnection.getInputStream();
				byte[] mybytearray = new byte[packetsize];
				int bytesRead = is.read(mybytearray, 0, mybytearray.length);
				System.out.println("Packet:" + (i + 1));
				bos.write(mybytearray, 0, mybytearray.length);
			}
			serverConnection.close();
			bos.close();
		} catch (IOException e) {
			System.err.print("Server not available");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

	private int computeHash1(String key) {
		int hash = key.hashCode() % 8;
		if (hash < 0)
			hash += 8;
		return hash;
	}

	private int computeHash2(String key) {
		int hash = (key.hashCode() + 4) % 8;
		if (hash < 0)
			hash += 8;
		return hash;
	}

	private static InetAddress getFirstNonLoopbackAddress()
			throws SocketException {
		Enumeration en = NetworkInterface.getNetworkInterfaces();
		while (en.hasMoreElements()) {
			NetworkInterface i = (NetworkInterface) en.nextElement();
			for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
				InetAddress addr = (InetAddress) en2.nextElement();
				if (!addr.isLoopbackAddress()) {
					if (addr instanceof Inet4Address) {
						return addr;
					}
					if (addr instanceof Inet6Address)
						continue;
				}
			}
		}
		return null;
	}
}