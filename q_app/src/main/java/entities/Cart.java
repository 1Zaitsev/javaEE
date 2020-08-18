package entities;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> productList;

    public Cart() {
        this.productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addProduct(Product product){
        productList.add(product);
    }
}
