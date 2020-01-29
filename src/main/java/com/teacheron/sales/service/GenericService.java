package com.teacheron.sales.service;

import java.util.List;

import com.teacheron.sales.exceptions.TeacheronException;

public interface GenericService<T> {

	/**
	 * creates Entity
	 * 
	 * @param T
	 *            the entity to create
	 * @return id the unique identifier of Entity
	 * @throws TeacheronException
	 */
	public Integer create(T entity) throws TeacheronException;

	/**
	 * updates Entity
	 * 
	 * @param T
	 *            the entity to update
	 * @throws TeacheronException
	 */
	void update(T entity) throws TeacheronException;

	/**
	 * deleted Entity for given id
	 * 
	 * @param id
	 *            the unique identifier of Entity
	 * @throws TeacheronException
	 */
	void delete(T entity) throws TeacheronException;

	/**
	 * deleted Entity for given id
	 * 
	 * @param id
	 *            the unique identifier of Entity
	 * @throws TeacheronException
	 */
	void delete(Integer id) throws TeacheronException;

	/**
	 * find Entity by id
	 * 
	 * @param id
	 *            the unique identifier of Entity
	 * @return T the entity
	 * @throws TeacheronException
	 */
	public T findById(Integer id) throws TeacheronException;
	 
	/**
	 * find all the entiies
	 * 
	 * @return T the entity
	 * @throws TeacheronException
	 */
	public List<T> findAll() throws TeacheronException;

    public <T> List<T> findAll(List<Integer> ids) throws TeacheronException;

    public void deleteAll(List<Integer> ids) throws TeacheronException;
}
