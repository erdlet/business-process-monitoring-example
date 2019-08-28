package de.erdlet.bpmonitoring.invoiceservice.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

@Embeddable
public class InvoiceNumber {

  @Column(name="invoice_number", nullable = false, updatable = false, unique = true)
  @Type(type = "uuid-char")
  private UUID number;

  public InvoiceNumber() {
  }

  public InvoiceNumber(final UUID number) {
    this.number = number;
  }

  public void setNumber(final UUID number) {
    this.number = number;
  }

  public UUID getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return number.toString();
  }
}
