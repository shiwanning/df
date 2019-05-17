package com.tcg.mis.common.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.tcg.mis.common.constant.ErrorCode;
import com.tcg.mis.common.exception.MisBaseException;
import com.tcg.mis.common.util.DateUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class StringFormatDateDeserialize extends JsonDeserializer<Date> {

    private String pattern;
    
    protected StringFormatDateDeserialize(String pattern) {
        this.pattern = pattern;
    }
    
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String text = jsonParser.getText();
        try {
            return DateUtil.parse(text, pattern);
        } catch (ParseException e) {
            throw new MisBaseException(ErrorCode.REQ_ERR, text + " is not pattern for '" + DateUtil.PATTERN_1 + "'");
        }
    }

    public static class StringDateDeserialize extends StringFormatDateDeserialize {
        public StringDateDeserialize() {
            super(DateUtil.PATTERN_1);
        }
    }
    
    public static class StringDateTimeDeserialize extends StringFormatDateDeserialize {
        public StringDateTimeDeserialize() {
            super(DateUtil.PATTERN_2);
        }
    }
    
    public static class MillisecondsDeserialize extends StringFormatDateDeserialize {
        public MillisecondsDeserialize() {
            super(DateUtil.PATTERN_7);
        }
    }
    
}
