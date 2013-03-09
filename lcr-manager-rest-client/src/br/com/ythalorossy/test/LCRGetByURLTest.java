package br.com.ythalorossy.test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LCRGetByURLTest {

	private final static String URL_REST = "http://localhost:8080/lcr-manager-web/rest/lcr";

	public static void main(String[] args) throws Exception {

		WebResource webResource = Client.create().resource(UriBuilder.fromUri(URL_REST).build());

		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("url", "http://ccd.serpro.gov.br/lcr/acserprov2.crl");
		params.add("cache", String.valueOf(true));
		
		ClientResponse clientResponse = webResource.path("{url}/{cache}").queryParams(params).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		
		if (clientResponse.getStatus() != 200) {
			
			throw new RuntimeException("Failed : HTTP error code : " + clientResponse.getStatus());
		}

		String output = clientResponse.getEntity(String.class);

		System.out.println("Output from Server .... \n");
		System.out.println(output);		
	}

}
