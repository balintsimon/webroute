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
        return "test10";
    }

    @WebRoute(method = POST, path="/")
    public static String test3(){
        return "test30";
    }
}
