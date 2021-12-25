package com.reactive.webflux.restcontroller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.webflux.model.Employee;
import com.reactive.webflux.model.ProductDto;
import com.reactive.webflux.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Employee emp) {

		employeeService.create(emp);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Mono<Employee>> findById(@PathVariable Integer id) {

		Mono<Employee> employee = employeeService.findById(id).log();
		HttpStatus status = employee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Mono<Employee>>(employee, status);
	}

	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Flux<Employee>> findByName(@PathVariable String name) {

		Flux<Employee> employee = employeeService.findByName(name);
		HttpStatus status = employee != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Flux<Employee>>(employee, status);
	}

	@GetMapping(value = "/names", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Employee> findAll() {

		Flux<Employee> response = employeeService.findAll();
		response.delayElements(Duration.ofSeconds(3));

		return response;

	}

	@GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDto> findAllProducts() {

		return employeeService.findAllProducts();

	}

	@PutMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Employee> update(@RequestBody Employee e) {
		return employeeService.update(e).log();
	}

	@DeleteMapping(value = "/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Integer id) {
		employeeService.deleteById(id).subscribe();
	}

}
