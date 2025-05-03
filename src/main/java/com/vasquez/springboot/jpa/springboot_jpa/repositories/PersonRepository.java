package com.vasquez.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.vasquez.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.vasquez.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person,Long> {

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguageAndName(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);
    
    @Query("select p.name, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    //------------------------------------------------------------------------------

    @Query("select p.name from Person p where p.id=?1")
    String getNameById(Long id);

    @Query("select concat(p.name, ' ',p.lastname) as fullname from Person p where p.id=?1")
    String getFullNameById(Long id);

    //----------------------------------------------------------------------------------

    @Query("select new com.vasquez.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    //----------------------------------------------------------------------------------

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinc();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinc();

    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    List<String> findAllProgrammingLanguageDistincCount();

    //----------------------------------------------------------------------------------
    @Query("select p from Person p where p.id between ?1 and ?2 order by p.id")
    List<Person> findAllBetweenId(Long c1, Long c2);

    @Query("select p from Person p where p.name between ?1 and ?2 order by p.name desc, p.lastname asc")
    List<Person> findAllBetweenName(String c1, String c2);  

    List<Person> findByIdBetweenOrderByIdDesc(Long id1, Integer id2);

    List<Person> findByNameBetweenOrderByNameAsc(String name1, String name2);

    List<Person> findByNameBetweenOrderByNameAscLastnameDesc(String name1, String name2);

    //----------------------------------------------------------------------------------
    List<Person> findAllByOrderByNameDesc();

    @Query("select p from Person p order by p.name, p.lastname desc")
    List<Person> getAllOrdered();

    //----------------------------------------------------------------------------------
    @Query("select count(p)from Person p")
    Long totalPerson();

    @Query("select min(p.id)from Person p")
    Long minId();

    @Query("select max(p.id)from Person p")
    Long maxId();
}
