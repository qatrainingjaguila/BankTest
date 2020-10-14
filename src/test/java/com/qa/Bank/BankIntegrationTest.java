package com.qa.Bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

		this.mockMVC.perform(delete("/account/deleteAccount/1")).andExpect(status().isNoContent());

	}

	@Test
	void testUpdateAccount() throws Exception {
		Account newAccount = new Account("John", "Smith", 100L, "pass123");
		String body = this.mapper.writeValueAsString(newAccount);
		ResultMatcher checkStatus = status().isAccepted();
		RequestBuilder req = put("/account/updateAccount/?id=1").contentType(MediaType.APPLICATION_JSON).content(body);
		MvcResult result = this.mockMVC.perform(req).andExpect(checkStatus).andReturn();
	}

}
