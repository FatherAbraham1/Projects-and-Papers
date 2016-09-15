package aos.server.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import aos.bean.Peer;
import aos.server.util.IndexingServer;

/**
 * Thread for handling each request made on a single client to server connection
 * as long as connection is maintained
 * 
 * @author Sampada
 * 
 */

public class ServerThread implements Runnable {
	private Socket peer;
	private IndexingServer server;
	private PrintWriter writer;

	public ServerThread(Socket requestPeer, IndexingServer indexingServer) {
		peer = requestPeer;
		server = indexingServer;

	}

	/**
	 * Searches file in registry and responses peer writing all peers having
	 * that file
	 * 
	 * @param fileName
	 *            Name of the file to be searched
	 * @param p Peer to send results to
	 */
	/*private void search(String fileName, Socket p) {
		ArrayList<Peer> peers = IndexingServer.search(fileName);
		if (peers == null || peers.size() == 0) {
			System.out.println("No results found");
			sendMessage(peer, "No results found");
		} else {
			System.out.println(peers.toString());
			sendMessage(peer, peers.toString());
		}
		if(peers!=null)
			sendMessage(p, peers.toString());
		else
			sendMessage(p, "");		
	}*/

	/**
	 * Registers a peer with his list of files in registry
	 * 
	 * @param reqParam
	 *            Arraylist of filenames to be registered
	 * @param peer
	 *            The peer which will be registered
	 */

	private void register(String[] reqParam, Peer p) {
		// Separating names of files
		String[] files = reqParam[2].split(",");

		for (String a : files)
			// Adding all files one by one
			IndexingServer.addFile(a, p);

		sendMessage(peer, "Registered Successfully");
	}

	/**
	 * This will read the request received on port and process it further to
	 * provide appropriate operation
	 */
	public void readRequest() {
		String requestLine;
		BufferedReader stdIn;
		try {
			stdIn = new BufferedReader(new InputStreamReader(
					peer.getInputStream()));
			requestLine = stdIn.readLine();
			// Processing requestLine to find out port number and IP address of
			// client
			String[] reqParam = requestLine.split(" ");
			String[] peerPort = reqParam[0].split("/");
			System.out.println("Received request from : " + reqParam[0]);

			Peer p = new Peer(peerPort[1], Integer.parseInt(peerPort[0]));
			if (reqParam[1].equals("Search")) {
				//search(reqParam[2],peer);
			} else if (reqParam[1].equals("Register"))
				register(reqParam, p);
			else if (reqParam[1].equals("Add")) {
				IndexingServer.addFile(reqParam[2], p);
				sendMessage(peer, "Added Successfully");
			} else if (reqParam[1].equals("Delete")) {
				IndexingServer.deleteFile(reqParam[2], p);
				sendMessage(peer, "Deleted Successfully");
			} else if (reqParam[1].equals("Download")) {
				sendDownloadServers(reqParam[2],peer);
			} else 
				unknownMessage(peer);
			server.registrySave();
			server.printRegistry();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void unknownMessage(Socket p) {
		System.out.println("Unknown command received");
		sendMessage(p, "Unknown command received");
		System.exit(0);
	}

	@Override
	public void run() {
		readRequest();
	}

	private void sendDownloadServers(String reqParam, Socket peer2) {
		
		ArrayList<Peer> peers = IndexingServer.search(reqParam);
		if (peers == null || peers.size() == 0) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(peer2.getOutputStream());
				oos.writeObject(peers);
				
/*				writer = new PrintWriter(peer2.getOutputStream(), true);
				writer.print(peers);
				writer.flush();
				//writer.close();
*/
				} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("No results found");
			//sendMessage(peer, "No results found");
		} else {
			System.out.println(peers.toString());
			try {
				ObjectOutputStream oos = new ObjectOutputStream(peer2.getOutputStream());
				oos.writeObject(peers);
				
/*				writer = new PrintWriter(peer2.getOutputStream(), true);
				writer.print(peers);
				writer.flush();
				//writer.close();
*/
				} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void sendMessage(Socket peerSocket, String message) {
		try {
			writer = new PrintWriter(peerSocket.getOutputStream(), true);
			writer.println(message);
			writer.flush();
			//writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}