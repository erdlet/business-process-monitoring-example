package de.erdlet.bpmonitoring.invoiceservice.messaging.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.erdlet.bpmonitoring.invoiceservice.model.Invoice;
import java.util.UUID;

public class InvoiceCancelledMessage {

  @JsonProperty(value = "order_number", required = true)
  private UUID orderNumber;

  @JsonProperty(value = "invoice_number", required = true)
  private UUID invoiceNumber;

  public InvoiceCancelledMessage() {
    //for framework usage
  }

  public InvoiceCancelledMessage(final Invoice invoice) {
    this.orderNumber = invoice.getOrderNumber().getNumber();
    this.invoiceNumber = invoice.getInvoiceNumber().getNumber();
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
