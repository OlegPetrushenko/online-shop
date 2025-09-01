package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.model.entity.Product;
import ait.cohort63.online_shop.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

// http://localhost:8080/products
// http://localhost:8080/api/products с префиксом для запросов к бек энду (настроено в application.yml)

@RestController
@RequestMapping("/products") // Указывает, что контроллер обрабатывает запросы связанные с ресурсом products
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        // обращаемся к сервису для сохранения продукта
        return service.saveProduct(product);
    }

    // GET /products?id=3
    // GET /products/id чтобы не было конфликта с другим геттером по пути /products
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        // обращаемся к сервису и запрашиваем продукт по id
        return service.getProductById(id);
    }

    // GET /products
    @GetMapping
    public List<Product> getAll() {
        // обращаемся к сервису и запрашиваем все продукты
        return service.getAllActiveProducts();
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        // обращаемся к серверу для обновления продукта
        return service.updateProduct(id, product);
    }

    // DELETE -> /products/12
    @DeleteMapping("/{productId}") // если отличает переменная пути
    public Product remove(@PathVariable("productId") Long id) {
        // обращаемся к сервису для удаления продукта
        return service.deleteProductById(id);
    }

    @DeleteMapping("/by-title") // если отличает переменная пути
    public Product removeByTitle(@RequestParam String title) {
        // обращаемся к сервису для удаления продукта
        return service.deleteProductByTitle(title);
    }

    @PutMapping("/restore/{id}")
    public Product restoreById(@PathVariable Long id) {
        return service.restoreProductById(id);
    }

    @GetMapping("/count")
    public long getProductCount() {
        return service.getProductCount();
    }

    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return service.getTotalPrice();
    }

    @GetMapping("/average-price")
    public BigDecimal getAveragePrice() {
        return service.getAveragePrice();
    }
}

//Наименование эндпоинтов:

// POST /products : POST - действие, /products - определяет ресурс с которым совершается действие
// GET /products/2
// PUT /products/3
// DELETE /products/1

// Допускается: /order/12/cancel