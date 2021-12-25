package com.reactive.webflux.service;

import com.reactive.webflux.model.Employee;
import com.reactive.webflux.model.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
	
	public void create(Employee employee);
	
	public Mono<Employee> findById(Integer id);
	
	public Flux<Employee> findByName(String Name);
	
	public Flux<Employee> findAll();
	
	public Mono<Employee> update(Employee emp);
	
	public Mono<Void> deleteById(Integer id);
	
	public Flux<ProductDto> findAllProducts(); 
}
