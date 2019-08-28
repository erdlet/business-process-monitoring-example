package de.erdlet.bpmonitoring.orderservice.messaging.publisher;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.erdlet.bpmonitoring.orderservice.messaging.publisher.messages.PurchaseOrderCancelledMessage;
import de.erdlet.bpmonitoring.orderservice.model.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderCancelledPublisher implements PublisherWithFallbackMethod<PurchaseOrder> {

  private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderCancelledPublisher.class);

  private final FanoutExchange purchaseOrderCancelledExchange;
  private final RabbitTemplate rabbitTemplate;

  public PurchaseOrderCancelledPublisher(final FanoutExchange purchaseOrderCancelledExchange,
      final RabbitTemplate rabbitTemplate) {
    this.purchaseOrderCancelledExchange = purchaseOrderCancelledExchange;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  @HystrixCommand(fallbackMethod = PublisherWithFallbackMethod.FALLBACK_METHOD_NAME)
  public void publishEvent(final PurchaseOrder purchaseOrder) {
    final var messageToSend = new PurchaseOrderCancelledMessage(purchaseOrder);

    rabbitTemplate.convertAndSend(purchaseOrderCancelledExchange.getName(), DEFAULT_ROUTING_KEY, messageToSend);

    LOGGER.info("Published cancelled order <{}>", purchaseOrder.getOrderNumber());
  }

  @Override
  public void handlePublishFailure(final PurchaseOrder purchaseOrder) {
    LOGGER.error("Could not publish event 'order_cancelled' for purchase order <{}>",
        purchaseOrder.getOrderNumber());
  }
}
