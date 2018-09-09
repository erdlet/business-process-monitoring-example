package de.erdlet.bpmonitoring.invoiceservice.messaging.publisher;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.erdlet.bpmonitoring.invoiceservice.messaging.messages.InvoicePaidMessage;
import de.erdlet.bpmonitoring.invoiceservice.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoicePaidPublisher implements PublisherWithFallbackMethod<Invoice> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvoicePaidPublisher.class);

  private final RabbitTemplate rabbitTemplate;
  private final FanoutExchange purchaseOrderPaidExchange;

  @Autowired
  public InvoicePaidPublisher(final FanoutExchange purchaseOrderPaidExchange, final RabbitTemplate rabbitTemplate) {
    this.purchaseOrderPaidExchange = purchaseOrderPaidExchange;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  @HystrixCommand(fallbackMethod = FALLBACK_METHOD_NAME)
  public void publishEvent(final Invoice invoice) {
    final var invoicePaidMessage = new InvoicePaidMessage(invoice);

    rabbitTemplate.convertAndSend(purchaseOrderPaidExchange.getName(), DEFAULT_ROUTING_KEY, invoicePaidMessage);
  }

  @Override
  public void handlePublishFailure(final Invoice invoice) {
    LOGGER.error("Could not  publish event for paid invoice <{}> of order <{}>", invoice.getInvoiceNumber(),
        invoice.getOrderNumber());
  }
}
