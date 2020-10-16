package com.qa.Bank.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.Bank.dto.AccountDTO;
import com.qa.Bank.service.TransactionService;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

	private TransactionService service;

	@Autowired
	public AccountController(TransactionService service) { // constructor with a service passed in, dependency injection
		super();
		this.service = service;
	}

	@PostMapping("/createAccount")
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO account) { // returns response entity with
																						// an account dto
		return new ResponseEntity<>(this.service.createAccount(account), HttpStatus.CREATED); // returns dto object from
																								// service and http
																								// status if successful
	}

	@DeleteMapping("/deleteAccount/{id}")
	public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Long id) { // accepts id
		return this.service.deleteAccount(id) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) // uses ternary ?/true sends
																							// no content status code if
																							// not internal server error
																							// is sent
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) { // accepts the id
		return ResponseEntity.ok(this.service.findAccountByID(id)); //
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<AccountDTO>> getAllAccounts() {
		return ResponseEntity.ok(this.service.readAccounts());
	}

	@PutMapping("/updateAccount")
	public ResponseEntity<AccountDTO> updateAccount(@PathParam("id") Long id, @RequestBody AccountDTO account) { // uses
																													// /?id=
		return new ResponseEntity<>(this.service.updateAccount(account, id), HttpStatus.ACCEPTED);
	}

	@PostMapping("/login")
	public ResponseEntity<AccountDTO> login(@PathParam("acc") int acc, @RequestBody String pw) {
		return new ResponseEntity<>(this.service.login(acc, pw), HttpStatus.ACCEPTED);
	}
}
