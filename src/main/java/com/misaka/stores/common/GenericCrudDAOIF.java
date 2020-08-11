package com.misaka.stores.common;


import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;


public interface GenericCrudDAOIF {

	<T> T create(T t) throws DataAccessException;

	<T> T find(Object id, Class<T> type) throws DataAccessException;

	<T> T update(T t) throws DataAccessException;

	void delete(Object t, Object id) throws DataAccessException;

	<T, U> List<T> findByNativeQuery(String queryName, Class<U> resultClass)
			throws DataAccessException;

	<T> List<T> findByNamedQuery(String queryName) throws DataAccessException;

	<T> List<T> findByNamedQuery(String queryName, int resultLimit);

	<T> List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters);

	<T> List<T> findByNamedQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit);
	
	<T> List<T> createQuery(String queryName) throws DataAccessException;

	<T> List<T> createQuery(String queryName, int resultLimit);

	<T> List<T> createQuery(String namedQueryName,
			Map<String, Object> parameters);

	<T> List<T> createQuery(String namedQueryName,
			Map<String, Object> parameters, int resultLimit);
	
	<T> List<T> createQuery(String namedQueryName,
			Map<String, Object> parameters,int first, int resultLimit);
		
	<T> Object createSingleResultQuery(String queryString,
			Map<String, Object> parameters, int first, int resultLimit);
	
    public <T> Long getCount(Class<T> entityClass,Predicate[] condition);

	public int createExecuteUpdateQuery(String queryString,Map<String, Object> parameters);

    public EntityManager getEntityManager();
}