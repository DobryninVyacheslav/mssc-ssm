package guru.springframework.msscssm.config.actions;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.stereotype.Component;

@Component
public class PreAuthPaymentAction extends PaymentAction {

    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> stateContext) {
        executePaymentAction(stateContext, PaymentEvent.PRE_AUTH_APPROVED, PaymentEvent.PRE_AUTH_DECLINED);
    }

}
