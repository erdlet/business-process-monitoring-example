package de.erdlet.bpmonitoring.monitoringservice.camunda.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CancelOrderDelegate implements JavaDelegate {

  private static final Logger LOGGER = LoggerFactory.getLogger(CancelOrderDelegate.class);

  @Override
  public void execute(DelegateExecution delegateExecution) throws Exception {
    LOGGER.info("Order <{}> cancelled because payment was not received",
        delegateExecution.getProcessBusinessKey());
  }
}
