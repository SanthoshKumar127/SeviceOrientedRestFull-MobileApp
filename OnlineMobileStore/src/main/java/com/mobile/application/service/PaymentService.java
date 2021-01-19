package com.mobile.application.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mobile.application.model.Orders;
import com.mobile.application.model.Payment;
import com.mobile.application.repository.PaymentRepository;

@Service
@Transactional

public class PaymentService {
	@Autowired
	private PaymentRepository paymentRepository;

	public PaymentService(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
	}

	public Payment savePayment(Payment payment, Orders order) {

		return paymentRepository.save(payment);

	}

	public Page<Payment> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Payment>payment=paymentRepository.findAll(pageable);
		return payment;
	}

	public void save(Payment payment) {
		paymentRepository.save(payment);
		
	}

	

	public Page<Payment> findAllById(Integer id, Pageable paymentPageable) {
		Page<Payment>payment= paymentRepository.findAllById(id,paymentPageable);
		return payment;
	}
}
