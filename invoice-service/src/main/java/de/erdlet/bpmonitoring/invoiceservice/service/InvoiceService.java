package de.erdlet.bpmonitoring.invoiceservice.service;

import de.erdlet.bpmonitoring.invoiceservice.messaging.publisher.InvoiceCancelledPublisher;
import de.erdlet.bpmonitoring.invoiceservice.messaging.publisher.InvoicePaidPublisher;
import de.erdlet.bpmonitoring.invoiceservice.model.Invoice;
import de.erdlet.bpmonitoring.invoiceservice.model.InvoiceNumber;
import de.erdlet.bpmonitoring.invoiceservice.model.OrderNumber;
import de.erdlet.bpmonitoring.invoiceservice.repository.InvoiceRepository;
import de.erdlet.bpmonitoring.invoiceservice.rest.exception.InvoiceNotFoundException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InvoiceService {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

  private final InvoiceRepository invoiceRepository;
  private final InvoicePaidPublisher invoicePaidPublisher;
  private final InvoiceCancelledPublisher invoiceCancelledPublisher;

  @Autowired
  public InvoiceService(final InvoiceRepository invoiceRepository, final InvoicePaidPublisher invoicePaidPublisher,
      final InvoiceCancelledPublisher invoiceCancelledPublisher) {
    this.invoiceRepository = invoiceRepository;
    this.invoicePaidPublisher = invoicePaidPublisher;
    this.invoiceCancelledPublisher = invoiceCancelledPublisher;
  }

  public void createNewInvoiceForOrder(final UUID orderNumber) {
    final var newInvoice = new Invoice(new OrderNumber(orderNumber));

    invoiceRepository.save(newInvoice);

    LOGGER.info("Saved invoice <{}> for order <{}>", newInvoice.getInvoiceNumber(), orderNumber);
  }

  public Invoice payInvoice(final UUID invoiceNumber) {
    final Invoice invoice = invoiceRepository.findInvoiceByInvoiceNumber(new InvoiceNumber(invoiceNumber))
        .orElseThrow(() -> new InvoiceNotFoundException(new InvoiceNumber(invoiceNumber)));

    invoice.pay();

    final var paidInvoice = invoiceRepository.save(invoice);

    invoicePaidPublisher.publishEvent(paidInvoice);

    return paidInvoice;
  }

  public void cancelInvoiceForOrder(final OrderNumber orderNumber) {
    final Invoice invoice = invoiceRepository.findInvoiceByOrderNumber(orderNumber)
        .orElseThrow(() -> new InvoiceNotFoundException(orderNumber));

    invoice.cancel();

    invoiceRepository.save(invoice);

    LOGGER.info("Cancelled invoice <{}> for order <{}>", invoice.getInvoiceNumber(), orderNumber);

    invoiceCancelledPublisher.publishEvent(invoice);
  }
}
