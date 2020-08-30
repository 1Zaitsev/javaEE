package repsitries;

import entities.Category;
import entities.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Named
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    @Transactional
    public void insert(Category category){
        entityManager.persist(category);
    }

    @Transactional
    public void update(Category category){
        entityManager.merge(category);
    }

    @Transactional
    public void delete(long id){
        Category category = entityManager.find(Category.class, id);
        if(category != null)
            entityManager.remove(category);
    }

    public Category findById(long id){
        return entityManager.find(Category.class, id);
    }

    public List<Category> findAll(){
        return entityManager.createQuery("from Category", Category.class).getResultList();
    }
}
