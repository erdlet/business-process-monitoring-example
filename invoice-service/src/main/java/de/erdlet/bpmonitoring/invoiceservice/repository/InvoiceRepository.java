package de.erdlet.bpmonitoring.invoiceservice.repository;

import de.erdlet.bpmonitoring.invoiceservice.model.Invoice;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

  Optional<Invoice> findInvoiceByInvoiceNumber(final UUID invoiceNumber);

}
