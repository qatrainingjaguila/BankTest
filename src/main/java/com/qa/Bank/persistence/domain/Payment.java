package com.qa.Bank.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;

	@Column
	@NotNull
	private Long amount;

	@Column
	@NotNull
	private Long recipientId;

	public Long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(Long recipientId) {
		this.recipientId = recipientId;
	}

	@Column
	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.LAZY) // Doesn't fetch data until an explicit call is made
	@JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false) // foreign key column cannot
																							// be updated, inserted or
																							// null
	private Account account;

	public Payment() {
		super();
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getAmount() {
		return amount;
	}

	public Payment(@NotNull Long amount) {
		super();
		this.amount = amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
