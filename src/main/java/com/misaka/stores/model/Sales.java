package com.misaka.stores.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="sales",schema="keells")
public class Sales implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String transactionId;
	private String transactionDateTime;
	private String total;
	private String status;
	
	public Sales() {
	
	}
	public Sales(int id, String transactionId, String transactionDateTime, String total, String status) {
		super();
		this.id = id;
		this.transactionId = transactionId;
		this.transactionDateTime = transactionDateTime;
		this.total = total;
		this.status = status;
	}
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="id")
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	@Column(name="transactionDateTime")
	public String getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	@Column(name="total")
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	@Column(name="status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
