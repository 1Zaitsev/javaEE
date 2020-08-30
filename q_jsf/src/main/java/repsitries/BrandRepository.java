package repsitries;

import entities.Brand;
import entities.Product;

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
public class BrandRepository {

    private Connection connection;

    @Inject
    private ServletContext context;

    @PostConstruct
    public void init(){
        this.connection = (Connection) context.getAttribute("connection");
    }

    public void insert(Brand brand) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into brands (title, description) values (?, ?);")){
            preparedStatement.setString(1, brand.getTitle());
            preparedStatement.setString(2, brand.getDescription());
            preparedStatement.execute();
        }
    }

    public void update(Brand brand) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("update brands set title = ?, description = ? where id = ?;")) {
            preparedStatement.setString(1, brand.getTitle());
            preparedStatement.setString(2, brand.getDescription());
            preparedStatement.setLong(3, brand.getId());
            preparedStatement.execute();
        }
    }

    public void delete(Long id) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from brands where id = ?;")) {
            preparedStatement.setLong(1, id);
        }
    }

    public List<Brand> findAll() throws SQLException {
        List<Brand> brandList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, title, description from brands;")){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                brandList.add(new Brand(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        return brandList;
    }
}
