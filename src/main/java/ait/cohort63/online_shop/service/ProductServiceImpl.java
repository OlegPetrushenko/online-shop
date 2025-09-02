package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.model.dto.ProductDTO;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.repository.ProductRepository;
import ait.cohort63.online_shop.service.interfaces.ProductService;
import ait.cohort63.online_shop.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMappingService mapper;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = mapper.mapDtoToEntity(productDTO);
//        product.setActive(true);
        return mapper.mapEntityToDto(repository.save(product));
    }

    @Override
    public List<ProductDTO> getAllActiveProducts() {
//        List<Product> result = new ArrayList<>();
//        List<Product> list = repository.findAll();
//        for (Product product : list) {
//            if (product.isActive()) result.add(product);
//        }
//        return result;
        return repository.findAll().stream()
                .filter(Product::isActive)
                // маппинг каждого продукта в DTO .map(entity -> mapper.mapEntityToDto(entity))
                .map(mapper::mapEntityToDto)
                .toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = repository.findById(id).orElse(null);

        if (product == null || !product.isActive()) {
            return null;
        }

        return mapper.mapEntityToDto(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductDTO deleteProductById(Long id) {
        return null;
    }

    @Override
    public ProductDTO deleteProductByTitle(String title) {
        return null;
    }

    @Override
    public ProductDTO restoreProductById(Long id) {
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
