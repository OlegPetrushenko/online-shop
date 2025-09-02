package ait.cohort63.online_shop.service.interfaces;

import ait.cohort63.online_shop.model.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ProductDTO saveProduct(ProductDTO productDTO);

    List<ProductDTO> getAllActiveProducts();

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    ProductDTO deleteProductById(Long id);

    ProductDTO deleteProductByTitle(String title);

    ProductDTO restoreProductById(Long id);

    long getProductCount();

    BigDecimal getTotalPrice();

    BigDecimal getAveragePrice();
}
