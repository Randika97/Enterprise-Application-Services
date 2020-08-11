package com.misaka.stores.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="products",schema="keells")
public class Stock implements Serializable	{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String stockId;
	private String stock;
	private String st_productid;
	
	public Stock() {
	}
	
	public Stock(int id, String stockId, String stock, String st_productid) {
		super();
		this.id = id;
		this.stockId = stockId;
		this.stock = stock;
		this.st_productid = st_productid;
	}
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="stockId")
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	@Column(name="stock")
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	@Column(name="st_productid")
	public String getSt_productid() {
		return st_productid;
	}
	public void setSt_productid(String st_productid) {
		this.st_productid = st_productid;
	}


}
