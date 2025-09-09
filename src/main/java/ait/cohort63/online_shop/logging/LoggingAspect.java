package ait.cohort63.online_shop.logging;

import ait.cohort63.online_shop.model.dto.ProductDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* ait.cohort63.online_shop.service.ProductServiceImpl.saveProduct(..))")
    public void saveProduct() {
        // Метод без тела, используется только для задания точки среза (PointCut)
    }

    //    @Before("saveProduct()")
    public void beforeSavingProduct() {
        logger.info("Method `saveProduct` in class ProductServiceImpl was called");
    }

    // Advice - дополнительная логика, которую мы добавляем к основной бизнес логике
    @Before("saveProduct()")
    public void beforeSavingProductWithArgs(JoinPoint joinPoint) {
        // Извлекаем параметры метода - Object[] - массив параметров вызова перехваченного метода
        // Более подробное логирование: какой метод вызван и какие параметры использованы
        Object[] params = joinPoint.getArgs();
        // Логируем информацию о вызове метода и его параметре
        logger.info("Метод saveProduct вызываем с параметром {}", params[0]);
    }

    @After("saveProduct()")
    public void afterSavingProduct() {
        logger.info("Method `saveProduct` finished its work");
    }

    // Advice After Returning -
    // After throwing -
    @AfterReturning(
            pointcut = "execution(* ait.cohort63.online_shop.service.ProductServiceImpl.getProductById(..))",
            returning = "result")
    public void afterReturningFromGetById(Object result) {
        logger.info("Method getProductById successfully returned: {}", result);
    }

    @AfterThrowing(
            pointcut = "execution(* ait.cohort63.online_shop.service.ProductServiceImpl.getProductById(..))",
            throwing = "ex"
    )
    public void afterThrowingExceptionFromGetProductById(JoinPoint joinPoint, Exception ex) {

        Object[] params = joinPoint.getArgs(); // нам нужен 0-й элемент массива

        logger.error("Method getProductById with param {} threw an exception: {}", params[0], ex.getMessage());
    }

    // Advice Around
    @Pointcut("execution(* ait.cohort63.online_shop.service.ProductServiceImpl.getAllActiveProducts(..)))")
    public void getAllProducts() {
        // пустой
    }

    // Around выполняется до, после и вместо основной логики
    @Around("getAllProducts()")
    public Object aroundGetAllActiveProducts(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = null;

        // Если нужны параметры метода
        Object[] params = joinPoint.getArgs();

        try {
            // До выполнения основного метода записываем сообщение в log
            logger.info("Method getAllProducts of class ProductServiceImpl called");
            // Выполняем основной метод и получаем результат
            result = joinPoint.proceed();
            // Логируем после успешного выполнения основного метода
            logger.info("Method getAllProducts of class ProductServiceImpl successfully returned: {}", result);

            // Если результат не нужно "править"
            // return result;

            // Изменить результат, добавляя дополнительную логику
            // отбрасываю все продукты дешевле 1.50 (оставляем продукты дороже 1.50)
            result = ((List<ProductDTO>) result).stream()
                    .filter(product -> product.getPrice().doubleValue() > 1.5)
                    .toList();

        } catch (Throwable ex) {
            // Логируем исключение, если оно произошло
            logger.error("Method getAll threw an exception: {}", ex.getMessage());
        }

        // Возвращаем (*модифицированный) результат
        return result == null ? new ArrayList<>() : result;
    }

    // ==================================

    // Любой метод в классе xxxServicexxx, название которого начинается на find
    @Pointcut("execution(* *..*Service*.find*(..))")
    void anyFindOnServiceLayer() {
    }

    // Любой метод, возвращает List чего-либо
    @Pointcut("execution(java.util.List *(..))")
    void returnsList() {
    }

    // Конкретный тип возвращаемого значения и пакет вызова методы
    @Pointcut("execution(ait.cohort63.online_shop.model.dto.ProductDTO ait.cohort63.online_shop.service..*(..))")
    void returnsProductDto() {
    }

    // Матчить контроллеры по методовым аннотациям
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    void anyGetMapping() {
    }
}
