package com.qa.Bank.dto;

public class PaymentDTO {

	private Long paymentId;

	private Long amount;

	private Long userId;

	private int recipientId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public PaymentDTO() {

	}

	public int getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}

	public PaymentDTO(Long paymentId, Long amount, Long userId, int recipientId) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.userId = userId;
		this.recipientId = recipientId;
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

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
