package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver;

import de.erdlet.bpmonitoring.monitoringservice.camunda.messages.WorkflowMessages;
import de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages.InvoiceCancelledMessage;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceCancelledReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceCancelledReceiver.class);

  private final RuntimeService runtimeService;

  @Autowired
  public InvoiceCancelledReceiver(final RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  @RabbitListener(queues = "#{invoiceCancelledQueue.name}")
  public void receiveOrderCreatedEvent(final InvoiceCancelledMessage message) {
    try {
      runtimeService.correlateMessage(WorkflowMessages.INVOICE_CANCELLED, message.getOrderNumber()
          .toString());

      LOGGER.info("Cancelled invoice <{}> for order <{}>", message.getInvoiceNumber(), message.getOrderNumber());
    } catch (final Exception ex) {
      LOGGER.error("Can't start order monitoring for purchase order <{}>", message.getOrderNumber());
    }
  }
}
