package de.erdlet.bpmonitoring.invoiceservice.rest.exception;

import de.erdlet.bpmonitoring.invoiceservice.model.InvoiceNumber;
import de.erdlet.bpmonitoring.invoiceservice.model.OrderNumber;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvoiceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -9127894329811828655L;

  public InvoiceNotFoundException(final InvoiceNumber invoiceNumber) {
    super(String.format("Can't find invoice for invoice number <%s>", invoiceNumber));
  }

  public InvoiceNotFoundException(final OrderNumber orderNumber) {
    super(String.format("Can't find invoice for order number <%s>", orderNumber));
  }
}
