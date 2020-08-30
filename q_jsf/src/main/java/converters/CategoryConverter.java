package converters;

import entities.Category;
import org.hibernate.engine.config.spi.ConfigurationService;
import repsitries.CategoryRepository;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class CategoryConverter implements Converter<Category> {
    @Inject
    private CategoryRepository categoryRepository;

    @Override
    public Category getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null || s.isEmpty()){
            return null;
        }
        return categoryRepository.findById(Long.parseLong(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Category category) {
        if(category == null) {
            return "";
        }
        return String.valueOf(category.getId());
    }
}
