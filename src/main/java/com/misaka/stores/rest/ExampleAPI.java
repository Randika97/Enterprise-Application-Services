package com.misaka.stores.rest;


import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.persistence.*;
import com.misaka.stores.common.GenericCrudDAOIF;
import com.misaka.stores.model.Employee;
import com.misaka.stores.model.District;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;





/**
 * Describe what this API does.
 *
 */
@Stateless
@Path("/{versionID}/example/")
public class ExampleAPI {

	/**
	 * Retrieves user information by id.
	 * @param sc
	 * @param uriInfo
	 * @param headers
	 * @return
	 * @throws JsonProcessingException
	 */
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	@EJB
	GenericCrudDAOIF genaricCrudDAO;
	
	@GET
	@Path("/getEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSymptoms(@Context SecurityContext sc, @Context UriInfo uriInfo) throws ParseException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
		//List<Employee> list = null;
		Integer firstRecord = 0;
		Integer maxResults = 50;
		boolean paginationInfo = false;
		StringBuilder whereSb = null;
		Long count = 0l;
		Map<String, Object> jqlParameters = new HashMap<String, Object>();
		EntityManager em = genaricCrudDAO.getEntityManager();
		
		try {
			whereSb = new StringBuilder("where 1=1 ");
			if (queryParameters.containsKey("first")) {
				firstRecord = Integer.parseInt(queryParameters.getFirst("first")) - 1;
			}
			if (queryParameters.containsKey("maxResults")) {
				maxResults = Integer.parseInt(queryParameters.getFirst("maxResults"));
			}
			if (queryParameters.containsKey("first_name")) {
				
				whereSb.append("and e.first_name like '%" + queryParameters.getFirst("first_name") + "%' ");
				
			}
			if (queryParameters.containsKey("last_name")) {
				whereSb.append("and e.last_name like '%" + queryParameters.getFirst("last_name") + "%' ");
			}
			if (queryParameters.containsKey("district")) {
				whereSb.append("and e.district like '%" + queryParameters.getFirst("district") + "%' ");
			}
			if (queryParameters.containsKey("gender")) {
				whereSb.append("and e.gender like '%" + queryParameters.getFirst("gender") + "%' ");
			}
			if (queryParameters.containsKey("date_to")) {
				whereSb.append("and e.date_to like '%" + queryParameters.getFirst("date_to") + "%' ");
			}
			if (queryParameters.containsKey("date_from")) {
				whereSb.append("and e.date_from like '%" + queryParameters.getFirst("date_from") + "%' ");
			}
			if (queryParameters.containsKey("paginationInfo")) {
				paginationInfo = Boolean.parseBoolean(queryParameters.getFirst("paginationInfo"));
			}
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Employee.GetAllEmployee");
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<Employee> resultList = procedureQuery.getResultList();
			//list = genaricCrudDAO.createQuery("SELECT e FROM Employee e " + whereSb.toString() + " order by e.first_name ASC", jqlParameters,firstRecord, maxResults);
			returnObject.put("list", resultList);
		
			if (firstRecord == 0 || paginationInfo) {

				StringBuffer jpqlCountSb = new StringBuffer();
				jpqlCountSb.append("Select count(distinct e.id) from Employee e ");
				jpqlCountSb.append(whereSb);
				count = (Long) genaricCrudDAO.createSingleResultQuery(jpqlCountSb.toString(), jqlParameters,0, maxResults);
				returnObject.put("noOfRecords", count);
			}
			updateSerializationMixIns(objectMapper, true);
			System.out.println("Taking Employee details from the database :)");
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			objectMapper = null;
			returnObject = null;
			//list = null;
			queryParameters = null;
			jqlParameters = null;
			whereSb = null;
			System.out.println("API has been callled successfully");
		}
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed").build();
	}
	@GET
	@Path("/getAllDistricts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDistricts(@Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		List<District> districts = null;

		try {
			districts = genaricCrudDAO.createQuery("SELECT d FROM District d");
			returnObject.put("districts", districts);
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}

	@GET
	@Path("/userById/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") Long id, @Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();

		try {
			returnObject.put("User", getSymptom(id.intValue()));
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}
	private Employee getSymptom(int i) {
		
		List<Employee> list = null;
		Map<String,Object> parameters = new HashMap<String,Object>();
		Employee employee = null;

		try {
			
	    		parameters.put("id", i);
	    		list =  genaricCrudDAO.createQuery(	"SELECT e FROM Employee e " + "WHERE e.id=:id",parameters);

	    		if (list.size()>0) {
	    			employee = list.get(0);
	    		}
			
		} catch (Exception e) {
		} finally {
			parameters = null;
			list = null;
		}
		
		return employee;
	}

	@POST
	@Path("/saveUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveUser(Employee employee, @Context SecurityContext sc, @Context UriInfo uriInfo)
			throws JsonProcessingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> returnObject = new HashMap<String, Object>();
			EntityManager em = genaricCrudDAO.getEntityManager();
			//MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Employee.insertEmployee");
			procedureQuery.setParameter("id", employee.getId());
			procedureQuery.setParameter("first_name", employee.getFirst_name());
			procedureQuery.setParameter("last_name", employee.getLast_name());
			procedureQuery.setParameter("gender", employee.getGender());
			procedureQuery.setParameter("date_to", employee.getDate_to());
			procedureQuery.setParameter("date_from", employee.getDate_from());
			procedureQuery.setParameter("dob", employee.getDob());
			procedureQuery.setParameter("contact_no", employee.getContact_no());
			procedureQuery.setParameter("Address", employee.getAddress());
			procedureQuery.setParameter("district", employee.getDistrict());
			procedureQuery.setParameter("skills", employee.getSkills());
			procedureQuery.execute();
//			@SuppressWarnings("unchecked")
//			List<Employee> resultList = procedureQuery.getResultList();
			genaricCrudDAO.update(procedureQuery);
			returnObject.put("status",employee);
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}

	@POST
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(Employee updateUser, @Context SecurityContext sc, @Context UriInfo uriInfo)
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		List<Employee> users = null;
		
		try {
			users = genaricCrudDAO.createQuery("SELECT u FROM Employee u WHERE id = " + updateUser.getId());
			if (users.size() > 0) {
				genaricCrudDAO.update(updateUser);
				returnObject.put("status", "Updated the user");
			} else {
				returnObject.put("status", "Finding user failed");
			}
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Connection or database configurations failed")
					.build();
		}
	}

	@DELETE
	@Path("/deleteUserById/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserById(@PathParam("id") int id, @Context SecurityContext context, @Context UriInfo uriInfo,
			@Context HttpHeaders headers) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> returnObject = new HashMap<String, Object>();
		List<Employee> users = null;
		Employee user = null;

		try {
			users = genaricCrudDAO.createQuery("SELECT u FROM Employee u WHERE id = " + id);
			if (users.size() > 0) {
				user = users.get(0);
				genaricCrudDAO.delete(user, user.getId());
			}
			returnObject.put("status", "Success");
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();
		} catch (Exception e) {
			e.printStackTrace();
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
