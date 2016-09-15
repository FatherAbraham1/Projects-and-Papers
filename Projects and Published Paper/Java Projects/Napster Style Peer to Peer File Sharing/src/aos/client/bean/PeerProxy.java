package aos.client.bean;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class PeerProxy implements Runnable {

	Socket requestPeer;
	String selfDirectory;
	public PeerProxy(Socket peer, String dir) {
		requestPeer = peer;
		selfDirectory=dir;
	}

	@Override
	public void run() {
		readRequest();
	}
	
	public void upload(String file){
		FileInputStream fStream = null;
		BufferedInputStream inputStream = null;
		OutputStream outputStream = null;
		try {
				try {
					
					System.out.println("Request received : " + requestPeer);
					File fileName = new File(selfDirectory+"/"+file); //

					byte[] mybytearray = new byte[(int) fileName.length()];
					fStream = new FileInputStream(fileName);
					inputStream = new BufferedInputStream(fStream);
					System.out.println("Size : " + (int) fileName.length());
					inputStream.read(mybytearray, 0, (int) fileName.length());
					outputStream = requestPeer.getOutputStream();
					System.out.println("Sending " + file );
					outputStream.write(mybytearray, 0, (int) fileName.length());
					outputStream.flush();
					System.out.println("File sent successfully.");
				}catch(FileNotFoundException exception){
					System.out.println("File no longer exists on this server");
				} 
				finally {
					if (inputStream != null)
						inputStream.close();
					if (outputStream != null)
						outputStream.close();
					if (requestPeer != null)
						requestPeer.close();
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	
	public void readRequest() {
		String requestLine;
		try {
			/*ObjectInputStream ois = new ObjectInputStream(
					requestPeer.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(requestPeer.getOutputStream());
*/
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					requestPeer.getInputStream()));
			requestLine = stdIn.readLine();

//			requestLine = (String) ois.readObject();
				System.out.println("Received request for : "+requestLine);
				//ois
				
				upload(requestLine);
				
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
