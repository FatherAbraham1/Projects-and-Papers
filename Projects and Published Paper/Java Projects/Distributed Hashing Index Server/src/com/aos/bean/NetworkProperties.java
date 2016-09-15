package com.aos.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class NetworkProperties {
	private static int[] Ports;
	private static String[] ServerIPs;
	private static int selfID;

	public NetworkProperties() {
		readNetworkProperties();
	}

	private void readNetworkProperties() {
		Properties properties = new Properties();

		Ports=new int[8];
		ServerIPs=new String[8];
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("config.txt");
			properties.load(fileInputStream);
			fileInputStream.close();

			selfID = Integer.parseInt(properties.getProperty("selfId"));
			for (int i = 0; i < 8; i++) {
				Ports[i] = Integer.parseInt(properties.getProperty("port"
						+ i));
				ServerIPs[i] = properties.getProperty("IP" + i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err
					.print("Connection properties file is not given. Please give properties file.");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Connection properties not set. Please set the properties or check whether file is located at correct address in order to begin.");
			System.exit(0);
		}
	}

	public String getIP(int i) {
		return ServerIPs[i];
	}

	public int getPort(int i) {
		return Ports[i];
	}

	public int getSelfID() {
		return selfID;
	}

}
