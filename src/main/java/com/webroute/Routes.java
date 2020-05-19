package com.webroute;

import static com.webroute.HttpMethod.GET;
import static com.webroute.HttpMethod.POST;

public class Routes {

    @WebRoute(method = GET, path = "/")
    public String test2(){
        return "test2";
    }

    @WebRoute(method = GET, path="/what")
    public String test1(){
        return "test1";
    }

}
