package listeners;

import entities.Cart;
import entities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repsitries.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppLaunchListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppLaunchListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Application launched");
        ServletContext context = sce.getServletContext();
        String jdbcConnectionString = context.getInitParameter("jdbcConnectionString");
        String username = context.getInitParameter("username");
        String password = context.getInitParameter("password");
        Cart cart = new Cart();
        context.setAttribute("cart", cart);
        try {
            Connection connection = DriverManager.getConnection(jdbcConnectionString, username,password);
            ProductRepository productRepository = new ProductRepository(connection);
            context.setAttribute("connection", connection);
            context.setAttribute("productRepository", productRepository);
        } catch (SQLException e) {
            logger.error("", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Connection connection = (Connection) context.getAttribute("connection");
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("", e);
        }
    }
}
