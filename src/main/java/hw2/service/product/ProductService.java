package hw2.service.product;

import hw2.entity.Product;

import java.util.List;

public interface ProductService {
    Product get(long id);

    List<Product> listAll();

    void save(Product product);

    void delete(long id);
}
