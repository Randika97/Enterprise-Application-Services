package com.misaka.stores.model;
import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ParameterMode;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.NamedNativeQuery;
@Entity
//@NamedStoredProcedureQuery(
//		  name="Employee.GetAllEmployee",
//		  procedureName="GetEmployee",
//		  resultClasses = { Employee.class }
//		)
//@NamedNativeQuery(name = "Getsecondname",
//				  callable = true,
//				  query = "{? = call Getsecondname (?) }",
//				  resultClass = Employee.class
//		)
//@NamedStoredProcedureQueries({
//	  @NamedStoredProcedureQuery(
//	    name="Employee.insertEmployee",
//	    resultClasses = { Employee.class },
//	    procedureName="insertEmployee",
//	    parameters={
//	    @StoredProcedureParameter(mode = ParameterMode.IN, name = "id",type = Integer.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name = "first_name",type = String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="last_name", type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="gender",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="date_to",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="date_from",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="dob",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="contact_no",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="Address",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="district",type=String.class),
//	      @StoredProcedureParameter(mode = ParameterMode.IN, name="skills",type=String.class)
//	      }
//	    ),
//	    @NamedStoredProcedureQuery(
//	    		name="Employee.GetAllEmployee",
//	    		procedureName="GetEmployee",
//	    		resultClasses = { Employee.class })
//	})

@Table(name="employee",schema="dms")

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String first_name;
	private String last_name;
	private String gender;
	private String date_to;
	private String date_from;
	private String skills;
	private String dob;
	private String contact_no;
	private String address;
	private String district;
	

	public Employee(int id, String first_name, String last_name, String gender, String date_to, String date_from,
			String skills, String dob, String contact_no, String address, String district) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.date_to = date_to;
		this.date_from = date_from;
		this.skills = skills;
		this.dob = dob;
		this.contact_no = contact_no;
		this.address = address;
		this.district = district;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	@Column(name="skills")
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	@Column(name="dob")
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	@Column(name="district")
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name="first_name")
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	@Column(name="last_name")
	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	@Column(name="date_to")
	public String getDate_to() {
		return date_to;
	}

	public void setDate_to(String date_to) {
		this.date_to = date_to;
	}
	@Column(name="date_from")
	public String getDate_from() {
		return date_from;
	}

	public void setDate_from(String date_from) {
		this.date_from = date_from;
	}

	public String getContact_no() {
		return contact_no;
	}
	@Column(name="contact_no")
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	@Column(name="id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name="gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
