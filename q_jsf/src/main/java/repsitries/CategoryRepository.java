package repsitries;

import entities.Category;

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
public class CategoryRepository {

    private Connection connection;

    @Inject
    private ServletContext context;

    @PostConstruct
    public void init(){
        this.connection = (Connection) context.getAttribute("connection");
    }

    public void insert(Category category) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into categories (title) values (?);")){
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.execute();
        }
    }

    public void update(Category category) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement("update categories set title = ? where id = ?;")){
            preparedStatement.setString(1, category.getTitle());
            preparedStatement.setLong(2, category.getId());
            preparedStatement.execute();
        }
    }

    public void delete(Long id) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from categories where id = ?")){
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        }
    }

    public List<Category> findAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, title from categories;")){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                categoryList.add(new Category(resultSet.getLong(1), resultSet.getString(2)));
            }
        }
        return categoryList;
    }


}
