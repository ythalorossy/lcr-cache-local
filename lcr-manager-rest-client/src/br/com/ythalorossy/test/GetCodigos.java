package br.com.ythalorossy.test;

import javax.ws.rs.core.UriBuilder;

import br.com.ythalorossy.dto.LCRStatusDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GetCodigos {

	private final static String URL_REST = "http://localhost:8080/lcr-manager-web/rest/lcr";
	
	public static void main(String[] args) {

		WebResource webResource = Client.create(new DefaultClientConfig()).resource(UriBuilder.fromUri(URL_REST).build());

		LCRStatusDTO[] result = webResource.path("codigos").path("status").get(LCRStatusDTO[].class);

		for (LCRStatusDTO lcrStatusDTO : result) {
			System.out.println(String.format("%s : %s", lcrStatusDTO.getCodigo(), lcrStatusDTO.getDescricao()));
		}
		
	}

}
