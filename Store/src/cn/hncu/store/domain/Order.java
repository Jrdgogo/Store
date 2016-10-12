package cn.hncu.store.domain;

import java.util.HashSet;
import java.util.Set;

public class Order {
	
	private String id;
	private String consignee;
	private char paytype;
	private double amount;
	private char state;
	private String orderdate;
	
	private User user;
	private Set<OrderMsg> orderMsgs=new HashSet<OrderMsg>();
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public char getPaytype() {
		return paytype;
	}
	public void setPaytype(char paytype) {
		
		this.paytype = paytype;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
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
		Order other = (Order) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", consignee=" + consignee + ", paytype="
				+ paytype + ", amount=" + amount + ", state=" + state
				+ ", orderdate=" + orderdate + ", orderMsgs=" + orderMsgs + "]";
	}
	
	
}
