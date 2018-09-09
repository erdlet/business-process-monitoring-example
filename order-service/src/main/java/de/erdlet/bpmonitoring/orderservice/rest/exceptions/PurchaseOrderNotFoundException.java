package de.erdlet.bpmonitoring.orderservice.rest.exceptions;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PurchaseOrderNotFoundException extends RuntimeException {

  public PurchaseOrderNotFoundException(final UUID orderNumber) {
    super("purchase order for order number <" + orderNumber + "> not found");
  }
}
