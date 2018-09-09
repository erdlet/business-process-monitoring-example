package de.erdlet.bpmonitoring.orderservice.repository;

import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {

  /**
   * Finds an {@link PurchaseOrder} by its {@link PurchaseOrder#orderNumber}.
   *
   * @param orderNumber the orderNumber for which the {@link PurchaseOrder} is searched
   * @return Optional containing the {@link PurchaseOrder} or empty Optional if none is found
   */
  Optional<PurchaseOrder> findByOrderNumber(final UUID orderNumber);

}
