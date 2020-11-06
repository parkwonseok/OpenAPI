package com.sprpa.openapi.vo;

public class DataVO {
	private String address;
	private String name;
	private String date;
	private String price;
	private int floor;
	private double area;
	private double longitude;
	private double latitude;
	private String areacode;
	public DataVO(String address, String name, String date, String price, int floor, double area, double longitude,
			double latitude, String areacode) {
		super();
		this.address = address;
		this.name = name;
		this.date = date;
		this.price = price;
		this.floor = floor;
		this.area = area;
		this.longitude = longitude;
		this.latitude = latitude;
		this.areacode = areacode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
}
