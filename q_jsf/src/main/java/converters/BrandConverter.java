package converters;

import entities.Brand;
import repsitries.BrandRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class BrandConverter implements Converter<Brand> {
    @Inject
    private BrandRepository brandRepositoryRepository;

    @Override
    public Brand getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null || s.isEmpty()){
            return null;
        }
        return brandRepositoryRepository.findById(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Brand brand) {
        if(brand == null) {
            return "";
        }
        return String.valueOf(brand.getId());
    }
}
