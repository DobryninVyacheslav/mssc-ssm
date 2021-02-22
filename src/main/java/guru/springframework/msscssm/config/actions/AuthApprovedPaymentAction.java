package guru.springframework.msscssm.config.actions;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthApprovedPaymentAction extends PaymentAction {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        log.info("Sending Notification of Auth Approved");
    }
}
