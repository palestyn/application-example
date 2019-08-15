package com.example.service;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.palestyn.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.PersonDao;
import com.example.domain.Person;

public class PersonService {

	Logger logger = LoggerFactory.getLogger(PersonService.class);

	@Inject TransactionManager trx;
	@Inject PersonDao personDao;
	
	public Person create(String fullName) {

		Person person=null;

		// 1st transaction
		try {
			person = trx.doWithEntityManager(Transactional.TxType.REQUIRED, em -> {
				Person _person=new Person();
				_person.setFullName(fullName);
				em.persist(_person);
				return _person;
			});
		} catch(PersistenceException e) {
			logger.info("Exception", e);
		}
		
		// get above committed person using dao		
		Person fromDao = personDao.findById(person.getId());
		return fromDao;
	}
}
