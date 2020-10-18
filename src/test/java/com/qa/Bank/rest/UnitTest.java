package com.qa.Bank.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.Bank.dto.AccountDTO;
import com.qa.Bank.persistence.domain.Account;
import com.qa.Bank.service.TransactionService;

@SpringBootTest
public class UnitTest {

	@Autowired
	private AccountController controller;

	@MockBean
	private TransactionService service;

	private List<Account> accounts;

	private AccountDTO testAccount;

	private Account testAccountWithID;

	private AccountDTO AccountDTO;

	private final long id = 1L;

	private ModelMapper mapper = new ModelMapper();

	private AccountDTO mapToDTO(Account account) { // takes an account and maps to a dto obj
		return this.mapper.map(account, AccountDTO.class);
	}

	@BeforeEach
	void init() {
		this.accounts = new ArrayList<>();
		this.testAccount = this.mapToDTO(new Account("John", "Smith", 100L, "pass123"));

		this.testAccountWithID = new Account(testAccount.getFirstName(), testAccount.getLastName(),
				testAccount.getBalance(), testAccount.getPass());
		this.testAccountWithID.setId(testAccount.getId());
		this.testAccountWithID.setAccountNumber(testAccount.getAccountNumber());

		this.accounts.add(testAccountWithID);
		this.AccountDTO = this.mapToDTO(testAccountWithID);
	}

	@Test
	void createAccountTest() {
		when(this.service.createAccount(testAccount)).thenReturn(this.AccountDTO);

		assertThat(new ResponseEntity<AccountDTO>(this.AccountDTO, HttpStatus.CREATED))
				.isEqualTo(this.controller.createAccount(testAccount));

		verify(this.service, times(1)).createAccount(this.testAccount);
	}

	@Test
	void findAccountByIDTest() {
		when(this.service.findAccountByID(this.id)).thenReturn(this.AccountDTO);

		assertThat(new ResponseEntity<AccountDTO>(this.AccountDTO, HttpStatus.OK))
				.isEqualTo(this.controller.getAccount(this.id));

		verify(this.service, times(1)).findAccountByID(this.id);
	}

	@Test
	void getAllAccountsTest() {

		when(service.readAccounts())
				.thenReturn(this.accounts.stream().map(this::mapToDTO).collect(Collectors.toList()));

		assertThat(this.controller.getAllAccounts().getBody().isEmpty()).isFalse();

		verify(service, times(1)).readAccounts();
	}

	@Test
	void updateAccountsTest() {
		AccountDTO newAccount = new AccountDTO(1L, 1000, "John", "Smiths", 1000L, "boom");
		AccountDTO updatedAccount = new AccountDTO(this.id, newAccount.getAccountNumber(), newAccount.getFirstName(),
				newAccount.getLastName(), newAccount.getBalance(), newAccount.getPass());

		when(this.service.updateAccount(newAccount, this.id)).thenReturn(updatedAccount);

		assertThat(new ResponseEntity<AccountDTO>(updatedAccount, HttpStatus.ACCEPTED))
				.isEqualTo(this.controller.updateAccount(this.id, newAccount));

		verify(this.service, times(1)).updateAccount(newAccount, this.id);
	}
	// Account
}
