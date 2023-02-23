package com.servletapp.utility;

import java.util.List;

public interface DAO<T> {
	List<T> getAll();
	T getOne(String email);
	int save(T obj);
	int update(T obj);
	int delete(String email);
}
