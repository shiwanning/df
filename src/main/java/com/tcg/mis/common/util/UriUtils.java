package com.tcg.mis.common.util;

public class UriUtils {

    public static String getModule(String uri) {

        String project = uri.split("/")[1].replace("-","");
        String module;

        if(uri.contains("v2")){
            module = uri.split("/")[3];
        }else{
            module =  uri.split("/")[2];
        }
        return project + "." + module + ".";
    }
}
