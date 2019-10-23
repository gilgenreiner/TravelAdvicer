package service;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("TravelGuide")
public class ApplicationConfig extends ResourceConfig {
	
	public ApplicationConfig() {
		packages("bll","dal", "service"); 
		
		register(CORSResponseFilter.class);
	}
	
}
