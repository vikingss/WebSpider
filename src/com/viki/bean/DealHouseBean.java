package com.viki.bean;

public class DealHouseBean {
	double totalPrice;//总价
	double unitPrice;//单价
	String info;//详情
	String year;//建筑年代
	String region;//小区
	String street;//街道
	String tag;//满5，满2
	String link;//链接
	String location;//位于哪个区 
	String dealDate;//成交日期
	public String getDate() {
		return dealDate;
	}
	public void setDate(String dealDate) {
		this.dealDate = dealDate;
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public static DealHouseBean createDeal(String link,String region,String location,String info,String tag,String totalPrice,String unitPrice,String year,String dealDate,String street){

		DealHouseBean h = new DealHouseBean();
		h.setLink(link);
		h.setRegion(region);
		h.setLocation(location);
		h.setInfo(info);
		h.setTag(tag);
//		System.out.println("totalPrice:" + totalPrice);
		h.setTotalPrice(Double.parseDouble(totalPrice));
		h.setUnitPrice(Double.parseDouble(unitPrice));
		h.setYear(year);
		h.setDate(dealDate);
		h.setStreet(street);
		return h;
	}
	public double getTotalPrice() {

		return this.totalPrice;
	}
}
