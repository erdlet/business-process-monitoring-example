package de.erdlet.bpmonitoring.invoiceservice.messaging.receiver;

import de.erdlet.bpmonitoring.invoiceservice.messaging.messages.PurchaseOrderCreatedMessage;
import de.erdlet.bpmonitoring.invoiceservice.service.InvoiceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderCreatedReceiver {

  private final InvoiceService invoiceService;

  @Autowired
  public PurchaseOrderCreatedReceiver(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @RabbitListener(queues = "#{purchaseOrderCreatedQueue.name}")
  public void receivePurchaseOrderCreatedEvent(final PurchaseOrderCreatedMessage receivedMessage) {
    invoiceService.createNewInvoiceForOrder(receivedMessage.getOrderNumber());
  }
}
