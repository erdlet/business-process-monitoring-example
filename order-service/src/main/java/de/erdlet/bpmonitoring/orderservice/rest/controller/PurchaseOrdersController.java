package de.erdlet.bpmonitoring.orderservice.rest.controller;

import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import de.erdlet.bpmonitoring.orderservice.service.PurchaseOrderService;
import java.net.URI;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrdersController {

  private final PurchaseOrderService purchaseOrderService;

  @Autowired
  public PurchaseOrdersController(final PurchaseOrderService purchaseOrderService) {
    this.purchaseOrderService = purchaseOrderService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces =
      MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<PurchaseOrder> createPurchaseOrder() {

    final var createdPurchaseOrder = purchaseOrderService.createNewPurchaseOrder();

    return ResponseEntity
        .created(formatGetOrderByOrderNumberURI(createdPurchaseOrder.getOrderNumber()))
        .body(createdPurchaseOrder);
  }

  @DeleteMapping(path = "/{orderNumber}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Void> cancelPurchaseOrder(@PathVariable("orderNumber") final UUID orderNumber) {
    purchaseOrderService.cancelOrder(orderNumber);

    return ResponseEntity.noContent()
        .build();
  }

  private static URI formatGetOrderByOrderNumberURI(final UUID orderNumber) {
    return UriComponentsBuilder.fromHttpUrl("http://localhost:8082")
        .pathSegment("purchase-orders")
        .pathSegment(orderNumber.toString())
        .build()
        .toUri();
  }
}
