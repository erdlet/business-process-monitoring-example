package de.erdlet.bpmonitoring.orderservice.messaging.publisher;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.erdlet.bpmonitoring.orderservice.messaging.publisher.messages.PurchaseOrderCreateMessage;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderCreatedPublisher implements PublisherWithFallbackMethod<PurchaseOrder> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderCreatedPublisher.class);

  private final FanoutExchange purchaseOrderCreatedExchange;
  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public PurchaseOrderCreatedPublisher(final FanoutExchange purchaseOrderCreatedExchange,
      final RabbitTemplate rabbitTemplate) {
    this.purchaseOrderCreatedExchange = purchaseOrderCreatedExchange;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  @HystrixCommand(fallbackMethod = PublisherWithFallbackMethod.FALLBACK_METHOD_NAME)
  public void publishEvent(final PurchaseOrder purchaseOrder) {

    final var messageToSend = new PurchaseOrderCreateMessage(purchaseOrder);

    rabbitTemplate.convertAndSend(purchaseOrderCreatedExchange.getName(), DEFAULT_ROUTING_KEY, messageToSend);

    LOGGER.info("Published created order <{}>", purchaseOrder.getOrderNumber());
  }

  @Override
  public void handlePublishFailure(final PurchaseOrder purchaseOrder) {
    LOGGER.error("Could not publish event 'order_created' for purchase order <{}>",
        purchaseOrder.getOrderNumber());
  }
}
