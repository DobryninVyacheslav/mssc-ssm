package guru.springframework.msscssm.config.actions;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class AuthPaymentAction extends PaymentAction {

    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        executePaymentAction(stateContext, PaymentEvent.AUTH_APPROVED, PaymentEvent.AUTH_DECLINED);
    }
}
