package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver;

import de.erdlet.bpmonitoring.monitoringservice.camunda.messages.WorkflowMessages;
import de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages.PurchaseOrderShippedMessage;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderShippedReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderShippedReceiver.class);

  private final RuntimeService runtimeService;

  @Autowired
  public PurchaseOrderShippedReceiver(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @RabbitListener(queues = "#{purchaseOrderShippedQueue.name}")
  public void receivePurchaseOrderShippedEvent(final PurchaseOrderShippedMessage message) {

    try {
      runtimeService.correlateMessage(WorkflowMessages.ORDER_SHIPPED, message.orderNumber.toString());

      LOGGER.info("Shipped purchase order for order number <{}>", message.orderNumber);

    } catch (final Exception ex) {
      LOGGER.error("Could not process shipped purchase order for order number <{}>", message.orderNumber, ex);
    }
  }
}
