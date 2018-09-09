package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class PurchaseOrderPaidMessage {

  @JsonProperty(value = "order_number", required = true)
  public UUID orderNumber;

}
