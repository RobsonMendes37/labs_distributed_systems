package com.exemplo; 

import javax.ws.rs.ApplicationPath; 
import javax.ws.rs.core.Application; 

//caminho base da API REST 
@ApplicationPath("/api") 
public class RestApplication extends Application { 
}