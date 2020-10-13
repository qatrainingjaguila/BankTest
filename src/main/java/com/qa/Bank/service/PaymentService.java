package com.qa.Bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.Bank.dto.PaymentDTO;
import com.qa.Bank.persistence.domain.Payment;
import com.qa.Bank.persistence.repo.PaymentRepo;

@Service
public class PaymentService {

	private PaymentRepo repo;

	private ModelMapper mapper;

	@Autowired
	public PaymentService(PaymentRepo repo, ModelMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}

	private PaymentDTO mapToDTO(Payment payment) {
		return this.mapper.map(payment, PaymentDTO.class);
	}

	private Payment mapFromDTO(PaymentDTO payment) {
		return this.mapper.map(payment, Payment.class);
	}

	public PaymentDTO createPayment(PaymentDTO payment) {
		Payment toSave = this.mapFromDTO(payment);
		Payment saved = this.repo.save(toSave);
		return this.mapToDTO(saved);
	}

	public boolean deletePayment(Long id) {
		if (!this.repo.existsById(id)) {
			throw new RuntimeException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public PaymentDTO findPaymentByID(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow(RuntimeException::new));
	}

	public List<PaymentDTO> readPayments() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public PaymentDTO updatePayment(PaymentDTO payment, Long id) {
		Payment toUpdate = this.repo.findById(id).orElseThrow(RuntimeException::new);
		return this.mapToDTO(this.repo.save(toUpdate));
	}

}
