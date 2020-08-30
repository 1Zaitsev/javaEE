package repsitries;

import entities.Brand;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Named
public class BrandRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager entityManager;

    @Transactional
    public void insert(Brand brand){
        entityManager.persist(brand);
    }

    @Transactional
    public void update(Brand brand){
        entityManager.merge(brand);
    }

    @Transactional
    public void delete(long id){
        Brand brand = entityManager.find(Brand.class, id);
        if(brand != null)
            entityManager.remove(brand);
    }

    public Brand findById(long id){
        return entityManager.find(Brand.class, id);
    }

    public List<Brand> findAll(){
        return entityManager.createQuery("from Brand", Brand.class).getResultList();
    }
}
