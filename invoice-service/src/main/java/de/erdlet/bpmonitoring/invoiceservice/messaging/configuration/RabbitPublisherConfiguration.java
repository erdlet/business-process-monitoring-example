package de.erdlet.bpmonitoring.invoiceservice.messaging.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitPublisherConfiguration {

  /* Exchange, Queues and Bindings for publishing messages for paid purchaseorders */

  @Bean
  public FanoutExchange purchaseOrderPaidExchange() {
    return new FanoutExchange("purchaseorder.paid");
  }

  @Bean
  public Queue monitoringPurchaseOrderPaidQueue() {
    return new Queue("monitoring.purchaseorder.paid");
  }

  @Bean
  public Binding monitoringPurchaseOrderPaidBinding() {
    return BindingBuilder.bind(monitoringPurchaseOrderPaidQueue()).to(purchaseOrderPaidExchange());
  }

  @Bean
  public Queue orderPurchaseOrderPaidQueue() {
    return new Queue("order.purchaseorder.paid");
  }

  @Bean
  public Binding orderPurchaseOrderPaidBinding() {
    return BindingBuilder.bind(orderPurchaseOrderPaidQueue()).to(purchaseOrderPaidExchange());
  }

  /* Exchange, Queues and Bindings for publishing messages for paid purchaseorders */

  @Bean
  public FanoutExchange invoiceCancelledExchange() {
    return new FanoutExchange("invoice.cancelled");
  }

  @Bean
  public Queue monitoringInvoiceCancelledQueue() {
    return new Queue("monitoring.invoice.cancelled");
  }

  @Bean
  public Binding monitoringInvoiceCancelledBinding() {
    return BindingBuilder.bind(monitoringInvoiceCancelledQueue()).to(invoiceCancelledExchange());
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
}
