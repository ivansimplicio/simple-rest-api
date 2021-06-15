package com.dev.service.interfaces;

import java.util.List;

public interface StandardCRUDOperations<T> {
	
	T find(Integer id);
	
	List<T> findAll();
	
	T insert(T obj);
	
	T update(T obj);
	
	void delete(Integer id);

}