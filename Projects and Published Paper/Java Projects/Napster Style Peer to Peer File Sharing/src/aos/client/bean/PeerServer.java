package aos.client.bean;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerServer implements Runnable{
	private int SelfPort;
	private String selfDirectory;
	private ServerSocket selfSocket;

	public PeerServer(int selfPort, String directory) {
		super();
		SelfPort = selfPort;
		selfDirectory=directory;
	}
	
	/**
	 * Starts listening on Selfport and accepts requests from other Peers for downloading
	 */
	@Override
	public void run() {
		try {
			selfSocket = new ServerSocket(SelfPort);
			System.out.println("Started listening on port : "+SelfPort);	
			while (true) {
				Socket requestPeer = selfSocket.accept();
				
				Thread t = new Thread(new PeerProxy(requestPeer,selfDirectory));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
