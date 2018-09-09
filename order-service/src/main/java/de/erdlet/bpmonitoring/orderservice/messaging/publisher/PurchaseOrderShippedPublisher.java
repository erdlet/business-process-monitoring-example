package de.erdlet.bpmonitoring.orderservice.messaging.publisher;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.erdlet.bpmonitoring.orderservice.messaging.publisher.messages.PurchaseOrderShippedMessage;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderShippedPublisher implements PublisherWithFallbackMethod<PurchaseOrder> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderShippedPublisher.class);

  private final RabbitTemplate rabbitTemplate;
  private final FanoutExchange purchaseOrderShippedExchange;

  @Autowired
  public PurchaseOrderShippedPublisher(final RabbitTemplate rabbitTemplate,
      final FanoutExchange purchaseOrderShippedExchange) {
    this.rabbitTemplate = rabbitTemplate;
    this.purchaseOrderShippedExchange = purchaseOrderShippedExchange;
  }

  @Override
  @HystrixCommand(fallbackMethod = FALLBACK_METHOD_NAME)
  public void publishEvent(final PurchaseOrder purchaseOrder) {
    final var messageToSend = new PurchaseOrderShippedMessage(purchaseOrder);

    rabbitTemplate.convertAndSend(purchaseOrderShippedExchange.getName(), DEFAULT_ROUTING_KEY, messageToSend);

    LOGGER.info("Published purchase order shipped event for order<{}>", purchaseOrder.getOrderNumber());
  }

  @Override
  public void handlePublishFailure(final PurchaseOrder purchaseOrder) {
    LOGGER.error("Failed to publish purchase order shipped event for order<{}>", purchaseOrder.getOrderNumber());
  }
}
