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
import com.misaka.stores.model.Temp;

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
		EntityManager em = genaricCrudDAO.getEntityManager();
		try {
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("Temp.GetTempDetails");
			procedureQuery.execute();	
			@SuppressWarnings("unchecked")
			List<Temp> resultList = procedureQuery.getResultList();
			//list = genaricCrudDAO.createQuery("SELECT e FROM Employee e " + whereSb.toString() + " order by e.first_name ASC", jqlParameters,firstRecord, maxResults);
			returnObject.put("temp", resultList);
			updateSerializationMixIns(objectMapper, true);
			System.out.println("Taking Tempory details from the database :)");
			return Response.status(Response.Status.OK).entity(objectMapper.writeValueAsString(returnObject)).build();

		} catch (Exception e) {
			System.out.println(e);
		} finally {
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
