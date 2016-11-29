package com.viki.bean;

public class HouseBean {
	long id;//id
	double totalPrice;//�ܼ�
	double unitPrice;//����
	String info;//����
	String region;//С��
	String street;//�ֵ�
	String tag;//��5����2
	String link;//����
	String location;//λ���ĸ���
	double upOrDown;//�ǵ�
	boolean isNew;//���Ϸ�Դ
		
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public double getUpOrDown() {
		return upOrDown;
	}
	public void setUpOrDown(double upOrDown) {
		this.upOrDown = upOrDown;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public String getTag() {
		return tag;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	

	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public static HouseBean createHouse(String link,String region,String location,String info,String tag,String totalPrice,
			String id,String unitPrice,String street){
		HouseBean h = new HouseBean();
		h.setLink(link);
		h.setRegion(region);
		h.setLocation(location);
		h.setInfo(info);
		h.setTag(tag);
		h.setTotalPrice(Double.parseDouble(totalPrice));
		h.setId(Long.parseLong(id));
		h.setUnitPrice(Double.parseDouble(unitPrice));
		h.setStreet(street);
		return h;
	}
	
	
}
