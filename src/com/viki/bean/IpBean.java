package com.viki.bean;

public class IpBean {
	private String ip;
	private int port;
	public IpBean(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	public String getIp() {
		return this.ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
