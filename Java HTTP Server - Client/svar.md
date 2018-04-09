# Frågor och svar

1. Vad är skillnaden mellan GET och POST:

  answer
  
  GET: Used to retrieve information from the server. When used to submit data to the server, the request arguments, usually form data, is sent in a request header, the first line of the request. The response is cacheable and can be bookmarked.
  POST: Used to submit data to the the server for processing. The response is not cacheable and can not be bookmarked.

2. Vad är REST?

  answer
  
  Stands for Representational State Transfer.
  An architecture style for designing networked applications. It is a set of constrainsts such as being stateless, having client/server relationship, a uniform interface etc. 
  The architecture consiststs of a client and server, running a statless protocol,
  usually HTTP, where the client send a request, using HTTP methods, and server responds to that request. The resources are identified by URI.
  Different HTTP methods used are GET, POST, PUT, and DELETE in order to perform operations, such as create, retrieve, update and delete resources. These methods might be idempotent, which are methods that result in the same outcome and don not modify resources,  no matter how many times called and thus considered safe. 
  GET: It is an idempotent methods, that result in the same response, and is used to retreive a resource on the server. The response received using GET methods is cacheable.
  POST: This methods is used to send data to the server and thus can be used to create new resources. The response is not cacheable. It is not an idempotent method and can result in different resources, depending on the request parameters enclosed in POST request. As it is not considered safe, it should be handled more carefully, as compared to GET. For example, as it is used to send information to the server that can result in creating new resources, we should make sure that the request was successful.
  PUT: Is used to store a resource on the server, which is not an idempotent methods and thus should be handled more carefully similar to POST. 
  Delete: As the name suggest, this methods is used to remove a resource on the server identified by the enclosed URI. It is also not an idempotent methods and thus should be handled more carefully similar to POST and PUT. 
  HEADER: This methods is used to retrieve header informtion and is considered idempotent.
  
  Benefits of REST: Efficiency, due to cacheability, and Scalability as statelessness allows request to be routed not only to the origin server but also to cached servers.
  

3. Vad är de andra HTTP metoderna som används i REST?

  answer
  
  DELETE, HEADER, PUT, UPDATE.
