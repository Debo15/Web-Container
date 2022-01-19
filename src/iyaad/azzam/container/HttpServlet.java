package iyaad.azzam.container;

import java.io.PrintWriter;

public abstract class HttpServlet {

    public void init(){
        System.out.println("inti impl...");
    }

    public void service(Request request, Response response){
        String method = request.getMethod();
        if ("GET".equals(method)){
            doGet(request, response);

        } else if ("POST".equals(method)){
            doPost(request, response);
        }else
            throw new RuntimeException("Method isn't supported yet !");
    }
    public void doGet(Request request, Response response){
        System.out.println("doGet impl...");
    }
    public void doPost(Request request, Response response){
        PrintWriter out = response.getPrintWriter();
        out.println("this is the default post page for HTTPServlet");
    }

    public void destroy() {
        /**
         * in this method we close connections(if exist) with database, etc ...
         */
        System.out.println("destroy the connections ...");
    }
}
