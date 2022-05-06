package com.kdt.prgrms.gccoffee.repository;


import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = ProductJdbcRepositoryTest.Config.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductJdbcRepositoryTest {

    @Configuration
    @EnableAutoConfiguration
    static class Config {
        @Bean
        public ProductJdbcRepository productJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

            return new ProductJdbcRepository(namedParameterJdbcTemplate);
        }
    }
    @Autowired
    private ProductJdbcRepository productJdbcRepository;

    @Nested
    @Order(1)
    @DisplayName("save 메서드는")
    class DescribeSaveMethod {

        @Nested
        @Order(1)
        @DisplayName("인자로 null인 product를 받으면")
        class ContextReceiveNullProduct {

            Product product = null;

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itThrowIllegalArgumentException() {

                Assertions.assertThatThrownBy(() -> productJdbcRepository.save(product))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @Order(2)
        @DisplayName("인자로 product를 받으면")
        class ContextReceiveProduct {

            Product product = new Product("coffee", Category.COFFEE_BEAN_PACKAGE, 1000, "");

            @Test
            @DisplayName("해당 product를 Database에 저장한다.")
            void itSaveProduct() {

                Product productCheck = productJdbcRepository.save(product);

                Assertions.assertThat(productCheck.getProductName()).isEqualTo("coffee");
                Assertions.assertThat(productCheck.getPrice()).isEqualTo(1000);
            }
        }
    }

    @Nested
    @Order(2)
    @DisplayName("findAll 메서드는")
    class DescribeFindAllMethod {

        @Nested
        @DisplayName("호출되면")
        class ContextCallThis {

            @Test
            @DisplayName("해당 product를 Database에 저장한다.")
            void itSaveProduct() {

                List<Product> products = productJdbcRepository.findAll();

                Assertions.assertThat(products.size()).isNotEqualTo(0);
            }
        }
    }
}
