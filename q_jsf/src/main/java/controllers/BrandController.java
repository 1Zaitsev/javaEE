package controllers;

import entities.Brand;
import entities.Product;
import repsitries.BrandRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

@SessionScoped
@Named
public class BrandController implements Serializable {

    private Brand brand;

    @Inject
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() throws SQLException {
        return brandRepository.findAll();
    }

    public String editBrand(Brand brand) {
        this.brand = brand;
        return "/brand.xhtml?faces-redirect=true";
    }

    public void deleteBrand(Brand brand) throws SQLException {
        brandRepository.delete(brand.getId());
    }

    public String createBrand() {
        this.brand = new Brand();
        return "/brand.xhtml?faces-redirect=true";
    }

    public String saveBrand() throws SQLException {
        if (brand.getId() != null) {
            brandRepository.update(brand);
        } else {
            brandRepository.insert(brand);
        }
        return "/brands.xhtml?faces-redirect=true";
    }

    public Brand getBrand() {
        return brand;
    }
}
