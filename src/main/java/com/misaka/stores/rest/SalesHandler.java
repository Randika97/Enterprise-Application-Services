package com.misaka.stores.rest;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.misaka.stores.common.GenericCrudDAOIF;
import com.misaka.stores.model.Employee;
import com.misaka.stores.model.Products;
import com.misaka.stores.model.Sales;

@Stateless
@Path("/{versionID}/salesDetails/")
public class SalesHandler {
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;
	@EJB
	GenericCrudDAOIF genaricCrudDAO;
	@GET
	@Path("/getSales")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@Context SecurityContext sc, @Context UriInfo uriInfo) throws ParseException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		EntityManager em = genaricCrudDAO.getEntityManager();
		
		try {
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Sales.GetSales");
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<Employee> resultList = procedureQuery.getResultList();
			//list = genaricCrudDAO.createQuery("SELECT e FROM Employee e " + whereSb.toString() + " order by e.first_name ASC", jqlParameters,firstRecord, maxResults);
			returnObject.put("list", resultList);
			updateSerializationMixIns(objectMapper, true);
			System.out.println("Taking Product details from the database :)");
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			objectMapper = null;
			returnObject = null;
			System.out.println("API has been callled successfully");
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed").build();
	}
	@GET
	@Path("/salesById/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salesById(@PathParam("id") Long id, @Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		EntityManager sale = genaricCrudDAO.getEntityManager();
		try {
			parameters.put("id", id);
			StoredProcedureQuery procedureQuery = sale.createNamedStoredProcedureQuery("Sales.getsalesbyid");
    		procedureQuery.setParameter("transactionId",id.intValue());
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<Sales> resultList = procedureQuery.getResultList();
			returnObject.put("Sales",resultList);
			resultList = null;
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
		finally {
			objectMapper = null;
			returnObject = null;
		}
	}
	@DELETE
	@Path("/deleteSalesById/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSalesById(@PathParam("id") int id, @Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		EntityManager sale = genaricCrudDAO.getEntityManager();

		try {
			parameters.put("id", id);
			StoredProcedureQuery procedureQuery = sale.createNamedStoredProcedureQuery("Sales.deletebyid");
    		procedureQuery.setParameter("transactionId",id);
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<Sales> resultList = procedureQuery.getResultList();
			resultList = null;
			returnObject.put("status", "Success");
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}

	
	@POST
	@Path("/updateSales")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUser(Sales sales, @Context SecurityContext sc, @Context UriInfo uriInfo)
			throws JsonProcessingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> returnObject = new HashMap<String, Object>();
			EntityManager em = genaricCrudDAO.getEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Sales.updateSales");
			procedureQuery.setParameter("transactionId", sales.getTransactionId());
			procedureQuery.setParameter("transactionDateTime", sales.getTransactionDateTime());
			procedureQuery.setParameter("total", sales.getTotal());
			procedureQuery.setParameter("stat", sales.getStat());
			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Employee> resultList = procedureQuery.getResultList();
			genaricCrudDAO.update(procedureQuery);
			returnObject.put("status",resultList);
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}
	
	
	
	
	
	
	private void updateSerializationMixIns(ObjectMapper objectMapper, boolean isQuestionTemplateList) {
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
	}

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private abstract class IgnoreHibernatePropertiesInJackson {
	}
}
