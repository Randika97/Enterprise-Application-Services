package com.misaka.stores.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
@Entity
@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
		    name="Sales.deletebyid",
		    resultClasses = { Sales.class },
		    procedureName="salesDeleteById",
		    parameters={
		      @StoredProcedureParameter(mode = ParameterMode.IN, name = "transactionId",type = Integer.class),
		      }
		),
	@NamedStoredProcedureQuery(
		    name="Sales.updateSales",
		    resultClasses = { Sales.class },
		    procedureName="salesUpdate",
		    parameters={
		      @StoredProcedureParameter(mode = ParameterMode.IN, name = "transactionId",type = Integer.class),
		      @StoredProcedureParameter(mode = ParameterMode.IN, name = "transactionDateTime",type = String.class),
		      @StoredProcedureParameter(mode = ParameterMode.IN, name = "total",type = Integer.class),
		      @StoredProcedureParameter(mode = ParameterMode.IN, name = "stat",type = String.class),
		      }
		),
	@NamedStoredProcedureQuery(
		    name="Sales.getsalesbyid",
		    resultClasses = { Sales.class },
		    procedureName="getsalesbyid",
		    parameters={
		      @StoredProcedureParameter(mode = ParameterMode.IN, name = "transactionId",type = Integer.class),
		      }
		),
	@NamedStoredProcedureQuery(
			name="Sales.GetSales",
	    	procedureName="GetSales",
	    	resultClasses = { Sales.class })
	})
@Table(name="sales",schema="keells")
public class Sales implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionId;
	private String transactionDateTime;
	private int total;
	private String stat;
	

	public Sales(int transactionId, String transactionDateTime, int total, String stat) {
		super();
		this.transactionId = transactionId;
		this.transactionDateTime = transactionDateTime;
		this.total = total;
		this.stat = stat;
	}
	public Sales() {
	}

	@Column(name="transactionId")
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Column(name="status")
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}


}
