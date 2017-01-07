package com.softsite.service;

import java.util.List;

import com.softsite.models.Person;
import com.softsite.repository.PersonRepository;

public class PersonService {

	private PersonRepository personRepository;
	
	public PersonService(){
		this.personRepository = new PersonRepository();
	}
	
	public void salvarPessoa(Person person){
		personRepository.inserirPessoa(person);
	}
	
	public List<Person> listarPessoas(){
		return personRepository.listarPessoas();
	}
	
	
}
