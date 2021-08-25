package com.rijai.nlds.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



/**
 * ExampleController
 * 
 * @author Leo Gabriel
 *
 */
@RestController
@RequestMapping("/api/ext/v1")

public class NldsController {
	//Renderer renderer = new Renderer();
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private static Map<String, String> idSecrets = new HashMap<>();
	private static Map<String, String> token_idSecrets= new HashMap<>();

	
	@RequestMapping(value="/hello-world", method = RequestMethod.GET)
	public ResponseEntity<String> get() {
		return ResponseEntity.ok("Hello World!");
	}	
	
	@RequestMapping(value="/authenticate", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}, 
	        produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> authenticate(@RequestHeader Map<String, String> headers) {
		String client_id = headers.get("client_id");
		String client_secret = headers.get("client_secret");
		UUID guid = UUID.randomUUID ();
		idSecrets.put(client_id + "-" + client_secret, guid.toString());
		token_idSecrets.put(guid.toString(), client_id + "-" + client_secret);
		LOG.debug("successfully authenticated " + client_id + "\"-\" + client_secret");
		return ResponseEntity.ok("{\"access_token\":\""+guid.toString()+"\",\"token_type\":\"bearer\",\"expires_in\":3598,\"scope\":\"entity.8adc9dee-6ccc-adb4-016c-d18f6ec133b3 entity.8adc9dee-6ccc-adb4-016c-d19054dc35e8 entity.8adc9dee-73be-e6f5-0173-c24242564756 entity.8adce421-6b72-ae05-016b-7552882127f8 platform.write service.events.read service.events.write service.genesis.read service.genesis.write service.notifications.read service.notifications.write service.usage.delete service.usage.update service.usage.write tenant.2000475 user.8adcd9eb778c215b0177a53ab7107ea9\",\"jti\":\"76d7a553d6384e73bcde38b421dcb224\"}");
	}

	@RequestMapping(value="/nlds/search", method = RequestMethod.GET)
	public ResponseEntity<String> search( @PathVariable Map<String, String> urlParams, @RequestHeader Map<String, String> headers){
		String token = headers.get("Authorization");		
		LOG.debug("URL parameters received:" + urlParams + ":" + token);
		if(validateToken(token))
		{
			return ResponseEntity.ok("{ {\"criteria\"} {\""+ urlParams.get("criteria") + "\"} }");				
		}
		else
		{
			return ResponseEntity.ok("{ {\"criteria\"} {\""+ urlParams.get("criteria") + "\"} }");				
		}		
	}

	@RequestMapping(value="/nlds/account/{clientid}", method = RequestMethod.GET)
	public ResponseEntity<String> details(@PathVariable String clientid, @RequestHeader Map<String, String> headers) {
		String token = headers.get("Authorization");
		
		LOG.debug("URL parameters received:" + clientid+ ":" + token);
		return ResponseEntity.ok("{ {\"Details of account\"},{\""+ clientid + "\"} {\""+ token + "\"} }");
	}

	
	@RequestMapping(value="/pep/search?{query}", method = RequestMethod.GET)
	public ResponseEntity<String> pepSearch(@PathVariable Map<String, String> urlParams, @RequestHeader HttpServletRequest request) {
		String token = request.getHeader("Authorization");		
		return ResponseEntity.ok("{ { \"criteria\"},{\"" + urlParams.get("criteria") +"\"} {\"token\"},{\""+ token + "\"} }");
	}
	
	private boolean validateToken(String token)
	{
		if(token_idSecrets.containsKey(token))
		{
			//TODO check the expiration, true for now
			return true;
		}
		else
		{
			return false;
		}
	}	
}
