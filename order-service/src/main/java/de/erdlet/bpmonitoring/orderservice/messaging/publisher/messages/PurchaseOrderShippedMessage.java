package de.erdlet.bpmonitoring.orderservice.messaging.publisher.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import java.util.UUID;

public class PurchaseOrderShippedMessage {

  @JsonProperty(value = "order_number", required = true)
  private UUID orderNumber;

  public PurchaseOrderShippedMessage() {
    //for framework usage
  }

  public PurchaseOrderShippedMessage(final PurchaseOrder purchaseOrder) {
    this.orderNumber = purchaseOrder.getOrderNumber();
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(UUID orderNumber) {
    this.orderNumber = orderNumber;
  }
}
