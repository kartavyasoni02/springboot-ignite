package com.teacheron.sales.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.teacheron.sales.dao.GenericDAO;
import com.teacheron.sales.exceptions.ValidationException;
import com.teacheron.sales.service.GenericService;

public abstract class GenericServiceImpl<T> implements GenericService<T> {
	/**
	 * gets the baseDAO
	 */
	public abstract GenericDAO<T, Integer> getBaseDAO();

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager", rollbackFor = Throwable.class)
	public Integer create(T entity) throws ValidationException {
		return getBaseDAO().create(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager", rollbackFor = Throwable.class)
	public void update(T entity) throws ValidationException {
		getBaseDAO().merge(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager", rollbackFor = Throwable.class)
	public void delete(T entity) {
		getBaseDAO().delete(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager", rollbackFor = Throwable.class)
	public void delete(Integer id) {
		T entity = getBaseDAO().load(id);
		getBaseDAO().delete(entity);
	}
	
	@Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager",rollbackFor = Throwable.class)
    public void deleteAll(List<Integer> ids) {
        getBaseDAO().deleteAll(ids);
    }

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T findById(Integer id) {

		return getBaseDAO().findById(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> findAll() {
		return getBaseDAO().findAll();
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public <T> List<T> findAll(List<Integer> ids) {
        return getBaseDAO().findAll(ids);
    }

}
