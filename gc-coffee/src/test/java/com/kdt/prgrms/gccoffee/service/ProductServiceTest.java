package com.kdt.prgrms.gccoffee.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Nested
    @DisplayName("서비스의 createProduct 메서드는")
    class DescribeCreateProduct {

        @Nested
        @DisplayName("인자로 null을 받게 되면")
        class ContextReceiveNull {

            Product product = null;

            @Test
            @DisplayName("IllegalArgument 예외를 반환한다.")
            void itThrowIllegalArgumentException() {

                Assertions.assertThatThrownBy(() -> productService.createProduct(product))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("인자로 product 도메인을 받게 되면")
        class ContextReceiveProductDomain {

            Product product = new Product(1, "coffee", Category.COFFEE_BEAN_PACKAGE, 1000, "", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("repository의 save메서드를 호출하고 저장된 product를 반환한다.")
            void itThrowIllegalArgumentException() {

                when(productRepository.save(any(Product.class))).thenReturn(product);

                Product productCheck = productService.createProduct(product);

                verify(productRepository).save(any(Product.class));
                Assertions.assertThat(product).isEqualTo(productCheck);
            }
        }
    }

    @Nested
    @DisplayName("서비스의 createProduct 메서드는")
    class DescribeGetProducts {

        @Nested
        @DisplayName("호출이 되면")
        class ContextReceiveGetRequest {

            Product firstProduct = new Product(1, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());
            Product secondProduct = new Product(1, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            List<Product> products = List.of(firstProduct, secondProduct);

            @Test
            @DisplayName("repository의 findAll메서드를 호출하고 해당 결과를 반환한다.")
            void itThrowIllegalArgumentException() {

                when(productRepository.findAll()).thenReturn(products);

                List<Product> productsCheck = productService.getProducts();

                verify(productRepository).findAll();
                Assertions.assertThat(products).isEqualTo(productsCheck);
            }
        }
    }
}
