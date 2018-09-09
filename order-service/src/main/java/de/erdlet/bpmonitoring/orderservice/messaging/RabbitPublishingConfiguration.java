package de.erdlet.bpmonitoring.orderservice.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPublishingConfiguration {

  /* Exchange, Queues and Bindings for publishing created purchase order events. */

  @Bean
  public FanoutExchange purchaseOrderCreatedExchange() {
    return new FanoutExchange("purchaseorder.created");
  }

  @Bean
  public Queue monitoringPurchaseOrderCreatedQueue() {
    return new Queue("monitoring.purchaseorder.created");
  }

  @Bean
  public Binding monitoringPurchaseOrderCreatedBinding() {
    return BindingBuilder.bind(monitoringPurchaseOrderCreatedQueue()).to(purchaseOrderCreatedExchange());
  }

  @Bean
  public Queue invoicePurchaseOrderCreatedQueue() {
    return new Queue("invoice.purchaseorder.created");
  }

  @Bean
  public Binding invoicePurchaseOrderCreatedBinding() {
    return BindingBuilder.bind(invoicePurchaseOrderCreatedQueue()).to(purchaseOrderCreatedExchange());
  }

  /* Exchange, Queues and Bindings for publishing shipped purchase order events. */

  @Bean
  public FanoutExchange purchaseOrderShippedExchange() {
    return new FanoutExchange("purchaseorder.shipped");
  }

  @Bean
  public Queue monitoringPurchaseOrderShippedQueue() {
    return new Queue("monitoring.purchaseorder.shipped");
  }

  @Bean
  public Binding monitoringPurchaseOrderShippedBinding() {
    return BindingBuilder.bind(monitoringPurchaseOrderShippedQueue()).to(purchaseOrderShippedExchange());
  }
}
