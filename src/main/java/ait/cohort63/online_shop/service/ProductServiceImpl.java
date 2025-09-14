package ait.cohort63.online_shop.service;

import ait.cohort63.online_shop.exception_handling.exceptions.*;
import ait.cohort63.online_shop.model.dto.ProductDTO;
import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.repository.ProductRepository;
import ait.cohort63.online_shop.service.interfaces.ProductService;
import ait.cohort63.online_shop.service.mapping.ProductMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductMappingService mapper;

    // SLF4J : самая распространенная библиотека для логирования
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

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

        // Если продукт не найден или его состояние active = false, выбрасываем исключение
        if (product == null || !product.isActive()) {
//            throw new FirstTestException("Product not active or not found");
//            throw new SecondTestException("Product not active or not found");
            throw new ThirdTestException("Product not active or not found");
//            return null;
        }

        return mapper.mapEntityToDto(product);
    }

    //    @Override
//    public ProductDTO getProductById(Long id) {
//        logger.info("Method getProductById called with parameter: {}", id);
//        logger.warn("Method getProductById called with parameter: {}", id);
//        logger.error("Method getProductById called with parameter: {}", id);
//
//        Product product = repository.findById(id).orElse(null);
//
//        // null ->
//        // true || ? -> true
//        // Product
//        // false || ? ->
//
//        if (product == null || !product.isActive()) {
//            return null;
//        }
//
//        return mapper.mapEntityToDto(product);
//
//        // false && ? -> false
//        // false & ? (будет посчитано) -> false
//    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with ID " + id + " not found."));

        if (!product.isActive()) {
            throw new ProductAlreadyDeletedException("Cannot update product with ID " + id + " because it is deleted.");
        }

        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());

        return mapper.mapEntityToDto(repository.save(product));
    }

    @Override
    public ProductDTO deleteProductById(Long id) {
        Product product = repository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product with ID " + id + " not found."));

        if (!product.isActive()) {
            throw new ProductAlreadyDeletedException("Product with ID " + id + " is already deleted.");
        }

        product.setActive(false);
        repository.save(product);
        return mapper.mapEntityToDto(product);
    }

    @Override
    public ProductDTO deleteProductByTitle(String title) {
        List<Product> products = repository.findAll();

        Product product = products.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(title) && p.isActive())
                .findFirst()
                .orElseThrow(() -> new ProductDeletionException("Active product with title '" + title + "' not found."));

        product.setActive(false);
        repository.save(product);

        return mapper.mapEntityToDto(product);
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

    @Transactional
    @Override
    public void attachImage(String imageUrl, String productTitle) {
        // Ищем продукт по названию
        Product product = repository.findByTitleIgnoreCase(productTitle)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Присваиваем продукты ссылку
        product.setImage(imageUrl);
    }
}
