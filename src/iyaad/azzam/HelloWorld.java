package iyaad.azzam;

import iyaad.azzam.container.HttpServlet;
import iyaad.azzam.container.Request;
import iyaad.azzam.container.Response;

import java.io.PrintWriter;

public class HelloWorld extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        PrintWriter out = response.getPrintWriter();
        out.println("<html><body><h1>everything works fine XD WOWWWW </h1></body></html>");
    }
}
