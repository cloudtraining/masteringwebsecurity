package io.cloudtraining.basicsecurity.microservice;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Path("/hmacservice")
public class HmacService {
	public final static URI BASE_URI = UriBuilder.fromUri("http://localhost/").port(9998).build();
	
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
    @Path("hmac/{userid}")
	@Consumes("text/plain")
	@Produces(MediaType.APPLICATION_JSON)
    public String restServiceTest(@PathParam("userid") String userid)
            throws Exception {
        
        // Define server response message.
        String dataReturnedFromServer = "This is a test message generated by the server";

		// Given the userid (use: getSecretKeyFromID ), get the secret key in a string variable - note this is simulated lookup.
		//TODO
        String secretKey = getSecretKeyFromID(userid);
        
		// Create a timestamp.
		String timestamp = Long.toString((new Date()).getTime());
		
		//Create MD5 HMAC encryption key (use: calculateMD5Hmac ) from secret key and timestamp.
		//TODO
		byte[] calcHmacKey = calculateMD5Hmac(secretKey, timestamp.getBytes() );
		
		// Encrypt the server response of dataReturnedFromServer (use: encrypt ) into a variable called encryptedData with the encryption key.
		//TODO
		byte[] encryptedData = encrypt(calcHmacKey, dataReturnedFromServer.getBytes() );
		
		// Compute the ShaHmac signature of the data we want to return. Compute over timestamp and encrypted body.
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byteArrayOutputStream.write(timestamp.getBytes());
		byteArrayOutputStream.write(encryptedData);
        //TODO String hmacResult = ?
		String hmacResult = calculateSha256Hmac(secretKey, dataReturnedFromServer.getBytes() );
		
        // Return data and hmac.
		//TODO Create ServiceResponse and set values.
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userid", userid);
		jsonObject.put("encryptedData", encryptedData);
		jsonObject.put("hmacResult", hmacResult);
		jsonObject.put("timestamp", timestamp);
		
		//String json = EntityUtils.toString(serviceResponse);
				
        return jsonObject.toString(5);
	
    }
	
	
	private String getSecretKeyFromID(String userid) {
		return "125-43157-543134-123451234-4";
	}
	
	//Used to Create Encryption Keys
	private byte[] calculateMD5Hmac(String secret, byte[] data) {
    	
    	try {
	    	SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacMD5");   	
	    	Mac mac = Mac.getInstance("HmacMD5");
	        mac.init(signingKey);
	        return mac.doFinal(data);          
    	} catch (GeneralSecurityException gse) {
            throw new IllegalArgumentException();    		
    	}

    }
    
	private byte[] encrypt(byte[] secretKey, byte[] textToEncode) throws Exception {
		Cipher c = Cipher.getInstance("AES");
		SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "AES");
		c.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		return c.doFinal(textToEncode);
	}
	
	//Used to Create HMAC Signatures
    private String calculateSha256Hmac(String secret, byte[] data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data);          
            String result = new String(Base64.encodeBase64(rawHmac));
            return result;
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException();
        }
    }	

}