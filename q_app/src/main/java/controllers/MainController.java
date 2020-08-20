package controllers;

import entities.Cart;
import entities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repsitries.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "MainController",urlPatterns = {"","/"})
public class MainController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private ProductRepository productRepository;
    private Cart cart;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if(productRepository == null){
            logger.error("no product repository");
            throw new ServletException("no product repository");

        }
        cart = (Cart) getServletContext().getAttribute("cart");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Index page load");
        switch (req.getServletPath()) {
            case "/":
                try {
                    req.setAttribute("products", productRepository.findAll());
                    getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
                } catch (SQLException e) {
                    logger.error("", e);
                }
                break;
            case "/new":
                req.setAttribute("product", new Product());
                getServletContext().getRequestDispatcher("/WEB-INF/product.jsp").forward(req, resp);
                break;
            case "/edit":
                String id = req.getParameter("id");
                try {
                    Product product = productRepository.findById(Integer.parseInt(id));
                } catch (SQLException e) {
                    logger.error("", e);
                }
                getServletContext().getRequestDispatcher("/WEB-INF/product.jsp").forward(req, resp);
                break;
            case "/about":
                getServletContext().getRequestDispatcher("/WEB-INF/about.jsp");
                break;
            case "/cart":
                req.setAttribute("products", cart.getProductList());
                getServletContext().getRequestDispatcher("/WEB-INF/cart.jsp").forward(req, resp);
                break;
            case "/add":
                String addId = req.getParameter("id");
                try {
                    Product product = productRepository.findById(Integer.parseInt(addId));
                    cart.addProduct(product);
                } catch (SQLException e) {
                    logger.error("", e);
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/upsert")) {
            try {
                String strId = req.getParameter("id");
                if (strId.isEmpty()) {
                    productRepository.insert(new Product(-1,
                            req.getParameter("name"),
                            req.getParameter("description"),
                            Double.parseDouble(req.getParameter("price"))));
                } else {
                    productRepository.update(new Product(Integer.parseInt(strId),
                            req.getParameter("name"),
                            req.getParameter("description"),
                            Double.parseDouble(req.getParameter("price"))));
                }
                resp.sendRedirect(getServletContext().getContextPath());
            } catch (SQLException e) {
                logger.error("", e);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
