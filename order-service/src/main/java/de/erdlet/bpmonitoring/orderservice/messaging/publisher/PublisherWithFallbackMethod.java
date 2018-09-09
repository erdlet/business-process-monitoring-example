package de.erdlet.bpmonitoring.orderservice.messaging.publisher;

public interface PublisherWithFallbackMethod<T> {

  String DEFAULT_ROUTING_KEY = "";

  /**
   * Name of the Hystrix fallback method.
   */
  String FALLBACK_METHOD_NAME = "handlePublishFailure";

  /**
   * Method for publishing an domain event to a messaging middleware.
   *
   * @param input domain object containing relevant information for the message to be published
   */
  void publishEvent(T input);

  /**
   * Fallback method for Hystrix which handles calls that can't reach the messaging middleware.
   *
   * @param input the input parameter of the {@link PublisherWithFallbackMethod#publishEvent(Object)} method
   */
  void handlePublishFailure(T input);

}
