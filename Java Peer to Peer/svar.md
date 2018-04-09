# Frågor och svar

1. Om du har N anslutna klienter, hur många tråd-objekt behövs i:
  1. Server?
  
    answer
Server needs N + 1 threads. N for clients and one (which might be the main thread) to listen for incoming connections.
  2. Klient?

    answer
    Client needs three threads, one is main thread, the second is to take input from the client and the third to listen for the incoming data from the clients.
    

2. Vad är runnable i Java?

  answer
  Its an interface that has an abstract method run(). If an object want to execute in a separate thread, it has to implement this interface.

3. Vad är nyckelordet synchronized i java?

  answer
  When diffrent threads are using a shared object, synchronized is used to allow only one thread at a time to access the object. Thus it prevent the other threads or the application from acessing and manipulating the data in an in appropriate way.
  

4. Beskriv de fyra skikten i TCP / IP-protokollstacken.
  
  answer
Physical Layer: Provide physical connection between communicating nodes. It is used to transfer raw bits in the form of electrical signals over the communcation media.
  
Network Layer: Is responsible to route the data between the networks, using IP addresses.
Transport Layer: Consists of TCP and UD protocol, and is used to collect and send the data to the application layer.
Application Layer: Is used to provide interface to the user, in order to take input from the user and display information to the user.

5. Vad betyder flaggorna, ACK, SYN och SEQ och vilket protokoll hörde till?

  answer
  These flags are used in TCP handshake. SYN is used by the client to connect to the server, and in response the ACK is sent by the server. The SEQ is used to differentialte between different sessions.
  
6. Vad är skillnaden mellan TCP och UDP?
TCP connection oriented protocol, and is reliable, which means if the data is lost, the sender will resend the data. TCP is also send data in order.
UDP on the other hand is connection less, the sender and receiver do not need to establish a connection. It is also unreliable, which means if the data is lost, the sender does not know about this.



