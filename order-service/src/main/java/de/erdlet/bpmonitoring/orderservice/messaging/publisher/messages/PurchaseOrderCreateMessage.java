package de.erdlet.bpmonitoring.orderservice.messaging.publisher.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import java.util.UUID;

public class PurchaseOrderCreateMessage {

  @JsonProperty("order_number")
  private UUID orderNumber;

  public PurchaseOrderCreateMessage() {
    // for framework usage
  }

  public PurchaseOrderCreateMessage(final PurchaseOrder purchaseOrder) {
    this.orderNumber = purchaseOrder.getOrderNumber();
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(UUID orderNumber) {
    this.orderNumber = orderNumber;
  }
}
