package de.erdlet.bpmonitoring.invoiceservice.messaging.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class PurchaseOrderCreatedMessage {

  @JsonProperty(value = "order_number", required = true)
  private UUID orderNumber;

  public PurchaseOrderCreatedMessage() {
    //for framework usage
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(UUID orderNumber) {
    this.orderNumber = orderNumber;
  }
}
