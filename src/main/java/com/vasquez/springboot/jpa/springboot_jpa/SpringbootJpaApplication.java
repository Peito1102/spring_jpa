package com.vasquez.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vasquez.springboot.jpa.springboot_jpa.entities.Person;
import com.vasquez.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		list();
		findOne();
		create();
	}

	@Transactional(readOnly = true)
	private void findOne() {
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(1L);
		// if (optionalPerson.isPresent()) {
		// 	person = optionalPerson.get();	
		// }
		// System.out.println(person);

		//repository.findById(1L).ifPresent(person -> System.out.println(person));
		//repository.findOneName("zo").ifPresent(person -> System.out.println(person));
		//repository.findOneLikeName("zo").ifPresent(person -> System.out.println(person));
		repository.findByNameContaining("to").ifPresent(person -> System.out.println(person));
		System.out.println("gaaa");
	}

	@Transactional(readOnly = true)
	private void list() {
		//List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = repository.findByProgrammingLanguage("Java");
		List<Person> persons = repository.buscarByProgrammingLanguageAndName("Java", "Renzo");

		List<Object[]> personsValues = repository.obtenerPersonData();
		persons.forEach(p-> System.out.println(p));
		personsValues.forEach(p-> System.out.println(p[0] + " es experto en: " + p[1]));
	}

	private void create() {
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		String lastname = scanner.next();
		String programmingLanguage = scanner.next();
		Person person = new Person(null, name, lastname, programmingLanguage);
		scanner.close();

		Person personNew = repository.save(person);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(p -> System.out.println(p));
	}

}
