package guru.springframework.msscssm.services;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder()
                .amount(BigDecimal.valueOf(12.99))
                .build();
    }

    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(this.payment);

        StateMachine<PaymentState, PaymentEvent> stateMachine = paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = paymentRepository.findById(savedPayment.getId()).orElse(null);

        System.out.println(stateMachine.getState().getId());

        System.out.println(preAuthedPayment);

        assertEquals(PaymentState.NEW, savedPayment.getState());
        assertEquals(PaymentState.PRE_AUTH, stateMachine.getState().getId());
    }
}