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
	    name="Temp.insertProducts",
	    resultClasses = { Temp.class },
	    procedureName="insertProducts",
	    parameters={
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="productId",type = Integer.class),
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="qty", type=Integer.class),
	      }
	    ),
	  @NamedStoredProcedureQuery(
			    name="Temp.deletebyId",
			    resultClasses = { Temp.class },
			    procedureName="deleteProductbyId",
			    parameters={
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "id",type = String.class)
			      }
			    ),
	    @NamedStoredProcedureQuery(
	    		name="Temp.GetTempDetails",
	    		procedureName="GetTemps",
	    		resultClasses = { Temp.class })
	})
@Table(name="temp",schema="keells")
public class Temp implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int temporyId;
	private int productId;
	private int qty;
	private int retailPrice;
	private int total;
	
	public Temp() {
	}

	public Temp(int temporyId, int productId, int qty, int retailPrice, int total) {
		super();
		this.temporyId = temporyId;
		this.productId = productId;
		this.qty = qty;
		this.retailPrice = retailPrice;
		this.total = total;
	}
	
	@Column(name="temporyId")
	public int getTemporyId() {
		return temporyId;
	}
	public void setTemporyId(int temporyId) {
		this.temporyId = temporyId;
	}
	
	@Column(name="productId")
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}
	@Column(name="qty")
	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	@Column(name="retailPrice")
	public int getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(int retailPrice) {
		this.retailPrice = retailPrice;
	}
	@Column(name="total")
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	


}
