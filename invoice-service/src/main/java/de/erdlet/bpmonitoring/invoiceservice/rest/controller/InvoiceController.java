package de.erdlet.bpmonitoring.invoiceservice.rest.controller;

import de.erdlet.bpmonitoring.invoiceservice.model.Invoice;
import de.erdlet.bpmonitoring.invoiceservice.service.InvoiceService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

  private final InvoiceService invoiceService;

  @Autowired
  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{invoice_number}/payments")
  public Invoice payInvoice(@PathVariable("invoice_number") final UUID invoiceNumber) {
    return invoiceService.payInvoice(invoiceNumber);
  }
}
