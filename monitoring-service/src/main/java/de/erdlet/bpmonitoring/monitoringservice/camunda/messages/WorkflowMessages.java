package de.erdlet.bpmonitoring.monitoringservice.camunda.messages;

public interface WorkflowMessages {

  String ORDER_RECEIVED = "ORDER_RECEIVED";
  String ORDER_PAID = "ORDER_PAID";
  String ORDER_SHIPPED = "ORDER_SHIPPED";
  String ORDER_CANCELLED = "ORDER_CANCELLED";
  String INVOICE_CANCELLED = "INVOICE_CANCELLED";
}
