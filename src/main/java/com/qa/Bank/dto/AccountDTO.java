package com.qa.Bank.dto;

import java.util.List;

import com.qa.Bank.persistence.domain.Payment;

public class AccountDTO {

	private Long id;

	private int accountNumber;

	private String firstName;

	private String lastName;

	private String pass;

	private Long balance;

	private List<Payment> payments;

	public AccountDTO() {
		super();
	}

	public AccountDTO(Long id, int accountNumber, String firstName, String lastName, Long balance, String pass) {
		super();
		this.id = id;
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
		this.pass = pass;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}
