package com.kdt.prgrms.gccoffee.service;

import com.kdt.prgrms.gccoffee.models.*;
import com.kdt.prgrms.gccoffee.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Nested
    @DisplayName("createOrder 메서드는")
    class DescribeCreateOrder {

        @Nested
        @DisplayName("null인 order 객체를 인자로 받으면")
        class ContextReceiveNullObject {
            Order order = null;

            @Test
            @DisplayName("잘못된 인자 예외를 발생시킨다.")
            void itCallRepositorySave() {

                Assertions.assertThatThrownBy(() -> orderService.createOrder(order))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("order 객체를 인자로 받으면")
        class ContextReceiveOrderObject {

            List<OrderItem> items = List.of(new OrderItem(1, Category.COFFEE_BEAN_PACKAGE, 123, 3));

            Order order = new Order(new UserEmail("adw@dad.com"), "경기도 구리시", "1323", items, OrderStatus.ACCEPTED);

            @Test
            @DisplayName("repository의 save를 호출한다.")
            void itCallRepositorySave() {

                orderService.createOrder(order);

                verify(orderRepository).save(order);

            }
        }
    }


}
