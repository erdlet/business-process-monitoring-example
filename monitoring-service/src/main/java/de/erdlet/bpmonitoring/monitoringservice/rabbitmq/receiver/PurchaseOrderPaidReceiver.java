package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver;

import de.erdlet.bpmonitoring.monitoringservice.camunda.messages.WorkflowMessages;
import de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages.PurchaseOrderPaidMessage;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderPaidReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderPaidReceiver.class);

  private final RuntimeService runtimeService;

  @Autowired
  public PurchaseOrderPaidReceiver(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @RabbitListener(queues = "#{purchaseOrderPaidQueue.name}")
  public void receivePurchaseOrderPaidEvent(final PurchaseOrderPaidMessage message) {
    try {
      runtimeService.correlateMessage(WorkflowMessages.ORDER_PAID, message.orderNumber.toString());
    } catch (final Exception ex) {
      LOGGER.error("Could not process paid order <{}>", message.orderNumber, ex);
    }
  }
}
