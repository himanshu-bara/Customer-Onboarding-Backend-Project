package com.oracle.customer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import com.oracle.customer.model.Account;

@Repository
@Transactional
public class AccountDAOImpl implements AccountDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Account saveAccount(Account account) {
        entityManager.persist(account);
        return account;
    }

    @Override
    public Account findByCustomerId(String customerId) {
        return entityManager.createQuery("SELECT a FROM Account a WHERE a.customerId = :cid", Account.class)
                .setParameter("cid", customerId)
                .getSingleResult();
    }
}
