package aos.server.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import aos.bean.Peer;
import aos.server.bean.Registry;
import aos.server.bean.ServerThread;

public class IndexingServer {
	private ServerSocket serverSocket;
	private int SelfPort;
	private static Registry registry;


	public IndexingServer(int selfPort) {
		super();
		SelfPort = selfPort;

		FileInputStream fin;
		ObjectInputStream inputStream;
		try {
			fin = new FileInputStream("registry.txt");
			inputStream = new ObjectInputStream(fin);
			registry = (Registry) inputStream.readObject();
			System.out.println(registry);
		} catch (FileNotFoundException e) {
			System.out.println("Creating new registry");
			registry = new Registry();
			System.out.println("Server started...");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.print("You might need to delete existing registry");
		}
	}

	public static void main(String args[]) {
		Properties properties = new Properties();
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("serverProperties");
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
		Integer serverPort = null;
		try{
		serverPort=Integer.parseInt(properties.getProperty("serverPort"));
		}catch(Exception e){
			System.out.println("Connection properties not set. Please set the properties or check whether file is located at correct address in order to begin.");
		}
		IndexingServer indexingServer = new IndexingServer(serverPort);
		indexingServer.start();
	}

	public void start() {
		try {
			serverSocket = new ServerSocket(SelfPort);
			while (true) {
				Socket requestPeer = serverSocket.accept();
				Thread t = new Thread(new ServerThread(requestPeer, this));				
				t.start();
				System.out.println(t.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Persist registry into a file registry.txt
	public void registrySave() {
		try {
			FileOutputStream fout = new FileOutputStream("registry.txt");
			ObjectOutputStream outputStream = new ObjectOutputStream(fout);
			// Writing registry in file
			outputStream.writeObject(registry);
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		// Saving registry before exiting
		registrySave();
	}

	/**
	 * Adding single file and its location to registry
	 * 
	 * @param file
	 *            File to be added
	 * @param peer
	 *            Location of file to be added
	 */
	public static void addFile(String file, Peer peer) {
		registry.add(file, peer);
	}

	/**
	 * Searches a file in index registry for locations, and returns the peers
	 * where it is present
	 * 
	 * @param file
	 *            Name of file to be searched
	 * @return Returns list of peers having the file
	 */
	public static ArrayList<Peer> search(String file) {
		ArrayList<Peer> peers = registry.search(file);
		return peers;
	}

	/**
	 * Deleting peer entry for a file
	 * 
	 * @param file
	 *            Name of file for which peer entry to be deleted
	 * @param peer
	 *            Peer whose entry to be removed from file peers
	 */
	public static void deleteFile(String file, Peer peer) {
		registry.remove(file, peer);
	}
	
	public static void printRegistry(){
		System.out.println("Current Registry : \n"+registry.toString());
	}

	public static void download(String string, Peer p) {

		
	}
}
	