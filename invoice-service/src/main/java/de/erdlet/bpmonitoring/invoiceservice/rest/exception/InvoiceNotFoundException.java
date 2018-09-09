package de.erdlet.bpmonitoring.invoiceservice.rest.exception;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvoiceNotFoundException extends RuntimeException {

  public InvoiceNotFoundException(final UUID invoiceId) {
    super(String.format("Can't find invoice for id <%s>", invoiceId.toString()));
  }
}
