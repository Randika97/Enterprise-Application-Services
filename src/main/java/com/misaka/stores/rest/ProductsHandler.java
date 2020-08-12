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
@Path("/{versionID}/productDetails/")
public class ProductsHandler {
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;
	@EJB
	GenericCrudDAOIF genaricCrudDAO;
	@GET
	@Path("/getProduct")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@Context SecurityContext sc, @Context UriInfo uriInfo) throws ParseException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
		//List<Products> list = null;
		Integer firstRecord = 0;
		Integer maxResults = 50;
		boolean paginationInfo = false;
		StringBuilder whereSb = null;
		Long count = 0l;
		Map<String, Object> jqlParameters = new HashMap<String, Object>();
		EntityManager em = genaricCrudDAO.getEntityManager();
		
		try {
//			whereSb = new StringBuilder("where 1=1 ");
//			if (queryParameters.containsKey("first")) {
//				firstRecord = Integer.parseInt(queryParameters.getFirst("first")) - 1;
//			}
//			if (queryParameters.containsKey("maxResults")) {
//				maxResults = Integer.parseInt(queryParameters.getFirst("maxResults"));
//			}
//			if (queryParameters.containsKey("first_name")) {
//				whereSb.append("and e.first_name like '%" + queryParameters.getFirst("first_name") + "%' ");
//			}
//			if (queryParameters.containsKey("last_name")) {
//				whereSb.append("and e.last_name like '%" + queryParameters.getFirst("last_name") + "%' ");
//			}
//			if (queryParameters.containsKey("district")) {
//				whereSb.append("and e.district like '%" + queryParameters.getFirst("district") + "%' ");
//			}
//			if (queryParameters.containsKey("gender")) {
//				whereSb.append("and e.gender like '%" + queryParameters.getFirst("gender") + "%' ");
//			}
//			if (queryParameters.containsKey("date_to")) {
//				whereSb.append("and e.date_to like '%" + queryParameters.getFirst("date_to") + "%' ");
//			}
//			if (queryParameters.containsKey("date_from")) {
//				whereSb.append("and e.date_from like '%" + queryParameters.getFirst("date_from") + "%' ");
//			}
//			if (queryParameters.containsKey("paginationInfo")) {
//				paginationInfo = Boolean.parseBoolean(queryParameters.getFirst("paginationInfo"));
//			}
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Products.GetProducts");
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<ProductsHandler> resultList = procedureQuery.getResultList();
			//list = genaricCrudDAO.createQuery("SELECT e FROM Employee e " + whereSb.toString() + " order by e.first_name ASC", jqlParameters,firstRecord, maxResults);
			returnObject.put("products", resultList);
		
//			if (firstRecord == 0 || paginationInfo) {
//
//				StringBuffer jpqlCountSb = new StringBuffer();
//				jpqlCountSb.append("Select count(distinct e.id) from Product e ");
//				jpqlCountSb.append(whereSb);
//				count = (Long) genaricCrudDAO.createSingleResultQuery(jpqlCountSb.toString(), jqlParameters,0, maxResults);
//				returnObject.put("noOfRecords", count);
//			}
				updateSerializationMixIns(objectMapper, true);
				System.out.println("Taking Product details from the database :)");
				return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();

			} catch (Exception e) {
				System.out.println(e);
			}finally {
				objectMapper = null;
				returnObject = null;
				//list = null;
				queryParameters = null;
				jqlParameters = null;
				whereSb = null;
				System.out.println("API has been callled :)");
			}
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed").build();
	}
	
	@POST
	@Path("/saveProducts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveProducts(Products products, @Context SecurityContext sc, @Context UriInfo uriInfo)
			throws JsonProcessingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> returnObject = new HashMap<String, Object>();
			EntityManager em = genaricCrudDAO.getEntityManager();
			//MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Products.insertProducts");
			procedureQuery.setParameter("productId",products.getProductId() );
			procedureQuery.setParameter("productName", products.getProductName());
			procedureQuery.setParameter("catergory", products.getCatergory());
			procedureQuery.setParameter("productPriceRetail", products.getProductPriceRetail());
			procedureQuery.setParameter("stock", products.getStock());
			procedureQuery.execute();
			@SuppressWarnings("unchecked")
			List<Products> resultList = procedureQuery.getResultList();
			genaricCrudDAO.update(procedureQuery);
			returnObject.put("status",resultList);
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}
	
	@GET
	@Path("/prodcutById/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response prodcutById(@PathParam("id") Long id, @Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		EntityManager pro = genaricCrudDAO.getEntityManager();
		try {
			parameters.put("id", id);
			StoredProcedureQuery procedureQuery = pro.createNamedStoredProcedureQuery("Products.getproductsbyid");
    		procedureQuery.setParameter("id",id.intValue());
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<Sales> resultList = procedureQuery.getResultList();
			returnObject.put("product",resultList);
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
	@Path("/deleteProdcutById/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProdcutById(@PathParam("id") int id, @Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		Map<String,Object> parameters = new HashMap<String,Object>();
		EntityManager sale = genaricCrudDAO.getEntityManager();

		try {
			parameters.put("id", id);
			StoredProcedureQuery procedureQuery = sale.createNamedStoredProcedureQuery("Products.deletebyId");
    		procedureQuery.setParameter("id",id);
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
	@Path("/updateProduct")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(Products products, @Context SecurityContext sc, @Context UriInfo uriInfo)
			throws JsonProcessingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> returnObject = new HashMap<String, Object>();
			EntityManager em = genaricCrudDAO.getEntityManager();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Products.updateProducts");
			procedureQuery.setParameter("id",products.getId());
			procedureQuery.setParameter("productId",products.getProductId());
			procedureQuery.setParameter("productName", products.getProductName());
			procedureQuery.setParameter("catergory", products.getCatergory());
			procedureQuery.setParameter("productPriceRetail", products.getProductPriceRetail());
			procedureQuery.setParameter("stock", products.getStock());
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
