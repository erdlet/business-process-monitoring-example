package de.erdlet.bpmonitoring.invoiceservice.messaging.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitReceiverConfiguration implements RabbitListenerConfigurer {

  /* Queue for receiving purchaseorder created messages published from order service */

  @Bean
  public Queue purchaseOrderCreatedQueue() {
    return new Queue("invoice.purchaseorder.created");
  }

  /* Queue for receiving purchaseorder cancelled messages published from order service */

  @Bean
  public Queue purchaseOrderCancelledQueue() {
    return new Queue("invoice.purchaseorder.cancelled");
  }

  /* General settings for RabbitMQ / RabbitTemplate */

  @Bean
  public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
    return new MappingJackson2MessageConverter();
  }

  @Bean
  public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setMessageConverter(consumerJackson2MessageConverter());
    return factory;
  }

  @Override
  public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
    registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }
}
