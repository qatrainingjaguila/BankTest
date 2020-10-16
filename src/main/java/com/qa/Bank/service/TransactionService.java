package com.qa.Bank.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Bank.dto.AccountDTO;
import com.qa.Bank.dto.PaymentDTO;
import com.qa.Bank.exceptions.AccountNotFoundException;
import com.qa.Bank.persistence.domain.Account;
import com.qa.Bank.persistence.domain.Payment;
import com.qa.Bank.persistence.repo.AccountRepo;
import com.qa.Bank.persistence.repo.PaymentRepo;
import com.qa.Bank.utils.MyBeanUtils;

//dev test
@Service
public class TransactionService {

	private AccountRepo accountRepo;

	private PaymentRepo paymentRepo;

	private ModelMapper mapper;

	private ObjectMapper objmapper;

	private static int newAccountNumber = 1000;

	private static void newAccountNo(Account account) {
		newAccountNumber += Math.round((Math.random() * 100));
		Integer newNumber = newAccountNumber;
		account.setAccountNumber(newNumber);

	}

	@Autowired
	public TransactionService(PaymentRepo paymentRepo, AccountRepo accountRepo, ModelMapper mapper,
			ObjectMapper objmapper) {
		this.paymentRepo = paymentRepo;
		this.accountRepo = accountRepo;
		this.mapper = mapper;
		this.objmapper = objmapper;
		;
	}

	private AccountDTO mapToDTO(Account account) { // takes an account and maps to a dto obj
		return this.mapper.map(account, AccountDTO.class);
	}

	private Account mapFromDTO(AccountDTO account) { // takes dto and maps to an account
		return this.mapper.map(account, Account.class);
	}

	public AccountDTO createAccount(AccountDTO account) {
		Account toSave = this.mapFromDTO(account);// creates account obj mapped from the DTO arg
		System.out.println("before:" + toSave.getAccountNumber());
		newAccountNo(toSave);
		System.out.println(toSave.getAccountNumber());
		Account saved = this.accountRepo.save(toSave); // using repo method.save to save the new obj
		newAccountNumber += (int) Math.random() * 10;
		return this.mapToDTO(saved); // returning obj as dto
	}

	public boolean deleteAccount(Long id) {
		if (!this.accountRepo.existsById(id)) {
			throw new AccountNotFoundException(); // throws custom exception
		}
		this.accountRepo.deleteById(id);
		return !this.accountRepo.existsById(id);
	}

	public List<AccountDTO> readAccounts() { // Returns list of account dtos
		return this.accountRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	} // uses findAll repo method to get all in repo, turns it into a stream, maps
		// with class method mapToDTO to convert
		// then uses stream method .collect which users collectors to convert output to
		// a data structure.

	public AccountDTO updateAccount(AccountDTO account, Long id) { // returns account dto
		// FOR MOCKITO:
		// Optional<Account> optAccount = this.repo.findById(id);
		// Payment optAccount = optAccount.orElseThrow()....
		Account toUpdate = this.accountRepo.findById(id).orElseThrow(AccountNotFoundException::new); // creating new
																										// Account
		// obj by finding the id
		// in repo
		MyBeanUtils.mergeNotNull(account, toUpdate); // calling function to merge but ignore empty fields
		return this.mapToDTO(this.accountRepo.save(toUpdate)); // save, convert to DTO and return
	}

	public AccountDTO findAccountByID(Long id) {
		Account exists = this.accountRepo.findById(id).orElseThrow(AccountNotFoundException::new);
		return this.mapToDTO(exists);
	}

	private PaymentDTO mapToDTO(Payment payment) {
		return this.mapper.map(payment, PaymentDTO.class);
	}

	private Payment mapFromDTO(PaymentDTO payment) {
		return this.mapper.map(payment, Payment.class);
	}

	public PaymentDTO createPayment(PaymentDTO payment) {

		Payment toSave = this.mapFromDTO(payment);

		Account payerNewBalance = this.accountRepo.findById(toSave.getUserId()).orElseThrow(RuntimeException::new);
		payerNewBalance.setBalance(payerNewBalance.getBalance() - payment.getAmount());

		List<Account> payeeAccounts = this.accountRepo.findAccountByAccountNumber(toSave.getRecipientId());
		Account payeeNewBalance = payeeAccounts.get(0);
		payeeNewBalance.setBalance(payeeNewBalance.getBalance() + payment.getAmount());
		Payment saved = this.paymentRepo.save(toSave);

		return this.mapToDTO(saved);
	}

	/*
	 * public boolean deletePayment(Long id) { if (!this.paymentRepo.existsById(id))
	 * { throw new RuntimeException(); } this.paymentRepo.deleteById(id); return
	 * !this.paymentRepo.existsById(id); }
	 */

	public PaymentDTO findPaymentByID(Long id) {
		return this.mapToDTO(this.paymentRepo.findById(id).orElseThrow(RuntimeException::new));
	}

	public List<PaymentDTO> readPayments() {
		return this.paymentRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	/*
	 * public PaymentDTO updatePayment(PaymentDTO payment, Long id) {
	 * 
	 * Payment toUpdate =
	 * this.paymentRepo.findById(id).orElseThrow(RuntimeException::new); return
	 * this.mapToDTO(this.paymentRepo.save(toUpdate)); }
	 */

	public AccountDTO login(int accountNumber, String pw) {
		try {
			Map<String, String> pass = objmapper.readValue(pw, Map.class);

			System.out.println(pass);
			System.out.println(accountNumber);

			List<Account> accounts = this.accountRepo.findAccountByAccountNumber(accountNumber);

			System.out.println(accounts);

			if (accounts.size() == 1) {
				Account toCheck = accounts.get(0); // this.accountRepo.findAccountByAccountNumber(accountNumber);

				System.out.println(toCheck);
				System.out.println(toCheck.getPass());
				System.out.println("answer = " + pass.get("pass").contentEquals(toCheck.getPass()));

				if (pass.get("pass").equals(toCheck.getPass())) {

					System.out.println("pass checked");

					return this.mapToDTO(toCheck);
				} else {
					throw new RuntimeException();
				}
			} else {
				throw new RuntimeException();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}

}
