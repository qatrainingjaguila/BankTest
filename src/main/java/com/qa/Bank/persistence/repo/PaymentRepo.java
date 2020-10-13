package com.qa.Bank.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.Bank.persistence.domain.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {

}
