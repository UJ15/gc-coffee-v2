package com.kdt.prgrms.gccoffee.controller;

import com.google.gson.Gson;
import com.kdt.prgrms.gccoffee.dto.CreateOrderRequest;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Order;
import com.kdt.prgrms.gccoffee.models.OrderItem;
import com.kdt.prgrms.gccoffee.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderRestController.class)
public class OrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    Gson gson = new Gson();

    @Nested
    @DisplayName("createOrder 메서드는")
    class DescribeCreateOrder {

        @Nested
        @DisplayName("해당 api로 post여청을 받으면")
        class ContextCallServiceCreateOrder {

            String url = "/api/v1/orders";
            List<OrderItem> items = List.of(new OrderItem(1, Category.COFFEE_BEAN_PACKAGE, 123, 3));

            CreateOrderRequest requestObject = new CreateOrderRequest("adw@dad.com", "경기도 구리시", "1323", items);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("ok 응답을 보내고 service의 createOrder메서드를 호출한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request).andExpect(status().isOk());

                verify(orderService).createOrder(any(Order.class));
            }
        }
    }
}
