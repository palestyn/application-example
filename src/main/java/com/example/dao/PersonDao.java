package com.example.dao;

import javax.inject.Inject;
import javax.transaction.Transactional.TxType;

import org.palestyn.transaction.TransactionManager;

import com.example.domain.Person;

public class PersonDao {

	@Inject TransactionManager trx;
	
	public Person findById(Integer id) {		
		return trx.doWithEntityManager(TxType.REQUIRED, em -> {
			return em.find(Person.class, id);
		});
	}
}
