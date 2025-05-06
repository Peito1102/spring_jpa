package com.vasquez.springboot.jpa.springboot_jpa;

import java.util.Arrays;
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
		whereIn();
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

	@Transactional(readOnly = true)
	public void personalizedQueriesBetween() {
		System.out.println("=========consultas por rangos===========");
		List<Person> persons = repository.findAllBetweenId(2L, 4L);
		persons.forEach(System.out::println);

		persons = repository.findAllBetweenName("J", "P");
		persons.forEach(System.out::println);

		persons = repository.findAllByOrderByNameDesc();
		persons.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void queriesFunctionAgregation() {
		System.out.println("=========consultas con el total de registros de la tabla persona===========");
		Long count = repository.totalPerson();
		Long min = repository.minId();
		Long max = repository.maxId();

		System.out.println("count:" + count);
		System.out.println("minimo:" + min);
		System.out.println("maximo:" + max);

		System.out.println("cosulta con el nombre y su largo");
		List<Object[]> regs = repository.getPersonNameLength();
		regs.forEach(reg -> {
			String name = (String) reg[0]; 
			Integer length = (Integer) reg[1];
			System.out.println("name= " + name + ", length= " + length);
		});

		Integer maximo = repository.getMaxNameLength();
		Integer minimo = repository.getMinNameLength();

		System.out.println("Nombre de mayor tamaño " + maximo);
		System.out.println("Nombre de mayor tamaño " + minimo);

		System.out.println("================resumen de funcione de agregacion===========");
		Object[] resumenReg = (Object[])repository.getResumeAggregationFunction();
		System.out.println("min: " + resumenReg[0] + ",max: " + resumenReg[1] + 
		",sum: " + resumenReg[2] + ",avg: " + resumenReg[3] + ",count: " + resumenReg[4]);


	}

	@Transactional(readOnly = true)
	public void subQueries() {
		System.out.println("========consulta por el nombre mas corto y su largo =======================");
		List<Object[]> registers = repository.getShorterName();
		registers.forEach(reg -> {
			String name = (String) reg[0];
			Integer length = (Integer) reg[1];
			System.out.println("name = " + name + ", length = " + length);
		});

		System.out.println("========consulta para obtener el ultimo registro de persona =======================");
		Optional<Person> optionalPerson = repository.getLastRegistration();
		optionalPerson.ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void whereIn() {
		System.out.println("========consulta where in =======================");
		List<Person> persons = repository.getPersonsByIds();
		persons.forEach(System.out::println);

		System.out.println("========consulta where in otra forma =======================");
		List<Person> persons2 = repository.getPersonsByIds2(Arrays.asList(1L,2L,4L));
		persons2.forEach(System.out::println);
	}

}
