package com.aos.bean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RMINetworkProperties {
	private static Integer[] RmiIds;
	private static int[] RMIPorts;
	private static String[] ServerIPs;
	private static int selfID, noOfServers;

	public RMINetworkProperties() {
		readNetworkProperties();
	}

	private void readNetworkProperties() {
		Properties properties = new Properties();

		RmiIds=new Integer[8];
		RMIPorts=new int[8];
		ServerIPs=new String[8];
		
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("config.txt");
			properties.load(fileInputStream);
			fileInputStream.close();

			selfID = Integer.parseInt(properties.getProperty("selfId"));
			noOfServers = Integer.parseInt(properties.getProperty("noOfServers"));
			for (int i = 0; i < 8; i++) {
				RMIPorts[i] = Integer.parseInt(properties.getProperty("port"
						+ i));
				ServerIPs[i] = properties.getProperty("IP" + i);
				RmiIds[i] = i;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out
					.println("Connection properties file is not given. Please give properties file.");
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
		return RMIPorts[i];
	}

	public Integer getID(int i) {
		return RmiIds[i];
	}

	public int getSelID() {
		return selfID;
	}

	public int getServers() {
		// TODO Auto-generated method stub
		return noOfServers;
	}

}
