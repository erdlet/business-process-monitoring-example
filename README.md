# Business process monitoring with Camunda

## Goal
This example shows a possibility to monitor a business process which uses different services for
execution. These services provide communication via REST for some operations or use RabbitMQ
for inter-service-communication.

## The business process
![business process](https://github.com/erdlet/business-process-monitoring-example/blob/master/business_process.png)

This process describes some really simplified purchase order in an e-commerce system. After an order was created
by some user in the order-service, it has to be paid before it can be shipped. As soon as the payment was booked in the
the invoice-service, the order-service will ship it (yes, it would be another service in reallity but that would be too
much here). Also an order can be cancelled before it is paid. In this case, the order and the invoice need to be
cancelled.

## How to start up the example

1) Start the infrastructure with `docker-compose` in the project root dir:
```bash
docker-compose up -d
```

This will start a RabbitMQ instance without any bigger configuration and exposes the ports 5672 and 15672 to be accessible by
the services in the dev environment. Also there will be a MariaDB instance launched for each service.

2) Start the `order-service` via an IDE or as jar if you built it before
3) Start the `invoice-service` via an IDE or as jar if you built it before
4) Start the `monitoring-service` via an IDE or as jar if you built it before  

__Attention__: The order of the service startup is important, as the `order-service` and `invoice-service`
create their RabbitMQ exchanges, queues and bindings.

## Create and ship a purchase order
1) Create an order via `curl -X POST http://localhost:8082/purchase-orders -H 'Content-Type: application/json' -H 'Accept: application/json'`
2) Now the `monitoring-service` received the message of an newly created order and should look like this at
the process view: ![process_status](https://github.com/erdlet/business-process-monitoring-example/blob/master/created_order.png)
The order number of the created purchaseorder will be the business-key of the new Camunda process instance.
3) Get the _invoice_number_ of the newly created invoice related to the new order from the `invoice-service` which is logged to the console (e.g. 'Saved invoice <__a5903c11-a9fb-4176-aabd-1aa9cb9f2d7c__> for order <d3c479d0-3dec-4381-8152-7213476b4e35>')
4) Pay the invoice via this call to the `invoice-service`, where _invoice_number_ is the copied number from above:
`curl -X POST http://http://localhost:8083/invoices/{invoice_number}/payments -H 'Content-Type: application/json' -H 'Accept: application/json'`
5) Now call `curl -X GET http://localhost:8081/rest/engine/default/history/process-instance?finished=true` to see all finished
process instances. You will find here the process instance via the order number (remember, it's the business-key) and will see
that the state is `COMPLETED`
 
 ## Create and cancel a purchase order
 1) Create an order via `curl -X POST http://localhost:8082/purchase-orders -H 'Content-Type: application/json' -H 'Accept: application/json'`
 2) Now the `monitoring-service` received the message of an newly created order and should look like this at
 the process view: 
 ![process_status](https://github.com/erdlet/business-process-monitoring-example/blob/master/created_order.png)
 The order number of the created purchaseorder will be the business-key of the new Camunda process instance.
 3) Take the order number from the response body of the above service call or the Camunda process business key and call
 `curl -X DELETE http://localhost:8082/purchase-orders/{orderNumber} -H 'Content-Type: application/json' -H 'Accept: application/json'` 
 4) Now you should see that the cancel-order path of the process is being executed by the services and monitored in Camunda.
 5) Now call `curl -X GET http://localhost:8081/rest/engine/default/history/process-instance?finished=true` to see all finished
 process instances. You will find here the process instance via the order number (remember, it's the business-key) and will see
 that the state is `COMPLETED`
