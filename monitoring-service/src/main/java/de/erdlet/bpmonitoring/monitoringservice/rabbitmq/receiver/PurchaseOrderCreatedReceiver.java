package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver;

import de.erdlet.bpmonitoring.monitoringservice.camunda.messages.WorkflowMessages;
import de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages.PurchaseOrderCreatedMessage;
import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderCreatedReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderCreatedReceiver.class);

  private final RuntimeService runtimeService;

  @Autowired
  public PurchaseOrderCreatedReceiver(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @RabbitListener(queues = "#{purchaseOrderCreatedQueue.name}")
  public void receiveOrderCreatedEvent(final PurchaseOrderCreatedMessage message) {
    try {
      runtimeService.startProcessInstanceByMessage(WorkflowMessages.ORDER_RECEIVED, message.orderNumber);

      LOGGER.info("Received purchase order <{}>", message.orderNumber);
    } catch (final Exception ex) {
      LOGGER.error("Can't start order monitoring for purchase order <{}>", message.orderNumber);
    }
  }
}
