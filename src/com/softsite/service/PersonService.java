package com.softsite.service;

import java.util.List;

import com.softsite.model.Person;
import com.softsite.repository.PersonRepository;

public class PersonService {

	private PersonRepository personRepository;
	
	public PersonService(){
		this.personRepository = new PersonRepository();
	}
	
	public void salvarPessoa(Person person){
		personRepository.inserirPessoa(person);
	}
	
	public Boolean removerPessoa(String cpf){
		return personRepository.removerPessoa(cpf);
	}
	
	public List<Person> listarPessoas(){
		return personRepository.listarPessoas();
	}
	
	
}
