package com.webroute;

import static com.webroute.HttpMethod.GET;
import static com.webroute.HttpMethod.POST;

public class Routes {

    @WebRoute(method = GET, path = "/")
    public static String test2(){
        return "test20";
    }

    @WebRoute(method = GET, path="/what")
    public static String test1(){
        return "<h1>test 10</h1>".toUpperCase();
    }

    @WebRoute(method = POST, path="/")
    public static String test3(){
        return "test30";
    }
}
