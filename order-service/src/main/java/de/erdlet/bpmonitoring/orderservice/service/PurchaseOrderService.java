package de.erdlet.bpmonitoring.orderservice.service;

import de.erdlet.bpmonitoring.orderservice.messaging.publisher.PurchaseOrderCreatedPublisher;
import de.erdlet.bpmonitoring.orderservice.messaging.publisher.PurchaseOrderShippedPublisher;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import de.erdlet.bpmonitoring.orderservice.repository.PurchaseOrderRepository;
import de.erdlet.bpmonitoring.orderservice.rest.exceptions.PurchaseOrderNotFoundException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PurchaseOrderService {

  private final PurchaseOrderRepository purchaseOrderRepository;

  private final PurchaseOrderCreatedPublisher orderCreatedPublisher;
  private final PurchaseOrderShippedPublisher purchaseOrderShippedPublisher;

  @Autowired
  public PurchaseOrderService(
      final PurchaseOrderRepository purchaseOrderRepository,
      final PurchaseOrderCreatedPublisher orderCreatedPublisher,
      final PurchaseOrderShippedPublisher purchaseOrderShippedPublisher) {
    this.purchaseOrderRepository = purchaseOrderRepository;
    this.orderCreatedPublisher = orderCreatedPublisher;
    this.purchaseOrderShippedPublisher = purchaseOrderShippedPublisher;
  }

  public PurchaseOrder createNewPurchaseOrder() {
    final var purchaseOrderToCreate = PurchaseOrder.createNew();

    final var createdPurchaseOrder = purchaseOrderRepository.save(purchaseOrderToCreate);

    orderCreatedPublisher.publishEvent(createdPurchaseOrder);

    return createdPurchaseOrder;
  }

  public void shipPurchaseOrder(final UUID orderNumber) {
    final var purchaseOrder = purchaseOrderRepository.findByOrderNumber(orderNumber)
        .orElseThrow(() -> new PurchaseOrderNotFoundException(orderNumber));

    purchaseOrder.shipToCustomer();

    final var savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

    //The order needs some time to be shipped. Lets simulate
    //different 3rd party system calls with this sleep.
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    purchaseOrderShippedPublisher.publishEvent(savedPurchaseOrder);
  }
}
