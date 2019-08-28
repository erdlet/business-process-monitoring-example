package de.erdlet.bpmonitoring.invoiceservice.messaging.receiver;

import de.erdlet.bpmonitoring.invoiceservice.messaging.messages.PurchaseOrderCreatedMessage;
import de.erdlet.bpmonitoring.invoiceservice.model.OrderNumber;
import de.erdlet.bpmonitoring.invoiceservice.rest.exception.InvoiceNotFoundException;
import de.erdlet.bpmonitoring.invoiceservice.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderCancelledReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderCancelledReceiver.class);

  private final InvoiceService invoiceService;

  @Autowired
  public PurchaseOrderCancelledReceiver(final InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @RabbitListener(queues = "#{purchaseOrderCancelledQueue.name}")
  public void receivePurchaseOrderCreatedEvent(final PurchaseOrderCreatedMessage receivedMessage) {
    LOGGER.info("Received event to cancel invoice for purchase order <{}>", receivedMessage.getOrderNumber());
    try {
      invoiceService.cancelInvoiceForOrder(new OrderNumber(receivedMessage.getOrderNumber()));
    } catch (final InvoiceNotFoundException ex) {
      LOGGER.error("Could not process cancelled order <{}> because no invoice was found.", receivedMessage.getOrderNumber(), ex);
    }
  }
}
