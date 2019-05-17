package com.tcg.mis.common.util.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.DateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

public class MultiDateDeserializer extends JsonDeserializer<Date> {

    private static final String[] DATE_FORMATS = {DateUtil.PATTERN_2, "MM/dd/yyyy, hh:mm:ss a", DateUtil.PATTERN_1};
    
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String text = jsonParser.getText();
        try {
            for (String pattern : DATE_FORMATS) {
                try {
                    return DateUtil.parse(text, pattern);
                } catch (ParseException e) {
                }
            }
            throw new JsonParseException(jsonParser, "Unparseable date: \"" + text + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        } catch (JsonParseException e) {
            throw new MisBaseException(ErrorCode.REQ_ERR, text + " is not pattern for '" + DateUtil.PATTERN_1 + "'");
        }
    }
    
}
