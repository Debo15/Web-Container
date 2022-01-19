package iyaad.azzam.container;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Request {
    final private BufferedReader request;
    private String method;
    private String path;
    final private Map<String, String> requestParams = new HashMap<>();
    final private Map<String, String> headers = new HashMap<>();
    public Request(BufferedReader request){
        this.request = request;
    }
    public void loadParams(Map<String, String> loader, String stringQuery){
        for(String parameter: stringQuery.split("&")){
            int index = parameter.indexOf("=");
            loader.put(parameter.substring(0, index), parameter.substring(index+1));
        }
    }
    public void loadRequestParams(String stringQuery){
        if (stringQuery.isEmpty())
            return;
        loadParams(requestParams, stringQuery);
    }
    public String getRequestParameter(String name){
        return requestParams.get(name);
    }
    public boolean parse() throws IOException {
        // first line format example: GET /hello?name=IYAD&pwd=12345&join_date=2019-9-15 HTTP/1.1
        String query = request.readLine();
        if (query.isEmpty()){
            return false;
        }

        String[] queryParams = query.split(" ");
        if (queryParams.length != 3){
            return false;
        }

        // set the method.
        method = queryParams[0];

        int index = queryParams[1].indexOf("?");

        // set the path and requestPrams
        if (index < 0){
            path = queryParams[1];
        }else{
            path = queryParams[1].substring(0, index);
            loadRequestParams(queryParams[1].substring(index+1));
        }

        while(true){
            query = request.readLine();
            if (query.isEmpty()) break;
            addTo(headers, query);
        }

        if (method.equals("POST")){
            StringBuilder payload = new StringBuilder();
            while(request.ready()){
                payload.append((char) request.read());
            }
            loadRequestParams(payload.toString());
        }
        return true;
    }
    private void addTo(@NotNull Map<String, String> container, String query){
        int index = query.indexOf(":");
        container.put(query.substring(0, index), query.substring(index+1));
    }

    public Map<String, String> getRequestParams() { return requestParams; }

    public Map<String, String> getHeaders() { return headers; }

    public String getPath() { return path; }

    public String getMethod() { return method; }
}
