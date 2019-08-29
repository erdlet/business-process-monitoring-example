package de.erdlet.bpmonitoring.monitoringservice.rabbitmq.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class ListenerConfig implements RabbitListenerConfigurer {

  @Bean
  public Queue purchaseOrderCreatedQueue() {
    return new Queue("monitoring.purchaseorder.created");
  }

  @Bean
  public Queue purchaseOrderShippedQueue() {
    return new Queue("monitoring.purchaseorder.shipped");
  }

  @Bean
  public Queue purchaseOrderPaidQueue() {
    return new Queue("monitoring.purchaseorder.paid");
  }

  @Bean
  public Queue purchaseOrderCancelledQueue() {
    return new Queue("monitoring.purchaseorder.cancelled");
  }

  @Bean
  public Queue invoiceCancelledQueue() {
    return new Queue("monitoring.invoice.cancelled");
  }

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
