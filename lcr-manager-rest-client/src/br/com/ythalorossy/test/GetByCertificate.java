package br.com.ythalorossy.test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class GetByCertificate {

	private final static String URL_REST = "http://localhost:8080/lcr-manager-web/rest/lcr";

	public static void main(String[] args) throws CertificateException, IOException {

		Certificate cert = generateCertificate();

		byte[] bytes = Base64.encode(cert.getEncoded());
		
		String certBase64 = new String(bytes, "UTF-8");
		
		WebResource webResource = Client.create(new DefaultClientConfig()).resource(UriBuilder.fromUri(URL_REST).build());
		
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("certificateBase64", certBase64);
		params.add("cache", String.valueOf(true));
		
		String result = webResource
				.path("base64")
				.queryParams(params)
				.post(String.class);

		System.out.println(result);

	}

	private static Certificate generateCertificate() throws FileNotFoundException, IOException, CertificateException {
		
		FileInputStream fis = new FileInputStream("C:\\monografia\\teste.cer");
		
		DataInputStream dis = new DataInputStream(fis);
		
		byte[] bytes = new byte[dis.available()];
		dis.readFully(bytes);
		dis.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		Certificate cert = cf.generateCertificate(bais);
		
		return cert;
	}

}
