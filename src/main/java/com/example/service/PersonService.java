package com.example.service;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.palestyn.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.domain.Person;

public class PersonService {

	Logger logger = LoggerFactory.getLogger(PersonService.class);

	@Inject TransactionManager trx;
	
	public Person create(String fullName) {

		final Person person = new Person();
		person.setFullName(fullName);

		// 1st transaction
		try {
			trx.doWithEntityManager(Transactional.TxType.REQUIRED, em -> {
				em.persist(person);
			});
		} catch(PersistenceException e) {
			logger.info("Exception", e);
		}
		
		Person output = null;

		//2nd transaction
		//will throw java.sql.SQLIntegrityConstraintViolationException: Duplicate entry
		try {			
			Person person2 = new Person();
			person2.setFullName(fullName); 
			output = trx.<Person>doWithEntityManager(Transactional.TxType.REQUIRED, em -> {
				em.persist(person2);
				return person2;
			});
		} catch(PersistenceException e) {
			logger.info("Exception", e);
		}
		
		// 3rd transaction
		try {			
			Person person2 = new Person();
			person2.setFullName(fullName+"."); 
			output = trx.<Person>doWithEntityManager(Transactional.TxType.REQUIRED, em -> {
				em.persist(person2);
				return person2;
			});
		} catch(PersistenceException e) {
			logger.info("Exception", e);
		}
		
		return output;
	}
}
