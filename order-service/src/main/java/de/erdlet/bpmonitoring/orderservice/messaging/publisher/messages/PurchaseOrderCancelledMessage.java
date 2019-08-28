package de.erdlet.bpmonitoring.orderservice.messaging.publisher.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import java.util.UUID;

public class PurchaseOrderCancelledMessage {

  @JsonProperty("order_number")
  private UUID orderNumber;

  public PurchaseOrderCancelledMessage() {
    // for framework usage
  }

  public PurchaseOrderCancelledMessage(final PurchaseOrder purchaseOrder) {
    this.orderNumber = purchaseOrder.getOrderNumber();
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(final UUID orderNumber) {
    this.orderNumber = orderNumber;
  }
}
