package com.tcg.mis.service.proxy.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcg.mis.service.proxy.UrlPathMappingService;
import com.tcg.mis.to.PathMapping;

@Service
public class UrlPathMappingServiceImpl implements UrlPathMappingService {

	@Autowired
    private Map<String, PathMapping> pathMappings;
	
	@Override
	public PathMapping getPathMapping(String key) {
		return pathMappings.get(key);
	}

}
