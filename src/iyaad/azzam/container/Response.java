package iyaad.azzam.container;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Response {
    private OutputStream out;
    private PrintWriter printWriter;
    public Response(OutputStream out){
        this.out = out;
        printWriter = new PrintWriter(out);
    }

    public OutputStream getOut() {
        return out;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }
}
