package ait.cohort63.online_shop.controller;

import ait.cohort63.online_shop.model.dto.ProductDTO;
import ait.cohort63.online_shop.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/*
Получение всех продуктов - доступно всем пользователям, включая анонимных
Получение продукта по id - только для аутентифицированных пользователей с любой ролью
Добавление продукта в БД - только админ
 */

// http://localhost:8080/products
// http://localhost:8080/api/products с префиксом для запросов к бек энду (настроено в application.yml)

@RestController
@RequestMapping("/products") // Указывает, что контроллер обрабатывает запросы связанные с ресурсом products
@Tag(name = "Product controller", description = "Controller for operations with products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create product", description = "Add new product.", tags = {"Product"})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class))})})
    @PostMapping
    public ProductDTO saveProduct(@Parameter(description = "Created product object") @RequestBody ProductDTO productDTO) {
        // обращаемся к сервису для сохранения продукта
        return service.saveProduct(productDTO);
    }

    // GET /products?id=3
    // GET /products/id чтобы не было конфликта с другим геттером по пути /products
    @Operation(summary = "Get product by id", tags = {"Product"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = ProductDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)})
    @GetMapping("/{id}")
    public ProductDTO getById(@Parameter(description = "The id that needs to be fetched", required = true) @PathVariable Long id) {
        // обращаемся к сервису и запрашиваем продукт по id
        return service.getProductById(id);
    }

    // GET /products
    @GetMapping
    public List<ProductDTO> getAll() {
        // обращаемся к сервису и запрашиваем все продукты
        return service.getAllActiveProducts();
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        // обращаемся к серверу для обновления продукта
        return service.updateProduct(id, productDTO);
    }

    // DELETE -> /products/12
    @DeleteMapping("/{productId}") // если отличает переменная пути
    public ProductDTO remove(@PathVariable("productId") Long id) {
        // обращаемся к сервису для удаления продукта
        return service.deleteProductById(id);
    }

    @DeleteMapping("/by-title") // если отличает переменная пути
    public ProductDTO removeByTitle(@RequestParam String title) {
        // обращаемся к сервису для удаления продукта
        return service.deleteProductByTitle(title);
    }

    @PutMapping("/restore/{id}")
    public ProductDTO restoreById(@PathVariable Long id) {
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