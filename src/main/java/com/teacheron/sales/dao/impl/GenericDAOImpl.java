package com.teacheron.sales.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.teacheron.sales.dao.GenericDAO;
import com.teacheron.sales.enumerations.ErrorCodeEnum;
import com.teacheron.sales.exceptions.TeacheronException;

/**
 * A base interface for all DAOs. This defines the common methods present in all DAOs. All other DAOs must extend from this DAO.
 * 
 * @param <E>
 *            Represents the generic Entity class for which the DAO needs to be returned.
 * @param <PK>
 *            Represents the type of primary Key class inside the database.
 * 
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenericDAOImpl<E, PK extends java.io.Serializable> implements GenericDAO<E, PK> {

	/**
	 * Represents the hibernate session factory object.
	 */
	protected SessionFactory sessionFactory;

	/**
	 * Represents the entity class for which the DAO has been written.
	 */
	private final Class<? extends E> _entityClass;

	/**
	 * Represents the constructor of the DAO object.
	 * 
	 * @param entityClass
	 *            represents the calss for which DAO needs to be created.
	 */
	public GenericDAOImpl(final Class<? extends E> entityClass) {
		super();
		_entityClass = entityClass;
	}

	/**
	 * Method returns the current Hibernate session for the thread.
	 * 
	 * @return Session Represents the Hibernate session.
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	protected Session getCrntSession() throws TeacheronException {
		try {
			return sessionFactory.getCurrentSession();
		} catch (final HibernateException e) {
			e.printStackTrace();
			throw new TeacheronException("Failed to get thread session ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

	/**
	 * Setter for the session factory.
	 * 
	 * @param sessionFactory
	 *            Represents the hibernate session factory object.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

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
	@Override
	public PK create(final E newInstance) throws TeacheronException {
		PK id = null;
		final Session session = getCrntSession();
		try {
			id = (PK) session.save(newInstance);
			getCrntSession().flush();
		} catch (final HibernateException e) {
			e.printStackTrace();
			throw new TeacheronException("Failed to insert  " + newInstance, ErrorCodeEnum.BASE_DB_ERROR, e);
		}
		return id;
	}

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
	@Override
	public void update(final E entity) throws TeacheronException {
		try {
			getCrntSession().merge(entity);
			getCrntSession().flush();
		} catch (final HibernateException e) {
			e.printStackTrace();
			throw new TeacheronException("Exception while updating entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

	/**
	 * This method is used to merge the entity into database.
	 * 
	 * @param entity
	 *            Represents the instance to be updated.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	@Override
	public void merge(final E entity) throws TeacheronException {
		try {
			getCrntSession().merge(entity);
			getCrntSession().flush();
		} catch (final ConstraintViolationException e) {
			throw new TeacheronException(e.getMessage(), ErrorCodeEnum.BASE_DB_ERROR);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while updating entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

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
	@Override
	public E get(final PK id) throws TeacheronException {
		try {
			return (E) getCrntSession().get(_entityClass, id);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while getting entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

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
	@Override
	public E load(final PK id) throws TeacheronException {
		try {
			return (E) getCrntSession().load(_entityClass, id);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while loading entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

	/**
	 * This method is used to return the criteria object for the class describes in the class signature E..
	 * 
	 * @return Represents the generic criteria object.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	@Override
	public Criteria getCriteria() throws TeacheronException {
		try {
			return getCrntSession().createCriteria(_entityClass);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while creating criteria ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

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
	@Override
	public Criteria getCriteria(final Class entityclass) throws TeacheronException {
		try {
			return getCrntSession().createCriteria(entityclass);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while getting criteria ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

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
	@Override
	public <V> List<V> executeCriteria(final Criteria criteria) throws TeacheronException {
		try {
			return criteria.list();
		} catch (final HibernateException e) {
			e.printStackTrace();
			throw new TeacheronException("Exception while executing criteria " + criteria, ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

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
	@Override
	public <V extends Object> V loadObject(final Class classs, final PK id) throws TeacheronException {
		try {
			return (V) getCrntSession().load(classs, id);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while load entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

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
	@Override
	public <V extends Object> V getObject(final Class classs, final PK id) throws TeacheronException {
		try {
			return (V) getCrntSession().get(classs, id);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while get entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

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
	@SuppressWarnings("deprecation")
	@Override
	public <V extends Object> V getEntityWithAssociations(Class entityClass, PK entityId, String... entityAssociations)
			throws TeacheronException {

		V dataObject = null;
		final Criteria criteria = getCriteria(entityClass);
		try {
			for (final String associationName : entityAssociations) {
				criteria.createAlias(associationName, associationName, CriteriaSpecification.LEFT_JOIN);
			}
			criteria.add(Restrictions.eq("id", entityId));
			final List<V> entityList = executeCriteria(criteria);
			if (entityList.size() > 0) {
				dataObject = entityList.get(0);
			}
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while executing getEntityWithAssociations " + criteria,
					ErrorCodeEnum.BASE_DB_ERROR, e);
		}
		return dataObject;
	}

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
	@Override
	public Query getQueryObject(final String hql) throws TeacheronException {
		try {
			final Query query = getCrntSession().createQuery(hql);
			return query;
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while creating query object " + hql, ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

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
	@Override
	public <V> List<V> executeHQLSelectQuery(final Query query) throws TeacheronException {
		try {
			return query.list();
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while executing hq query  " + query, ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

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
	@Override
	public <V> List<V> executeHQLSelectQuery(final String query) throws TeacheronException {
		try {
			NativeQuery sqlQuery = getCrntSession().createSQLQuery(query);
			return sqlQuery.list();
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while executing hq query  " + query, ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}
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
	@Override
	public int executeHQLDMLQuery(final Query query) throws TeacheronException {
		try {
			return query.executeUpdate();
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while executing hq query  " + query, ErrorCodeEnum.BASE_DB_ERROR, e);
		}

	}

	/**
	 * This method is used to create Entity.
	 * 
	 * @param newInstance
	 *            Represents newInstance.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	@Override
	public PK saveEntity(Object newInstance) throws TeacheronException {
		PK id = null;
		final Session session = getCrntSession();
		try {
			id = (PK) session.save(newInstance);
		} catch (final HibernateException e) {
			throw new TeacheronException("Failed to insert  " + newInstance + " successfully", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
		return id;
	}

	/**
	 * This method is used to update Entity.
	 * 
	 * @param newInstance
	 *            Represents newInstance.
	 * 
	 * @throws TeacheronException
	 *             throws in case of query or DB Error.
	 */
	@Override
	public void updateEntity(Object newInstance) throws TeacheronException {
		try {
			getCrntSession().update(newInstance);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while refresh entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("hiding")
	@Override
	public <E> List<E> findAll() throws TeacheronException {
		final Criteria criteria = getCriteria(_entityClass);
		final List<E> list = executeCriteria(criteria);
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(E newInstance) throws TeacheronException {
		try {
			getCrntSession().delete(newInstance);
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while deleting entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> T findById(Integer id) {
		final Criteria criteria = getCriteria(_entityClass);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("hiding")
	@Override
	public <E> List<E> findAll(List<Integer> ids) {
		if (ids != null && ids.size() > 0) {
			final Criteria criteria = getCriteria(_entityClass);
			criteria.add(Restrictions.in("id", ids));
			return criteria.list();
		} else {
			return new ArrayList<E>();
		}
	}

	@Override
	public void deleteAll(List<Integer> ids) throws TeacheronException {
		try {
			final Session session = getCrntSession();
			for (final Integer id : ids) {
				final Object object = session.load(_entityClass, id);
				session.delete(object);
			}
		} catch (final HibernateException e) {
			throw new TeacheronException("Exception while deleting entity ", ErrorCodeEnum.BASE_DB_ERROR, e);
		}
	}
	
	@Override
	public E checkDuplicate(String column, String value, Integer id)
			throws TeacheronException {
		// TODO Auto-generated method stub
		return null;
	}

}
