package guru.springframework.msscssm.config.actions;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.services.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Random;

@Slf4j
public abstract class PaymentAction implements Action<PaymentState, PaymentEvent> {

    protected void executePaymentAction(StateContext<PaymentState, PaymentEvent> context,
                                        PaymentEvent approvedEvent, PaymentEvent declinedEvent) {
        log.info("Payment action was called!!!");
        if (new Random().nextInt(10) < 8) {
            log.info("Approved");
            sendPaymentEvent(context, approvedEvent);
        } else {
            log.info("Declined!!!");
            sendPaymentEvent(context, declinedEvent);
        }
    }

    private void sendPaymentEvent(StateContext<PaymentState, PaymentEvent> context,
                                  PaymentEvent preAuthDeclined) {
        context.getStateMachine().sendEvent(MessageBuilder
                .withPayload(preAuthDeclined)
                .setHeader(PaymentServiceImpl.PAYMENT_ID_HEADER,
                        context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER))
                .build()
        );
    }
}
