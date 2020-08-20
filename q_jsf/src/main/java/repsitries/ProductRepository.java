package repsitries;

import entities.Product;
import listeners.AppLaunchListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class ProductRepository {

    private Connection connection;
    @Inject
    private ServletContext context;

    @PostConstruct
    public void init(){
        connection = (Connection) context.getAttribute("connection");
    }

    public void insert(Product product) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into products (name, description, price) values (?, ?, ?);")){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("update products set name = ?, description = ?, price = ? ;")){
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.execute();
        }
    }

    public void delete(int id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from products where id = ? ;")){
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        }
    }

    public Product findById(int id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("select id,name, description, price where id = ?;")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4));
            }
        }
        return null;
    }

    public List<Product> findAll() throws SQLException {
        List<Product> productList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, name, description, price from products;")){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                productList.add(new Product(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4)));
            }
        }
        return productList;
    }
}
