package com.misaka.stores.rest;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.misaka.stores.common.GenericCrudDAOIF;
import com.misaka.stores.model.Employee;

@Stateless
@Path("/{versionID}/tempDetails/")
public class Tempory {
	static Session sessionObj;
	static SessionFactory sessionFactoryObj;
	@EJB
	GenericCrudDAOIF genaricCrudDAO;
	@GET
	@Path("/getTemp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProducts(@Context SecurityContext sc, @Context UriInfo uriInfo) throws ParseException {

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
			//StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Temp.GetTempDetails");
			//procedureQuery.execute();	
			//@SuppressWarnings("unchecked")
			//List<Employee> resultList = procedureQuery.getResultList();
			//list = genaricCrudDAO.createQuery("SELECT e FROM Employee e " + whereSb.toString() + " order by e.first_name ASC", jqlParameters,firstRecord, maxResults);
			//returnObject.put("list", resultList);
		
			if (firstRecord == 0 || paginationInfo) {

				StringBuffer jpqlCountSb = new StringBuffer();
				jpqlCountSb.append("Select count(distinct e.id) from Temp e ");
				jpqlCountSb.append(whereSb);
				count = (Long) genaricCrudDAO.createSingleResultQuery(jpqlCountSb.toString(), jqlParameters,0, maxResults);
				returnObject.put("noOfRecords", count);
			}
			updateSerializationMixIns(objectMapper, true);
			System.out.println("Taking Tempory details from the database :)");
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
	private void updateSerializationMixIns(ObjectMapper objectMapper, boolean isQuestionTemplateList) {
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
	}

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private abstract class IgnoreHibernatePropertiesInJackson {
	}

}
