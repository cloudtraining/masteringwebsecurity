package io.cloudtraining.basicsecurity.microservice;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import org.apache.http.HttpException;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Path("/simplemicroservice")
public class SimpleMicroservice {
	public final static URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9997).build();
	
	protected static HttpServer startServer() throws IOException {
		final Map<String, String> initParams = new HashMap<String, String>();
		System.out.println("Starting JavaEE App Server - grizzly2...");
		initParams.put("com.sun.jersey.config.property.packages","io.cloudtraining.basicsecurity.microservice");
		return GrizzlyWebContainerFactory.create(BASE_URI, initParams);
	}

	public static void main(String[] args) throws HttpException, IOException, NoSuchAlgorithmException {
		HttpServer httpServer = startServer();
		System.out.println(String.format("Jersey Started - WADL at %sapplication.wadl\nHit [ENTER] to stop", BASE_URI));
		System.in.read();
		httpServer.stop();
	}

	@GET
	@Path("example")
	public String restServiceTest()
			throws HttpException, IOException, NoSuchAlgorithmException {
		return "Simplest Microservice example";
	}
	
	//LESSON: 1 - Add one more listener for a test message
	// ...
	//@Path("test2/{message}")
	//public String restServiceMessageTest(@PathParam("message") String message)

	
}
