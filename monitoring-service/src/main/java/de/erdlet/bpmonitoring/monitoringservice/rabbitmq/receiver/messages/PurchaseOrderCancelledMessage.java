package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class PurchaseOrderCancelledMessage {

  @JsonProperty(value = "order_number", required = true)
  private UUID orderNumber;

  public PurchaseOrderCancelledMessage() {
    // for framework usage
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(final UUID orderNumber) {
    this.orderNumber = orderNumber;
  }
}
