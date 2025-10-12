
### <ins>SpringRabbitRelay</ins>

*Work In Progress (Experimental)*.

Proof of concept for maintaining a single application across two network zones. Updations happening in one network zone 
gets migrated to the other zone via RabbitMQ.

### Instructions to run

1. Install RabbitMQ and start the service
   
   `docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management`
2. Install MySQL and create two databases For example : app1, app2
3. Create two instances of the application by copying the source code and modifying properties in application.properties.  
    - spring.application.name to app1/app2
    - server.networktype to INTRANET/INTERNET
    - server.port to 8000/8001
    - Change the config for Datasource Configuration
5. Run the two instances via mvn spring-boot:clean
6. Insert some random data via navigating to url /populaterandom
7. Navigate to /updateRandom , the changes gets migrated to the other zone via rabbitmq.

-----


