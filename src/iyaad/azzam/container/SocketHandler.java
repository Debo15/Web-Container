package iyaad.azzam.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketHandler extends Thread{
    private final Socket socket;
    private final Map<String, HttpServlet> handlers;
    private final Map<String, String[]> HTTP_RESPONSE = new HashMap<>();

    public SocketHandler(Socket socket, Map<String, HttpServlet> handlers){
        this.socket = socket;
        this.handlers = handlers;
        HTTP_RESPONSE.put("200", new String[]{"OK"});
        HTTP_RESPONSE.put("404", new String[]{"Not Found", "The path you're looking for doesn't exist."});
        HTTP_RESPONSE.put("500", new String[]{ "Internal Server Error", "Can't process your request."});

    }
    @Override
    public void start(){
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Request request = new Request(in);
            out = new PrintWriter(socket.getOutputStream());
            if (!request.parse()){
                print(out, "500");
            }else {
                HttpServlet servlet = handlers.get(request.getPath());
                if (servlet == null)
                    print(out, "404");
                else{
                    Response response = new Response(socket.getOutputStream());
                    out = response.getPrintWriter();
                    print(out, "200");
                    servlet.service(request, response);
                }
            }
            out.flush();


//             System.out.println("METHOD-TYPE: " + request.getMethod());
//             System.out.println("PATH: " + request.getPath());
//             request.getRequestParams().forEach((key, val) -> System.out.println("parameter (name: " + key + ", value: " + val + ")"));
//             request.getHeaders().forEach((key, val) -> System.out.println("header (name: "+ key + ", value: " + val + ")"));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert in != null;
                in.close();
                assert out != null;
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    private void print(PrintWriter out, String type){
        out.println("HTTP/1.1 " + type + " " + HTTP_RESPONSE.get(type)[0]);
        out.println("Content-Type: text/html");
        out.println();

        if (!"200".equals(type)) {
            out.println("<html></body>");
            out.println(HTTP_RESPONSE.get(type)[1]);
            out.println("</body></html>");
        }
    }


    public Map<String, HttpServlet> getHandlers() {
        return handlers;
    }
}
