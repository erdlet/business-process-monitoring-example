package de.erdlet.bpmonitoring.invoiceservice.model;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Invoice {

  public enum Status {
    NOT_PAID,
    CANCELLED,
    PAID
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long databaseId;

  @Embedded
  private InvoiceNumber invoiceNumber;

  @Embedded
  private OrderNumber orderNumber;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  private Invoice() {
    // only for frameworks
  }

  public Invoice(final OrderNumber orderNumber) {
    this.orderNumber = orderNumber;

    this.invoiceNumber = new InvoiceNumber(UUID.randomUUID());
    this.status = Status.NOT_PAID;
  }

  public InvoiceNumber getInvoiceNumber() {
    return invoiceNumber;
  }

  public OrderNumber getOrderNumber() {
    return orderNumber;
  }

  public void pay() {
    this.status = Status.PAID;
  }

  public void cancel() {
    this.status = Status.CANCELLED;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Invoice invoice = (Invoice) o;
    return Objects.equals(invoiceNumber, invoice.invoiceNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invoiceNumber);
  }

  @Override
  public String toString() {
    return "Invoice{" +
        "databaseId=" + databaseId +
        ", invoiceNumber=" + invoiceNumber +
        ", status=" + status +
        '}';
  }
}
