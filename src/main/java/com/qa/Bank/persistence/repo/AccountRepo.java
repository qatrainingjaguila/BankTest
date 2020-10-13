package com.qa.Bank.persistence.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Bank.persistence.domain.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

	List<Account> findAccountByAccountNumber(int accountNumber);

}
