package com.kdt.prgrms.gccoffee.repository;


import com.kdt.prgrms.gccoffee.models.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = {OrderJdbcRepositoryTest.Config.class})
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderJdbcRepositoryTest {

    @Configuration
    @EnableAutoConfiguration
    static class Config {
        @Bean
        public OrderJdbcRepository orderJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {

            return new OrderJdbcRepository(namedParameterJdbcTemplate);
        }
    }
    @Autowired
    private OrderJdbcRepository orderJdbcRepository;

    @Nested
    @Order(1)
    @DisplayName("save 메서드는")
    class DescribeSaveMethod {

        @Nested
        @Order(1)
        @DisplayName("인자로 null인 order를 받으면")
        class ContextReceiveNullOrder {


            com.kdt.prgrms.gccoffee.models.Order order = null;


            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itThrowIllegalArgumentException() {

                org.assertj.core.api.Assertions.assertThatThrownBy(() -> orderJdbcRepository.save(order))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @Order(2)
        @DisplayName("인자로 order를 받으면")
        class ContextReceiveOrder {

            Product product = new Product("coffee", Category.COFFEE_BEAN_PACKAGE, 1000, "");

            List<OrderItem> items = List.of(new OrderItem(1, Category.COFFEE_BEAN_PACKAGE, 1000, 1));

            com.kdt.prgrms.gccoffee.models.Order order = new com.kdt.prgrms.gccoffee.models.Order(new UserEmail("adw@dad.com"), "경기도 구리시", "1323", items, OrderStatus.ACCEPTED);

            @Test
            @DisplayName("해당 order를 Database에 저장한다.")
            void itSaveProduct() {

                com.kdt.prgrms.gccoffee.models.Order orderCheck = orderJdbcRepository.save(order);

                org.assertj.core.api.Assertions.assertThat(orderCheck.getAddress()).isEqualTo("경기도 구리시");
                org.assertj.core.api.Assertions.assertThat(orderCheck.getPostcode()).isEqualTo("1323");
            }
        }
    }
}
