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
        public void handle(HttpExchange httpExchange) throws IOException {

            for (Method m : Routes.class.getMethods()) {
                if(m.isAnnotationPresent(WebRoute.class)) {
                    Annotation annotation = m.getAnnotation(WebRoute.class);
                    WebRoute webRoute = (WebRoute) annotation;

                    if (webRoute.path().equals(httpExchange.getRequestURI().getPath())
                            && webRoute.method().toString().equals(httpExchange.getRequestMethod())) {
                        OutputStream os = httpExchange.getResponseBody();
                        String response = m.getName();
                        try {
                            response = m.invoke(null).toString();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        httpExchange.sendResponseHeaders(200, response.length());
                        os.write(response.getBytes());
                        os.close();

                    }
                }
            }
        }
    }
}

