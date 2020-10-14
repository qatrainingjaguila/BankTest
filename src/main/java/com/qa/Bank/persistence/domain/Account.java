package com.qa.Bank.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Payment> payments = new ArrayList<Payment>();

	public Account() {
		super();
	}

	public Account(String firstName, String lastName, Long balance, String pass) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.balance = balance;
		this.pass = pass;

	}

	@Column(name = "account_number")
	@NotNull
	private int accountNumber;

	@Override
	public String toString() {
		return "Account [id=" + id + ", payments=" + payments + ", accountNumber=" + accountNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", balance=" + balance + ", pass=" + pass + "]";
	}

	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Column(name = "last_name")
	private String lastName;

	@Column
	private Long balance;

	@NotNull
	@Column(name = "password")
	private String pass;

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
