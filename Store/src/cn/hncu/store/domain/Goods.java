package cn.hncu.store.domain;

import java.util.Set;


public class Goods {
	
	private String id;
	private String name;
	private double price;
	private double rebate;
	private String photo;
	private int stock;
	private String onlineTime;
	private String descr;
	
	private SellType sellType;
	private Brand brand;
	private Object goods;
	private Set<OrderMsg> orderMsgs;
	
	public SellType getSellType() {
		return sellType;
	}
	public void setSellType(SellType sellType) {
		this.sellType = sellType;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Object getGoods() {
		return goods;
	}
	public void setGoods(Object goods) {
		this.goods = goods;
	}
	public Set<OrderMsg> getOrderMsgs() {
		return orderMsgs;
	}
	public void setOrderMsgs(Set<OrderMsg> orderMsgs) {
		this.orderMsgs = orderMsgs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Goods other = (Goods) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
