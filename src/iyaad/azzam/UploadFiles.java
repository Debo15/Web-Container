package iyaad.azzam;

import iyaad.azzam.container.HttpServlet;
import iyaad.azzam.container.Request;
import iyaad.azzam.container.Response;

import java.io.PrintWriter;

public class UploadFiles extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        PrintWriter out = response.getPrintWriter();
        out.println(
                "<html>" +
                        "<head>" +
                        "<title>upload your name</title>" +
                        "</head>" +
                        "<body>");
        out.println(
                "<form method = 'POST'>" +
                    "<input type = 'text' name = 'first_name' value = 'Iyad'  > <br /> <br />" +
                    "<input type = 'text' name = 'last_name' value = 'Al-Azzam' > <br /><br />" +
                    "<input type = 'submit' value = 'Submit' />" +
                "</form>"
        );
        out.println("</body></html>");
    }

    @Override
    public void doPost(Request request, Response response) {
        PrintWriter out = response.getPrintWriter();
        out.println("<html><head><title>upload your name</title></head><body>");
        out.println("<h2 style = 'color: green'>Passed Successfully !!</h2>");
        out.println("<table border = 1><tr><th>name</th><th>value</th></tr>");
        out.println("<tr><td>First Name</td><td>" + request.getRequestParameter("first_name") + "</td></tr>");
        out.println("<tr><td>Last Name</td><td>" + request.getRequestParameter("last_name") + "</td></tr>");

        out.println("</table></body></html>");
    }
}
