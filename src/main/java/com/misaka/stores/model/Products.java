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
	    name="Products.insertProducts",
	    resultClasses = { Products.class },
	    procedureName="insertProducts",
	    parameters={
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="productId",type = String.class),
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="productName", type=String.class),
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="catergory",type=String.class),
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="productPriceRetail",type=String.class),
	      @StoredProcedureParameter(mode = ParameterMode.IN, name="stock",type=Integer.class)
	      }
	    ),
	  @NamedStoredProcedureQuery(
			    name="Products.updateProducts",
			    resultClasses = { Products.class },
			    procedureName="updateProductbyId",
			    parameters={
			      @StoredProcedureParameter(mode = ParameterMode.IN, name="id",type = Integer.class),
			      @StoredProcedureParameter(mode = ParameterMode.IN, name="productId",type = String.class),
			      @StoredProcedureParameter(mode = ParameterMode.IN, name="productName", type=String.class),
			      @StoredProcedureParameter(mode = ParameterMode.IN, name="catergory",type=String.class),
			      @StoredProcedureParameter(mode = ParameterMode.IN, name="productPriceRetail",type=String.class),
			      @StoredProcedureParameter(mode = ParameterMode.IN, name="stock",type=Integer.class)
			      }
			    ),
	  @NamedStoredProcedureQuery(
			    name="Products.deletebyId",
			    resultClasses = { Products.class },
			    procedureName="deleteProductbyId",
			    parameters={
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "id",type = String.class)
			      }
			    ),
	  @NamedStoredProcedureQuery(
			    name="Products.getproductsbyid",
			    resultClasses = { Products.class },
			    procedureName="getProductbyId",
			    parameters={
			    @StoredProcedureParameter(mode = ParameterMode.IN, name = "id",type = String.class)
			      }
			    ),
	    @NamedStoredProcedureQuery(
	    		name="Products.GetProducts",
	    		procedureName="GetProducts",
	    		resultClasses = { Products.class })
	})
@Table(name="products",schema="keells")
public class Products implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String productId;
	private String productName;
	private String catergory;
	private String productPriceRetail;
	private int stock;


	public Products(int id, String productId, String productName, String catergory, String productPriceRetail,
			int stock) {
		super();
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.catergory = catergory;
		this.productPriceRetail = productPriceRetail;
		this.stock = stock;
	}

	public Products() {
	}

	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="productId")
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Column(name="productName")
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Column(name="catergory")
	public String getCatergory() {
		return catergory;
	}
	public void setCatergory(String catergory) {
		this.catergory = catergory;
	}
	@Column(name="productPriceRetail")
	public String getProductPriceRetail() {
		return productPriceRetail;
	}
	public void setProductPriceRetail(String productPriceRetail) {
		this.productPriceRetail = productPriceRetail;
	}

	@Column(name="stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}


	

}
