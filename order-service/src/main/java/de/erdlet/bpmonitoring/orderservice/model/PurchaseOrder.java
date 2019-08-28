package de.erdlet.bpmonitoring.orderservice.model;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;

@Entity
public class PurchaseOrder {

  public static PurchaseOrder createNew() {
    final var purchaseOrder = new PurchaseOrder();
    purchaseOrder.orderNumber = UUID.randomUUID();
    purchaseOrder.status = Status.OPEN;

    return purchaseOrder;
  }

  public enum Status {
    OPEN,
    SHIPPED
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long databaseId;

  @NotNull
  @Type(type = "uuid-char")
  private UUID orderNumber;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Status status;

  private PurchaseOrder() {
    //only for frameworks
  }

  public UUID getOrderNumber() {
    return orderNumber;
  }

  public void shipToCustomer() {
    this.status = Status.SHIPPED;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PurchaseOrder purchaseOrder = (PurchaseOrder) o;
    return Objects.equals(orderNumber, purchaseOrder.orderNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(orderNumber);
  }

  @Override
  public String toString() {
    return "PurchaseOrder{" +
        "databaseId=" + databaseId +
        ", orderNumber=" + orderNumber +
        ", status=" + status +
        '}';
  }
}
