package com.vasquez.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vasquez.springboot.jpa.springboot_jpa.dto.PersonDto;
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
		personalizedQueriesDistinc();
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
	public void list() {
		//List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = repository.findByProgrammingLanguage("Java");
		List<Person> persons = repository.buscarByProgrammingLanguageAndName("Java", "Renzo");

		List<Object[]> personsValues = repository.obtenerPersonData();
		persons.forEach(p-> System.out.println(p));
		personsValues.forEach(p-> System.out.println(p[0] + " es experto en: " + p[1]));
	}

	@Transactional
	public void create() {
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

	@Transactional
	public void update() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona:");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresent(person -> {
			System.out.println("Ingrese el lenguaje de programacion: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person personUpdated = repository.save(person);
			System.out.println(personUpdated);
		});

		scanner.close();
	}

	@Transactional
	public void delete() {
		repository.findAll().forEach(System.out::println);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();

		repository.deleteById(id);
		repository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void delete2() {
		repository.findAll().forEach(System.out::println);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona a eliminar: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(person -> repository.delete(person), 
		() -> System.out.println("Lo sentimos no existe la persona con ese id!"));
		repository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional(readOnly = true)
	public void personalizedQueries() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("============== consulta solo el nombre por el id ================");
		System.out.println("Ingresa el id: ");
		Long id = scanner.nextLong();
		scanner.close();

		String name = repository.getNameById(id);
		System.out.println(name);

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("============== consulta ================");
		System.out.println("Ingresa el id: ");
		Long id = scanner.nextLong();
		scanner.close();

		String name = repository.getNameById(id);
		System.out.println(name);

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		List<PersonDto> personitas = repository.findAllPersonDto();
		personitas.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinc() {
		System.out.println("=========consultas con nombres de personas===========");
		List <String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("=========consultas con nombres unicos de personas===========");
		names = repository.findAllNamesDistinc();
		names.forEach(System.out::println);

		System.out.println("=========consultas con lenguajes de programacion unicos===========");
		List<String> languages = repository.findAllProgrammingLanguageDistinc();
		languages.forEach(System.out::println);

		System.out.println("=========cantidad de lenguajes de programacion unicos===========");
		List<String> count = repository.findAllProgrammingLanguageDistincCount();
		System.out.println(count);
	}

}
