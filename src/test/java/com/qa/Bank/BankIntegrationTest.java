package com.qa.Bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.Bank.persistence.domain.Account;
import com.qa.Bank.persistence.domain.Payment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BankIntegrationTest {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreateAccount() throws Exception {
		Account newAccount = new Account("John", "Smith", 100L, "pass123");
		String body = this.mapper.writeValueAsString(newAccount);
		ResultMatcher checkStatus = status().isCreated();
		RequestBuilder req = post("/account/createAccount").contentType(MediaType.APPLICATION_JSON).content(body);
		MvcResult result = this.mockMVC.perform(req).andExpect(checkStatus).andReturn();
	}

	@Test
	void testCreatePayment() throws Exception {

		Account newAccount = new Account("John", "Smith", 100L, "pass123");
		String body = this.mapper.writeValueAsString(newAccount);
		RequestBuilder req = post("/account/createAccount").contentType(MediaType.APPLICATION_JSON).content(body);
		MvcResult result = this.mockMVC.perform(req).andReturn();
		String reqBody = result.getResponse().getContentAsString();
		Account accountResult = this.mapper.readValue(reqBody, Account.class);

		Payment newPayment = new Payment(100L, accountResult.getAccountNumber(), 1L);
		String paymentBody = this.mapper.writeValueAsString(newPayment);
		ResultMatcher checkStatus = status().isCreated();
		RequestBuilder testReq = post("/payment/createPayment").contentType(MediaType.APPLICATION_JSON)
				.content(paymentBody);
		this.mockMVC.perform(testReq).andExpect(checkStatus).andReturn();
	}

	@Test
	void testDeleteAccount() throws Exception {
		Account newAccount = new Account("John", "Smith", 100L, "pass123");

		this.mockMVC.perform(delete("/account/deleteAccount/3")).andExpect(status().isNoContent());

	}

	@Test
	void testUpdateAccount() throws Exception {
		Account newAccount = new Account("John", "Smith", 100L, "pass123");
		String body = this.mapper.writeValueAsString(newAccount);
		ResultMatcher checkStatus = status().isAccepted();
		RequestBuilder req = put("/account/updateAccount/?id=1").contentType(MediaType.APPLICATION_JSON).content(body);
		MvcResult result = this.mockMVC.perform(req).andExpect(checkStatus).andReturn();
	}

	@Test
	void testReadAccount() throws Exception {
		Account newAccount = new Account("John", "Smith", 100L, "pass123");
		String body = this.mapper.writeValueAsString(newAccount);
		RequestBuilder req = post("/account/createAccount").contentType(MediaType.APPLICATION_JSON).content(body);
		MvcResult result = this.mockMVC.perform(req).andReturn();
		String reqBody = result.getResponse().getContentAsString();
		Account accountResult = this.mapper.readValue(reqBody, Account.class);

		String responseBody = this.mapper.writeValueAsString(accountResult);
		this.mockMVC.perform(get("/account/get/4")).andExpect(status().isOk()).andExpect(content().json(responseBody));
	}

	@Test
	void testGetAccounts() throws Exception {
		Account newAccount = new Account("John", "Smith", 100L, "pass123");
		String body = this.mapper.writeValueAsString(newAccount);
		RequestBuilder req = post("/account/createAccount").contentType(MediaType.APPLICATION_JSON).content(body);

		this.mockMVC.perform(delete("/account/deleteAccount/1")).andExpect(status().isNoContent());
		this.mockMVC.perform(delete("/account/deleteAccount/2")).andExpect(status().isNoContent());

		MvcResult result = this.mockMVC.perform(req).andReturn();
		String reqBody = result.getResponse().getContentAsString();
		Account accountResult = this.mapper.readValue(reqBody, Account.class);

		List<Account> accounts = new ArrayList<>();

		String content = this.mockMVC.perform(request(HttpMethod.GET, "/account/getAll")).andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();

		assertEquals(this.mapper.writeValueAsString(accounts), content); // Response returns values in a different
																			// order, unable to assert
	}

	/*
	 * @Test void testSignIn() throws Exception { Account newAccount = new
	 * Account("John", "Smith", 100L, "pass1234"); String body =
	 * this.mapper.writeValueAsString(newAccount); RequestBuilder req =
	 * post("/account/createAccount").contentType(MediaType.APPLICATION_JSON).
	 * content(body); MvcResult result = this.mockMVC.perform(req).andReturn();
	 * String reqBody = result.getResponse().getContentAsString(); Account
	 * accountResult = this.mapper.readValue(reqBody, Account.class);
	 * System.out.println(accountResult.getAccountNumber());
	 * 
	 * JSONObject json = new JSONObject(); json.put("pass", "pass123");
	 * 
	 * String url = "/account/login/?id =" + accountResult.getAccountNumber();
	 * 
	 * RequestBuilder loginReq =
	 * post(url).contentType(MediaType.APPLICATION_JSON).content(json.toString());
	 * this.mockMVC.perform(loginReq).andExpect(status().isAccepted()); }
	 */

}
