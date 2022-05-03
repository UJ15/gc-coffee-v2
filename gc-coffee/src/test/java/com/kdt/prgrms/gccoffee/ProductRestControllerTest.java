package com.kdt.prgrms.gccoffee;

import com.google.gson.Gson;
import com.kdt.prgrms.gccoffee.controller.ProductRestController;
import com.kdt.prgrms.gccoffee.dto.CreateProductRequest;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.service.ProductService;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    Gson gson = new Gson();

    @Nested
    @DisplayName("createProduct 메서드는")
    class DescribeCreateProduct {

        @Nested
        @DisplayName("생성 요청시 존재하지 않는 Category의 상품 생성 요청이 들어오면")
        class ContextNotExistCategoryRequest {

            String url = "/api/v1/products";

            CreateProductRequest requestObject = new CreateProductRequest("a", "Hello!!", 1, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("잘못된 요청 상태를 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request).andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("생성 요청시 가격이 음수값인 상품 생성 요청이 들어오면")
        class ContextNegativePriceRequest {

            String url = "/api/v1/products";

            CreateProductRequest requestObject = new CreateProductRequest("a", "COFFEE_BEAN_PACKAGE", -1, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(requestObject));

            @Test
            @DisplayName("잘못된 요청 상태를 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request).andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("생성 요청시 이름이 없는 상품 생성 요청이 들어오면")
        class ContextNullProductNameRequest {

            String url = "/api/v1/products";

            CreateProductRequest requestObject = new CreateProductRequest("", "COFFEE_BEAN_PACKAGE", -1, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("잘못된 요청 상태를 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request).andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이름 a, 카테고리 커피, 가격 1000원의 상품 생성 요청이 들어오면")
        class ContextCreateProductRequest {

            String url = "/api/v1/products";

            CreateProductRequest requestObject = new CreateProductRequest("a", "COFFEE_BEAN_PACKAGE", 1000, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("Ok를 반환하고 서비스의 create 메소드를 호출한다.")
            void itReturnOkAndCallServiceCreate() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isOk());

                verify(productService).createProduct(any(Product.class));
            }
        }
    }
}
