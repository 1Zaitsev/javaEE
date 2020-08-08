package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "pageHeader", urlPatterns = "/page_header")
public class PageHeader extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("<a href='/lesson1/main'> Main </a>");
        resp.getWriter().print("<a href='/lesson1/catalog'> Catalog </a>");
        resp.getWriter().print("<a href='/lesson1/cart'> Cart </a>");
        resp.getWriter().print("<a href='/lesson1/order'> Order </a> \n");
        resp.getWriter().println("<h1>" + req.getAttribute("pageName") + "</h1>");
    }
}
