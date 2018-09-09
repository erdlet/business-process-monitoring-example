package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseOrderCreatedMessage {

  @JsonProperty(value = "order_number", required = true)
  public String orderNumber;

}
