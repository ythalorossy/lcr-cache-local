package br.com.ythalorossy.test;

import java.io.ByteArrayInputStream;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import br.com.ythalorossy.dto.LCRDTO;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class GetByURLTest {
	
	private final static String URL_REST = "http://localhost:8080/lcr-manager-web/rest/lcr";
	
	public static void main(String[] args) throws Exception {

//		testePerformaceCache();
		
		testeRetornoJSON(true);
		
	}

	private static void testeRetornoJSON(boolean cache) throws CRLException, CertificateException {
		
		teste(cache);
		
	}

	private static void testePerformaceCache() throws CRLException, CertificateException {
		
		long tempoTeste1 = System.currentTimeMillis();
		
		for (int i = 0; i < 100; i++) {
			teste(false);
		}
		
		tempoTeste1 = (System.currentTimeMillis() - tempoTeste1);
		
		long tempoTeste2 = System.currentTimeMillis();
		
		for (int i = 0; i < 100; i++) {
			teste(true);
		}
		
		tempoTeste2 = (System.currentTimeMillis() - tempoTeste2);
		
		System.out.println("TEMPO SEM CACHE: " + tempoTeste1 + "ms");
		System.out.println("TEMPO COM CACHE: " + tempoTeste2 + "ms");
		
		System.out.println("Diferença (SEM CACHE - CACHE): " + (tempoTeste1 - tempoTeste2) + "ms");
	}

	private static void teste(boolean cache) throws CRLException, CertificateException {
		
		WebResource webResource = Client.create(new DefaultClientConfig()).resource(UriBuilder.fromUri(URL_REST).build());

		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("url", "http://ccd.serpro.gov.br/lcr/acserprov2.crl");
		params.add("cache", String.valueOf(cache));
		
		LCRDTO lcrdto = webResource.path("url").queryParams(params).get(LCRDTO.class);
		
		if (!(lcrdto.getLcrStatusDTO().getCodigo().equals(new Integer(900)))) {
			
			byte[] decode = Base64.decode(lcrdto.getBase64());
			
			X509CRL crl = (X509CRL) CertificateFactory.getInstance("X.509").generateCRL(new ByteArrayInputStream(decode));
			
			System.out.println(crl.getIssuerDN());
			
		} else {
			
			System.out.println(lcrdto.getLcrStatusDTO().getCodigo() + " : " + lcrdto.getLcrStatusDTO().getDescricao());
			
		}

	}

}
