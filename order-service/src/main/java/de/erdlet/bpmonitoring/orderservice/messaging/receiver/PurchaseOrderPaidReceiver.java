package de.erdlet.bpmonitoring.orderservice.messaging.receiver;

import de.erdlet.bpmonitoring.orderservice.messaging.receiver.messages.PurchaseOrderPaidMessage;
import de.erdlet.bpmonitoring.orderservice.service.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderPaidReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderPaidReceiver.class);

  private final PurchaseOrderService purchaseOrderService;

  @Autowired
  public PurchaseOrderPaidReceiver(PurchaseOrderService purchaseOrderService) {
    this.purchaseOrderService = purchaseOrderService;
  }

  @RabbitListener(queues = "#{purchaseOrderPaidQueue.name}")
  public void receivePurchaseOrderPaidEvent(final PurchaseOrderPaidMessage message) {
    try {
      purchaseOrderService.shipPurchaseOrder(message.orderNumber);
    } catch (final Exception ex) {
      LOGGER.error("Could not process paid order <{}>", message.orderNumber, ex);
    }
  }
}
