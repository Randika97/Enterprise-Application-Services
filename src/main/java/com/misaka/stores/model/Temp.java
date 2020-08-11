package com.misaka.stores.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="temp",schema="keells")
public class Temp implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String productId;
	private String qty;
	private String retailPrice;
	private String total;
	
	public Temp() {
	}
	
	public Temp(int id, String productId, String qty, String retailPrice, String total) {
		super();
		this.id = id;
		this.productId = productId;
		this.qty = qty;
		this.retailPrice = retailPrice;
		this.total = total;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

}
