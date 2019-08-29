package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.receiver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class InvoiceCancelledMessage {

  @JsonProperty(value = "order_number", required = true)
  private UUID orderNumber;

  @JsonProperty(value = "invoice_number", required = true)
  private UUID invoiceNumber;

  public InvoiceCancelledMessage() {
    //for framework usage
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(UUID orderNumber) {
    this.orderNumber = orderNumber;
  }

  public UUID getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(UUID invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }
}
