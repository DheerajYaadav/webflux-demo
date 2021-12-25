package com.reactive.webflux.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.reactive.webflux.model.Employee;
import com.reactive.webflux.model.ProductDto;
import com.reactive.webflux.repository.EmployeeRepository;
import com.reactive.webflux.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private WebClient webclient;

	@Override
	public void create(Employee employee) {
		employeeRepository.save(employee).subscribe();
	}

	@Override
	public Mono<Employee> findById(Integer id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Flux<Employee> findByName(String Name) {
		return employeeRepository.findByName(Name).log();
	}

	@Override
	public Flux<Employee> findAll() {
		Flux<Employee> fluxEmployee = employeeRepository.findAll();
		return fluxEmployee;
	}

	@Override
	public Mono<Employee> update(Employee emp) {
		return employeeRepository.save(emp);
	}

	@Override
	public Mono<Void> deleteById(Integer id) {
		return employeeRepository.deleteById(id);
	}

	@Override
	public Flux<ProductDto> findAllProducts() {

		ResponseSpec res = webclient.get().uri("/all").retrieve();
		Flux<ProductDto> pr = res.bodyToFlux(ProductDto.class);
		return pr;
	}

}
