package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "main", urlPatterns = "/main")
public class MainPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<String> headers = new ArrayList<String>();
//        Enumeration<String> enumeration = req.getHeaderNames();
//        while(enumeration.hasMoreElements()){
//            headers.add(enumeration.nextElement());
//        }
//        for (String header: headers) {
//            resp.getWriter().println(header);
//        }

        req.setAttribute("pageName", "Main");
        getServletContext().getRequestDispatcher("/page_header").include(req, resp);
        resp.getWriter().println("<h2>Welcome!</h2>");
        resp.getWriter().println("<p>This is a tiny website to become acquainted with servlets.</p>");
    }
}
