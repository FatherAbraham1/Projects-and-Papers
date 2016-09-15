package com.aos.evalAPI;

import java.util.Hashtable;
import java.util.Scanner;

import com.aos.bean.RMINetworkProperties;
import com.aos.client.Connections;

public class Eval {
	private RMINetworkProperties networkProperties;
	private static Scanner in;
	private Hashtable<Integer, Connections> connections;
	String workload[][] = new String[100000][4];

	public Eval() {
		networkProperties = new RMINetworkProperties();
		in = new Scanner(System.in);
		connections = new Hashtable<Integer, Connections>();
	}

	void workload() {
		for (int i = 0; i < 100000; i++) {
			String k = String.format("%02d", networkProperties.getSelID())
					+ String.format("%08d", i);
			String v = String.format("%02d", networkProperties.getSelID())
					+ String.format("%088d", i);
			workload[i][0] = k;
			workload[i][1] = v;
			int hash = computeHash(k);
			workload[i][2] = Integer.toString(hash);
			workload[i][3] = networkProperties.getIP(hash);
		}
	}

	private int computeHash(String key) {
		int hash = Math.abs(key.hashCode() % networkProperties.getServers());
		return hash;
	}

}
