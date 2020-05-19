package com.webroute;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Test {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            for (Method m : Routes.class.getMethods()) {
                if(m.isAnnotationPresent(WebRoute.class)) {
                    Annotation annotation = m.getAnnotation(WebRoute.class);
                    WebRoute webRoute = (WebRoute) annotation;

                    if (webRoute.path().equals(t.getRequestURI().getPath())
                            && webRoute.method().toString().equals(t.getRequestMethod())) {
                        OutputStream os = t.getResponseBody();
                        String response = m.getName();
                        t.sendResponseHeaders(200, response.length());
                        os.write(response.getBytes());
                        os.close();
                    }
                }
            }
        }
    }
}

