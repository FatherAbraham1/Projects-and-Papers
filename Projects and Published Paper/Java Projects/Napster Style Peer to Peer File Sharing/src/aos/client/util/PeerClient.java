package aos.client.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

import aos.bean.Peer;
import aos.client.bean.PeerServer;

public class PeerClient {
	private static Socket indexingServer;
	private static String hostName, selfDirectory, selfIP;
	private static int serverPort, selfPort;

	// Initiating self client
	public PeerClient(String host, int server, int port) {
		super();
		hostName = host;
		serverPort = server;
		selfPort = port;
		try {
			selfIP = getFirstNonLoopbackAddress().toString();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Properties properties = new Properties();
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("properties");
			properties.load(fileInputStream);
			fileInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out
					.println("Connection properties file is not given. Please give properties file.");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			serverPort = Integer.parseInt(properties.getProperty("serverPort"));
			selfPort = Integer.parseInt(properties.getProperty("selfPort"));
			hostName = properties.getProperty("hostName");
			selfDirectory = properties.getProperty("selfDirectory");
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Connection properties not set. Please set the properties or check whether file is located at correct address in order to begin.");
			System.exit(0);
		}
		// Starting server on Peer side to listen on self port
		Thread selfServer = new Thread(new PeerServer(selfPort, selfDirectory));
		selfServer.start();

		PeerClient self = new PeerClient(hostName, serverPort, selfPort);
		int choice;
		String message = "";
		do {
			System.out
					.println("Peer Client\nMenu:\n1. Register file list\t2. Register directory on server\n3. Add file\t\t4. Delete file\n5. Search file\t\t6. Download\n7. Exit");

			String choiceInp = in.next();
			try {
				choice = Integer.parseInt(choiceInp);

				switch (choice) {
				case 1:
					message = self.register();
					break;
				case 2:
					message = self.registerDirectory();
					break;
				case 3:
					message = self.addFile();
					break;
				case 4:
					message = self.deleteFile();
					break;
				case 5:
					System.out.println("Enter file name to search: ");
					String fileName = in.next();
					self.search(fileName);
					break;
				case 6:
					System.out.println("Enter file name to download: ");
					String file = in.next();
					self.download(file);
					break;
				case 7:
					System.out.println("THANK YOU!!");
					System.exit(0);
					break;
				default:
					break;
				}

				if (choice != 5 && choice != 6)
					self.requestConnection(selfPort + selfIP + " " + message);

			} catch (NumberFormatException exception) {
				System.out.println("Please enter correct choice");
				choice = 0;
			}
		} while (choice != 7);
	}
		
	private void download(String fileName) {
		// TODO Auto-generated method stub
		ArrayList<Peer> response = search(fileName);
		if (response != null && response.size() != 0) {
			int MAX = 104857600;
			Peer peer =response.get(0);
				System.out.println("Trying to download from server : " + peer);
				
				int size,total = 0;
				FileOutputStream outputStream = null;
				BufferedOutputStream bufferedOutputStream = null;
				Socket socket = null;
				try {
					socket = new Socket(peer.getIpAddress(), peer.getPeerPort());
					sendMessage(socket, fileName);
					byte[] bytesReceived = new byte[MAX];
					InputStream is = socket.getInputStream();
					outputStream = new FileOutputStream(fileName);
					bufferedOutputStream = new BufferedOutputStream(outputStream);
					size = is.read(bytesReceived, 0, bytesReceived.length);
					total = size;

					do {
						size = is.read(bytesReceived, total,
								(bytesReceived.length - total));
						if (size >= 0)
							total += size;
					} while (size > -1);

					bufferedOutputStream.write(bytesReceived, 0, total);
					bufferedOutputStream.flush();
					System.out.println("File " + fileName + " downloaded ("
							+ total + " bytes read)");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch(Exception exception){
					System.out.println("Could not download from"+ peer);
				}
				finally {
					if (outputStream != null)
						try {
							outputStream.close();
							if (bufferedOutputStream != null)
								bufferedOutputStream.close();
							if (socket != null)
								socket.close();
						} catch (IOException e) {

							e.printStackTrace();
						}
				}


				

		}
		else
			System.out.println("File not found");
	}

	private ArrayList<Peer> search(String fileName) {

		PeerClient self = new PeerClient(hostName, serverPort, selfPort);
		ArrayList<Peer> response = null;
		ObjectInputStream ois;
		try {
			indexingServer = new Socket(hostName, serverPort);
			sendMessage(indexingServer, selfPort + selfIP + " " + "Download "
					+ fileName);

			ois = new ObjectInputStream(indexingServer.getInputStream());
			response = (ArrayList<Peer>) ois.readObject();
			int i = 0;
			if (response == null || response.size() == 0)
				System.out.println("File not found");
			for (Peer peer : response) {
				System.out.println(++i + "  " + peer);
			}

		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	private String registerDirectory() {
		String fileName = "";
		final File location = new File(selfDirectory);

		fileName = addFiles(location);
		System.out.println("Files in folder : " + fileName);
		return "Register " + fileName;
	}

	private String addFiles(File location) {
		String files = "";
		for (final File f : location.listFiles()) {
			if (f.isDirectory()) {
				files = files + "," + addFiles(f);
			} else {
				files = files + "," + f.getName();
			}
		}
		return files;
	}

	/*
	 * private static String searchFile() { PeerClient self = new
	 * PeerClient(hostName, serverPort, selfPort); Scanner in = new
	 * Scanner(System.in); System.out.println("Enter file name to search : ");
	 * String fileName = in.next(); BufferedReader stdIn = null; try {
	 * indexingServer = new Socket(hostName, serverPort);
	 * sendMessage(indexingServer, selfPort + selfIP + " " + "Search " +
	 * fileName);
	 * 
	 * stdIn = new BufferedReader(new InputStreamReader(
	 * indexingServer.getInputStream())); String response = stdIn.readLine();
	 * 
	 * //System.out.println(response); return "Search response : " + response;
	 * // stdIn.close(); // indexingServer.close(); } catch (ConnectException e)
	 * { e.printStackTrace(); } catch (UnknownHostException e) {
	 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
	 * return ""; }
	 */
	private String deleteFile() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter file name to delete from server : ");
		String fileName = in.next();
		return "Delete " + fileName;
	}

	private String addFile() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter file name to add on server : ");
		String fileName = in.next();
		return "Add " + fileName;
	}

	private String register() {
		Scanner in = new Scanner(System.in);
		System.out
				.println("Enter file names separated by comma ',' and no spaces in between (e.g. : a.txt,b.txt) :");
		String fileNames = in.next();
		return "Register " + fileNames;
	}

	// Sending request for connection to indexing server
	private void requestConnection(String message) {
		try {
			indexingServer = new Socket(hostName, serverPort);
			sendMessage(indexingServer, message);

		} catch (ConnectException e) {
			System.out.println("Server not available.");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sendMessage(Socket indexingServer, String request) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(indexingServer.getOutputStream());
			writer.println(request);
			writer.flush();
			// writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Fetching public ip address of self machine
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
