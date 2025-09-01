package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.repository.ProductRepository;
import ait.cohort63.online_shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product saveProduct(Product product) {
        product.setActive(true);
        return repository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {
//        List<Product> result = new ArrayList<>();
//        List<Product> list = repository.findAll();
//        for (Product product : list) {
//            if (product.isActive()) result.add(product);
//        }
//        return result;
        return repository.findAll().stream().filter(Product::isActive).toList();
    }

    @Override
    public Product getProductById(Long id) {
        Product product = repository.findById(id).orElse(null);

        if (product == null || !product.isActive()) {
            return null;
        }

        return product;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public Product deleteProductByTitle(String title) {
        return null;
    }

    @Override
    public Product restoreProductById(Long id) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}
