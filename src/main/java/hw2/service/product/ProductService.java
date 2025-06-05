package hw2.service.product;

import hw2.entity.Product;

import java.util.List;

public interface ProductService {
    public Product get(long id);

    public List<Product> listAll();

    public void save(Product product);

    public void delete(long id);
}
