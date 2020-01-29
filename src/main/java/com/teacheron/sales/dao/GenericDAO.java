package com.teacheron.sales.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.query.Query;

import com.teacheron.sales.exceptions.TeacheronException;

/**
 * A base interface for all DAOs. This defines the common methods present in all DAOs. All other DAOs must extend from this DAO.
 * 
 * 
 * @param <E>
 *            Represents the generic Entity class for which the DAO needs to be returned.
 * @param <PK>
 *            Represents the type of primary Key class inside the database.
 * 
 */
@SuppressWarnings({ "rawtypes" })
public interface GenericDAO<E, PK extends Serializable> {
	/**
	 * This method is used to save entity into the database
	 * 
	 * @param newInstance
	 *            Represents the instance to be saved.
	 * 
	 * @return PK Represents the primary key of the entity
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	public PK create(E newInstance) throws TeacheronException;

	/**
	 * This method is used to update the entity into database.
	 * 
	 * @param entity
	 *            Represents the instance to be updated.
	 * 
	 * @see org.hibernate.Session#saveOrUpdate(java.lang.Object)
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	void update(E entity) throws TeacheronException;

	/**
	 * This method is used to merge the entity into database.
	 * 
	 * @param entity
	 *            Represents the instance to be updated.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	public void merge(final E entity) throws TeacheronException;

	/**
	 * This method is used to get the particular row from database and its columns will mapped to the object describes in the class
	 * signature E.
	 * 
	 * @param id
	 *            Represents the primary key value for which objects needs to be fetched.
	 * 
	 * @return entity Represents the mapped obejct from database row.
	 * 
	 * @see org.hibernate.Session#get(java.lang.Class, java.io.Serializable)
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	E get(PK id) throws TeacheronException;

	/**
	 * This method is used to load the proxy for the object describes in the class signature E. This method never hit the database until the
	 * property (except id property) of that object() is accessed by getter.
	 * 
	 * @param id
	 *            Represents the id of the object for which the proxy needs to be load.
	 * 
	 * @return entity Represents the proxy object.
	 * 
	 * @see org.hibernate.Session#load(java.lang.Class, java.io.Serializable)
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	E load(PK id) throws TeacheronException;

	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 
	 * @return Represents the generic criteria object.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	Criteria getCriteria() throws TeacheronException;

	/**
	 * This method is used to get the Generic Criteria object.
	 * 
	 * @param entityclass
	 *            Represents the class for which Criteria object needs be generated.
	 * 
	 * @return Represents the generic criteria object.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	Criteria getCriteria(final Class entityclass) throws TeacheronException;

	/**
	 * This method is used to execute the query of the Criteria object.
	 * 
	 * @param criteria
	 *            Represents the criteria object which is going to execute the query.
	 * 
	 * @return List Represents the results return from the query.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	<V> List<V> executeCriteria(final Criteria criteria) throws TeacheronException;

	/**
	 * This method is used to load the proxy for the generic object.This method never hit the database until the property (except id
	 * property) of that object() is accessed by getter.
	 * 
	 * @param classs
	 *            Represents the class for which the proxy needs to be generated.
	 * @param id
	 *            Represents the id for which proxy needs to be generated.
	 * 
	 * @return V Represents the proxy object.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	<V extends Object> V loadObject(final Class entityClass, final PK id) throws TeacheronException;

	/**
	 * This method is used to load the generic object from the database.
	 * 
	 * @param classs
	 *            Represents the class for which the object needs to be fetched from database.
	 * @param id
	 *            Represents the id for which the object needs to be fetched from database.
	 * 
	 * @return V Represents the generic object.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	<V extends Object> V getObject(final Class entityClass, final PK id) throws TeacheronException;

	/**
	 * This method is used to get the generic object from database with all mentioned associations.
	 * 
	 * @param entityClass
	 *            Represents the Class for which the object needs to fetch from database.
	 * @param entityId
	 *            Represents the id for which the obejct needs to be fetch from database.
	 * @param entityAssociations
	 *            Represents the associations of the object needs to be load with the object.
	 * 
	 * @return V Represents the Object fetched from database.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	<V extends Object> V getEntityWithAssociations(Class entityClass, final PK entityId, final String... entityAssociations)
			throws TeacheronException;

	/**
	 * This method is used to get Query object for Hibernate Query.
	 * 
	 * @param hql
	 *            Represents the Hibernate Query for which the Query object needs to fetched.
	 * 
	 * @return Represents the Query object.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	Query getQueryObject(final String hql) throws TeacheronException;

	/**
	 * This method is used to get the results from the Hibernate DDL Query object.
	 * 
	 * @param query
	 *            Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return List Represents the result returns from the database.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	<V> List<V> executeHQLSelectQuery(final Query query) throws TeacheronException;



	/**
	 * This method is used to get the results from the SQl Query string.
	 * 
	 * @param query
	 *            Represents the sql query string for which the results needs to be fetched.
	 * 
	 * @return List Represents the result returns from the database.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	<V> List<V> executeHQLSelectQuery(String query) throws TeacheronException;

	/**
	 * This method is used to execute Hibernate DML Query object.
	 * 
	 * @param query
	 *            Represents the hibernate query object for which the results needs to be fetched.
	 * 
	 * @return represents number of rows inserted/updated or deleted.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	int executeHQLDMLQuery(final Query query) throws TeacheronException;

	/**
	 * 
	 * @param object
	 * @return id
	 * @throws TeacheronException
	 */
	public PK saveEntity(Object object) throws TeacheronException;

	/**
	 * 
	 * @param newInstance
	 * @throws TeacheronException
	 */
	public void updateEntity(Object newInstance) throws TeacheronException;

	/**
	 * 
	 * @param newInstance
	 * @throws TeacheronException
	 */
	@SuppressWarnings("hiding")
	public <E> List<E> findAll() throws TeacheronException;

	/**
	 * This method is used to delete Entity.
	 * 
	 * @param newInstance
	 * @throws TeacheronException
	 */
	public void delete(E newInstance) throws TeacheronException;

	/**
	 * @param <T>
	 * @param id
	 * @return
	 */
	public <T> T findById(Integer id);

	/**
	 * Load all the Instances
	 * 
	 * @param ids
	 *            Holds the Identifier of the instance
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <E> List<E> findAll(List<Integer> ids);

	/**
	 * This method is use to delete entity list
	 * 
	 * @param ids
	 *            List of ids to delete
	 * @throws TeacheronException
	 */
	public void deleteAll(List<Integer> ids) throws TeacheronException;
	
	/**
	 * This method is used to check duplicate value in database.
	 * @param column
	 * @param value
	 * @param id
	 * @return
	 * @throws TeacheronException
	 */
	public E checkDuplicate(String column, String value, Integer id) throws TeacheronException;
}
