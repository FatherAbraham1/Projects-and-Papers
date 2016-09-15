package aos.bean;

import java.io.Serializable;

public class Peer implements Serializable{
	private String ipAddress;
	private Integer peerPort, bandwidth;
	public Peer(String ipAddress, Integer peerPort, Integer bandwidth) {
		super();
		//Read from property file
		this.ipAddress = ipAddress;
		this.peerPort = peerPort;
		this.bandwidth = bandwidth;
	}
	
	public Peer(String ipAddress, Integer peerPort) {
		super();
		this.ipAddress = ipAddress;
		this.peerPort = peerPort;
		this.bandwidth=-1;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPeerPort() {
		return peerPort;
	}
	public void setPeerPort(Integer peerPort) {
		this.peerPort = peerPort;
	}
	public Integer getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	@Override
	public String toString() {
		return "\t[  IP : " + ipAddress + ",  Port : " + peerPort
				+ ",  Bandwidth=" + bandwidth + "  ]\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
		result = prime * result
				+ ((peerPort == null) ? 0 : peerPort.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peer other = (Peer) obj;
		if (ipAddress == null) {
			if (other.ipAddress != null)
				return false;
		} else if (!ipAddress.equals(other.ipAddress))
			return false;
		if (peerPort == null) {
			if (other.peerPort != null)
				return false;
		} else if (!peerPort.equals(other.peerPort))
			return false;
		return true;
	}
	
	
}