package com.tcg.mis.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.tcg.mis.common.response.BaseResponseT;
import com.tcg.mis.common.util.JsonUtils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON)
@Api(tags = "version", description = "版本相关资讯")
@Validated
public class VersionController {

    private static JsonNode versionInfo = null;

    @GetMapping(value = "version")
    @ApiOperation(value = "发版相关讯息")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "success")})
    public BaseResponseT<JsonNode> version() throws IOException {
        if(versionInfo == null) {
            String json = StreamUtils.copyToString( new ClassPathResource("version.json").getInputStream(), Charset.forName("UTF-8"));
            versionInfo = JsonUtils.getJsonNodeFromJson(json);
        }
        return new BaseResponseT<>(versionInfo);
    }
}
