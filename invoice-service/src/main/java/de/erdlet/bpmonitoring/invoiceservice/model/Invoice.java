package de.erdlet.bpmonitoring.invoiceservice.model;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Type;

@Entity
public class Invoice {

  public enum Status {
    NOT_PAID,
    PAID
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long databaseId;

  @Column(nullable = false, updatable = false, unique = true)
  @Type(type = "uuid-char")
  private UUID invoiceNumber;

  @Column(nullable = false, updatable = false, unique = true)
  @Type(type = "uuid-char")
  private UUID orderNumber;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  private Invoice() {
    // only for frameworks
  }

  public Invoice(final UUID orderNumber) {
    this.orderNumber = orderNumber;

    this.invoiceNumber = UUID.randomUUID();
    this.status = Status.NOT_PAID;
  }

  public UUID getInvoiceNumber() {
    return invoiceNumber;
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void pay() {
    this.status = Status.PAID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) o;
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
