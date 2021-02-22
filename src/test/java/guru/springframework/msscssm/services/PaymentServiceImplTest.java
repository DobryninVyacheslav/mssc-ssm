package guru.springframework.msscssm.services;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
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

        log.info("Should be PRE_AUTH or PRE_AUTH_ERROR: {}", stateMachine.getState().getId());
        log.info("PreAuthedPayment: {}", preAuthedPayment);

        assertEquals(PaymentState.NEW, savedPayment.getState());
    }

    @RepeatedTest(10)
    void auth() {
        Payment savedPayment = paymentService.newPayment(this.payment);
        StateMachine<PaymentState, PaymentEvent> preAuthSM = paymentService.preAuth(savedPayment.getId());

        if (preAuthSM.getState().getId() == PaymentState.PRE_AUTH) {
            log.info("Payment is Pre Authorized");
            StateMachine<PaymentState, PaymentEvent> authSM = paymentService
                    .authorizePayment(savedPayment.getId());
            log.info("Result of auth: {}", authSM.getState().getId());
        } else {
            log.info("Payment failed pre-auth...");
        }
        assertEquals(PaymentState.NEW, savedPayment.getState());
    }
}