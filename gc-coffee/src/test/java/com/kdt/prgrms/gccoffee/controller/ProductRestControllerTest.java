package com.kdt.prgrms.gccoffee.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kdt.prgrms.gccoffee.GsonLocalDateTimeAdapter;
import com.kdt.prgrms.gccoffee.dto.CreateProductRequest;
import com.kdt.prgrms.gccoffee.dto.UpdateProductRequest;
import com.kdt.prgrms.gccoffee.exception.custom.NotFoundException;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.service.ProductService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter())
            .create();

    @Nested
    @DisplayName("createProduct 메서드는")
    class DescribeCreateProduct {

        @Nested
        @DisplayName("생성 요청시 가격이 음수값인 상품 생성 요청이 들어오면")
        class ContextNegativePriceRequest {

            String url = "/api/v1/products";

            CreateProductRequest requestObject = new CreateProductRequest("a", Category.COFFEE_BEAN_PACKAGE, -1, "");

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

            CreateProductRequest requestObject = new CreateProductRequest("", Category.COFFEE_BEAN_PACKAGE, -1, "");

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

            CreateProductRequest requestObject = new CreateProductRequest("a", Category.COFFEE_BEAN_PACKAGE, 1000, "");

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

    @Nested
    @DisplayName("getAllProducts 메서드는")
    class DescribeGetAllProducts {

        @Nested
        @DisplayName("Category를 검색조건으로 get요청을 받으면")
        class ContextReceiveParamCategory {

            String url = "/api/v1/products?category=COFFEE_BEAN_PACKAGE";

            Product firstProduct = new Product(1, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());
            Product secondProduct = new Product(2, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            List<Product> products = List.of(firstProduct, secondProduct);

            @Test
            @DisplayName("OK 응답과 서비스의 getProducts 메서드를 호출한다.")
            void itReturnOkAndCallServiceGetProducts() throws Exception {

                mockMvc.perform(get(url))
                        .andExpect(status().isOk());

                verify(productService).getProducts();
            }

            @Test
            @DisplayName("해당 카테고리의 상품 리스트를 반환한다.")
            void itReturnCategoryProductList() throws Exception {

                when(productService.getProducts()).thenReturn(products);

                String expected = gson.toJson(products);

                mockMvc.perform(get(url))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expected));
            }
        }

        @Nested
        @DisplayName("상품 이름을 검색조건으로 get요청을 받으면")
        class ContextReceiveParamName {

            String url = "/api/v1/products?name=aa";

            Product firstProduct = new Product(1, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());
            Product secondProduct = new Product(2, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            List<Product> products = List.of(firstProduct, secondProduct);

            @Test
            @DisplayName("OK 응답과 서비스의 getProducts 메서드를 호출한다.")
            void itReturnOkAndCallServiceGetProducts() throws Exception {

                mockMvc.perform(get(url))
                        .andExpect(status().isOk());

                verify(productService).getProducts();
            }

            @Test
            @DisplayName("해당 카테고리의 상품 리스트를 반환한다.")
            void itReturnNameProductList() throws Exception {

                when(productService.getProducts()).thenReturn(products);

                String expected = gson.toJson(products);

                mockMvc.perform(get(url))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expected));
            }
        }

        @Nested
        @DisplayName("get 요청을 받으면")
        class ContextCreateProductRequest {

            String url = "/api/v1/products";

            Product firstProduct = new Product(1, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());
            Product secondProduct = new Product(2, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            List<Product> products = List.of(firstProduct, secondProduct);

            @Test
            @DisplayName("Ok를 반환하고 서비스의 getProducts 메소드를 호출한다.")
            void itReturnOkAndCallServiceGetProducts() throws Exception {

                mockMvc.perform(get(url))
                        .andExpect(status().isOk());

                verify(productService).getProducts();
            }

            @Test
            @DisplayName("product 목록을 json 형태로 반환한다.")
            void ReturnJsonTypeProductList() throws Exception {

                when(productService.getProducts()).thenReturn(products);

                String expected = gson.toJson(products);

                mockMvc.perform(get(url))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expected));
            }
        }
    }

    @Nested
    @DisplayName("getProductById 메서드는")
    class DescribeGetProductById {

        @Nested
        @DisplayName("잘못된 Id의 get요청을 받으면")
        class ContextReceiveWrongId {

            long id = 1;

            String url = "/api/v1/products/1";

            @Test
            @DisplayName("Notfound 응답을 반환한다.")
            void itReturnNotfound() throws Exception {

                when(productService.getProductById(id)).thenThrow(NotFoundException.class);

                mockMvc.perform(get(url))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("유효한 id의 get요청을 받으면")
        class ContextReceiveValidId {

            long id = 1;

            String url = "/api/v1/products/1";

            Product firstProduct = new Product(1, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());


            @Test
            @DisplayName("ok응답과 서비스 getProductById메서드를 호출한다.")
            void itReturnOkAndCallServiceGetProductById() throws Exception {

                when(productService.getProductById(id)).thenReturn(firstProduct);

                mockMvc.perform(get(url))
                        .andExpect(status().isOk());

                verify(productService).getProductById(id);
            }

            @Test
            @DisplayName("해당 아이디의 Product를 json형태로 응답한다.")
            void itReturnProductByJsonType() throws Exception {

                String expected = gson.toJson(firstProduct);

                when(productService.getProductById(id)).thenReturn(firstProduct);

                mockMvc.perform(get(url))
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(expected));
            }
        }
    }

    @Nested
    @DisplayName("deleteProductById 메서드는")
    class DescribeDeleteById {

        @Nested
        @DisplayName("존재할 수 없는 id를 인자로 받게되면")
        class ContextReceiveNotNonexistentId {

            long id = -1;
            String url = "/api/v1/products/-1";

            @Test
            @DisplayName("찾을 수 없음 에러를 반환한다.")
            void itReturnIllegalArgument() throws Exception {

                doThrow(NotFoundException.class).when(productService).deleteProductById(id);

                mockMvc.perform(delete(url))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("저장되어있는 id를 인자로 받게되면")
        class ContextReceiveSavedId {

            long inputId = 1;
            String url = "/api/v1/products/1";

            @Test
            @DisplayName("ok상태를 반환하고 service의 deleteById메서드를 호출한다.")
            void itReturnOkAndCallServiceDelete() throws Exception {


                mockMvc.perform(delete(url))
                        .andExpect(status().isOk());

                verify(productService).deleteProductById(inputId);
            }
        }
    }

    @Nested
    @DisplayName("updateProductById 메서드는")
    class DescribeUpdateProductById {

        @Nested
        @DisplayName("잘못된 id의 값을 인자로 받으면")
        class ContextReceiveWrongId {


            String url = "/api/v1/products/-1";

            UpdateProductRequest requestObject = new UpdateProductRequest("aa", Category.COFFEE_BEAN_PACKAGE, 1, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("Notfound 404 응답을 반환한다.")
            void itReturnNotFoundException() throws Exception {

                doThrow(NotFoundException.class).when(productService).updateProductById(any(long.class), any(Product.class));

                mockMvc.perform(request)
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("생성 요청시 이름이 존재하지 않는 상품 생성 요청이 들어오면")
        class ContextNullNameRequest {

            String name = "";

            String url = "/api/v1/products/1";

            UpdateProductRequest requestObject = new UpdateProductRequest(name, Category.COFFEE_BEAN_PACKAGE, 1, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("잘못된 요청 상태를 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("생성 요청시 가격이 음수값인 상품 생성 요청이 들어오면")
        class ContextNegativePriceRequest {

            long price = -1;

            String url = "/api/v1/products";

            CreateProductRequest requestObject = new CreateProductRequest("a", Category.COFFEE_BEAN_PACKAGE, price, "");

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
        @DisplayName("저장되어 있는 id와 요청을 받으면")
        class ContextSavedIdAndRequestBody {

            String url = "/api/v1/products/1";

            UpdateProductRequest requestObject = new UpdateProductRequest("aa", Category.COFFEE_BEAN_PACKAGE, 1, "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(gson.toJson(requestObject));

            @Test
            @DisplayName("service의 updateProductById 메서드를 호출한다")
            void itCallServiceUpdateProductById() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isOk());

                verify(productService).updateProductById(any(long.class), any(Product.class));
            }
        }
    }
}
