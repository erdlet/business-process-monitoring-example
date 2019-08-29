package de.erdlet.bpmonitoring.invoiceservice.repository;

import de.erdlet.bpmonitoring.invoiceservice.model.Invoice;
import de.erdlet.bpmonitoring.invoiceservice.model.InvoiceNumber;
import de.erdlet.bpmonitoring.invoiceservice.model.OrderNumber;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

  Optional<Invoice> findInvoiceByInvoiceNumber(final InvoiceNumber invoiceNumber);

  Optional<Invoice> findInvoiceByOrderNumber(final OrderNumber orderNumber);

}
