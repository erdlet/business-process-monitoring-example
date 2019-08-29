package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver;

import de.erdlet.bpmonitoring.monitoringservice.camunda.messages.WorkflowMessages;
import de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages.PurchaseOrderCancelledMessage;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderCancelledReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderCancelledReceiver.class);

  private final RuntimeService runtimeService;

  @Autowired
  public PurchaseOrderCancelledReceiver(final RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @RabbitListener(queues = "#{purchaseOrderCancelledQueue.name}")
  public void receiveOrderCreatedEvent(final PurchaseOrderCancelledMessage message) {
    try {
      runtimeService.correlateMessage(WorkflowMessages.ORDER_CANCELLED, message.getOrderNumber().toString());

      LOGGER.info("Cancelled purchase order <{}>", message.getOrderNumber());
    } catch (final Exception ex) {
      LOGGER.error("Can't cancel purchase order <{}>", message.getOrderNumber());
    }
  }
}
