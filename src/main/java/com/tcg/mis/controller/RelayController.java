package com.tcg.mis.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.JsonUtils;
import com.tcg.mis.service.proxy.UrlPathMappingService;
import com.tcg.mis.to.PathMapping;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/relay")
@Api(tags = "relay", description = "把 request pass 到另一个系统")
@Validated
public class RelayController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RelayController.class);

	@Autowired
	private UrlPathMappingService urlPathMappingService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/get/{key}")
    public ResponseEntity<?> proxy(@RequestHeader MultiValueMap<String, String> headers, @PathVariable("key") String key, HttpServletRequest request) throws URISyntaxException, IOException {

        PathMapping pm = urlPathMappingService.getPathMapping(key);
        
        if (pm == null) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "无此路径");
        }

        URI url = new URI("http", null, pm.getServer(), pm.getPort(), pm.getTargetPath(), request.getQueryString(), null);

        ResponseEntity<?> resp;
        
        // 修正 Authorization
        if(headers.get("authorization") != null) {
        	headers.put("Authorization", headers.get("authorization"));
        }
        try {
        	LOGGER.info("relay to url: {}", url);
            resp = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(headers), String.class);
        } catch (HttpClientErrorException exp) {
        	LOGGER.info("url {} have httpClientError: {}", url, exp);
            resp = new ResponseEntity(exp.getResponseBodyAsString(), exp.getResponseHeaders(), exp.getStatusCode());
        } catch (HttpServerErrorException exp) {
        	LOGGER.info("url {} have httpServerError: {}", url, exp);
            resp = new ResponseEntity(exp.getResponseBodyAsString(), exp.getResponseHeaders(), exp.getStatusCode());
        }
        if(JsonUtils.isJson(resp.getBody())) {
            HttpHeaders hh = new HttpHeaders();
            for(Entry<String, List<String>> entry : resp.getHeaders().entrySet()) {
                for(String ev : entry.getValue()) {
                    hh.set(entry.getKey(), ev);
                }
            }
            hh.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(JsonUtils.getJsonNodeFromJson(resp.getBody().toString()), hh, resp.getStatusCode());
        }
        return resp;
    }
	
	@PostMapping("/post/{key}")
    public ResponseEntity<?> postProxy(@RequestBody(required = false) JsonNode body, @RequestHeader MultiValueMap<String, String> headers, @PathVariable("key") String key, HttpServletRequest request) throws URISyntaxException, IOException {

        PathMapping pm = urlPathMappingService.getPathMapping(key);
        
        if (pm == null) {
            throw new MisBaseException(ErrorCode.SYS_ERR, "无此路径");
        }

        URI url = new URI("http", null, pm.getServer(), pm.getPort(), pm.getTargetPath(), request.getQueryString(), null);

        ResponseEntity<?> resp;
        
        // 修正 Authorization
        if(headers.get("authorization") != null) {
        	headers.put("Authorization", headers.get("authorization"));
        }
        try {
        	String sbody = body == null ? null : body.toString();
            if(sbody != null) {
                headers.remove("content-length");
                headers.add("content-length", String.valueOf(sbody.length()));
            }
            resp = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<String>(sbody, headers), String.class);
        } catch (HttpClientErrorException exp) {
        	LOGGER.info("url {} have httpClientError: {}", url, exp);
            resp = new ResponseEntity(exp.getResponseBodyAsString(), exp.getResponseHeaders(), exp.getStatusCode());
        } catch (HttpServerErrorException exp) {
        	LOGGER.info("url {} have httpServerError: {}", url, exp);
            resp = new ResponseEntity(exp.getResponseBodyAsString(), exp.getResponseHeaders(), exp.getStatusCode());
        }
        if(JsonUtils.isJson(resp.getBody())) {
            HttpHeaders hh = new HttpHeaders();
            for(Entry<String, List<String>> entry : resp.getHeaders().entrySet()) {
                for(String ev : entry.getValue()) {
                    hh.set(entry.getKey(), ev);
                }
            }
            hh.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(JsonUtils.getJsonNodeFromJson(resp.getBody().toString()), hh, resp.getStatusCode());
        }
        return resp;
    }
	
}
