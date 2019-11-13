package uo.ri.business.impl;

import uo.ri.business.exception.BusinessException;

public interface Command<T> {

	T execute() throws BusinessException;
}
