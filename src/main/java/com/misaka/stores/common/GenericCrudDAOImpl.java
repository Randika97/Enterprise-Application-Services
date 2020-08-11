package com.misaka.stores.common;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



@Stateless
@Local(GenericCrudDAOIF.class)
public class GenericCrudDAOImpl implements GenericCrudDAOIF {
	
	

	@PersistenceContext
	EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public <T> T create(T t) throws DataAccessException {
		this.em.persist(t);
		this.em.flush();
		this.em.refresh(t);
		return t;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> T find(Object id, Class<T> type) throws DataAccessException {
		return (T) this.em.find(type, id);
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	
	public <T> T update(T t) throws DataAccessException {
		return (T) this.em.merge(t);
	}

	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public void delete(Object t, Object id) throws DataAccessException {
		Object ref = this.em.getReference(t.getClass(), id);
		this.em.remove(ref);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> findByNamedQuery(String namedQueryName) throws DataAccessException {
		return em.createNamedQuery(namedQueryName).getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	
	public <T,U> List<T> findByNativeQuery(String nativeQueryName,Class<U> resultClass) throws DataAccessException {
		return this.em.createNativeQuery(nativeQueryName,resultClass).getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters) {
		return findByNamedQuery(namedQueryName, parameters, 0);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> findByNamedQuery(String queryName, int resultLimit) {
		return this.em.createNamedQuery(queryName).setMaxResults(resultLimit)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit) {
		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
		Query query = this.em.createNamedQuery(namedQueryName);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		for (Entry<String, Object> entry : rawParameters) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> createQuery(String queryString) {
		Map<String, Object> parameters = null;
		
		return createQuery(queryString,parameters,0);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> createQuery(String queryString,int resultLimit) {
		Map<String, Object> parameters = null;
		
		return createQuery(queryString,parameters,resultLimit);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> createQuery(String queryString,
			Map<String, Object> parameters) {
		return createQuery(queryString,parameters,0);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> createQuery(String queryString,
			Map<String, Object> parameters, int resultLimit) {
		Query query = this.em.createQuery(queryString);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
        if (parameters !=null) {
    		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
    		for (Entry<String, Object> entry : rawParameters) {
    			query.setParameter(entry.getKey(), entry.getValue());
    		}
        }
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> List<T> createQuery(String queryString,
			Map<String, Object> parameters, int first, int resultLimit) {
		Query query = this.em.createQuery(queryString);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		if (first > 0)
			query.setFirstResult(first);
		if (parameters !=null) {
    		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
    		for (Entry<String, Object> entry : rawParameters) {
    			query.setParameter(entry.getKey(), entry.getValue());
    		}
        }
		return query.getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> Object createSingleResultQuery(String queryString,
			Map<String, Object> parameters, int first, int resultLimit) {
		Query query = this.em.createQuery(queryString);
		if (resultLimit > 0)
			query.setMaxResults(resultLimit);
		if (first > 0)
			query.setFirstResult(first);
		if (parameters !=null) {
    		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
    		for (Entry<String, Object> entry : rawParameters) {
    			query.setParameter(entry.getKey(), entry.getValue());
    		}
        }
		return query.getSingleResult();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public <T> Long getCount(Class<T> entityClass,Predicate[] condition) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(condition);
        return em.createQuery(criteriaQuery).getSingleResult();
    }
	
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public int createExecuteUpdateQuery(String queryString,
			Map<String, Object> parameters) {
		Query query = this.em.createQuery(queryString);
		if (parameters !=null) {
    		Set<Entry<String, Object>> rawParameters = parameters.entrySet();
    		for (Entry<String, Object> entry : rawParameters) {
    			query.setParameter(entry.getKey(), entry.getValue());
    		}
        }
		return query.executeUpdate();
	}

	public EntityManager getEntityManager() {
		return em;
		
	}
}

