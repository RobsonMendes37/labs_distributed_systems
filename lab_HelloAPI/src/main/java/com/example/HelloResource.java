package com.exemplo; 

import javax.inject.Inject; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType; 

@Path("/hello") 
public class HelloResource { 
    @Inject 
    private HelloService helloService; 

    @GET 
    @Path("/{name}") 
    @Produces(MediaType.TEXT_PLAIN) 
    public String sayHello(@PathParam("name") String name) { 
        return helloService.sayHello(name); 
    } 
} 