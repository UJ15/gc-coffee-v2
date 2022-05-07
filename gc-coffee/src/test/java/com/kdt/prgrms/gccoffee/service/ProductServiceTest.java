package com.kdt.prgrms.gccoffee.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kdt.prgrms.gccoffee.exception.custom.NotFoundException;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import com.kdt.prgrms.gccoffee.repository.ProductRepository;
import net.minidev.json.writer.MapperRemapped;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.longThat;
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
    @DisplayName("서비스의 getProducts 메서드는")
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

    @Nested
    @DisplayName("서비스의 getProductById 메서드는")
    class DescribeGetProductById {

        @Nested
        @DisplayName("존재하지 않는 Id를 인자로 받으면")
        class ContextReceiveNotExistId {

            long id = -1;

            @Test
            @DisplayName("Notfound 예외를 발생시킨다.")
            void itThrowNotFoundException() {

                Assertions.assertThatThrownBy(() -> productService.getProductById(id))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 Id를 인자로 받으면")
        class ContextReceiveExistId {

            long id = 1;

            Product product = new Product(id, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("repository의 findById 메서드를 호출하고 해당 id의 product를 반환한다.")
            void itThrowNotFoundException() {

                when(productRepository.findById(id)).thenReturn(Optional.of(product));

                Product productCheck = productService.getProductById(id);

                Assertions.assertThat(product).isEqualTo(productCheck);
            }
        }
    }

    @Nested
    @DisplayName("서비스의 updateProductById 메서드는")
    class DescribeUpdateProductById {

        @Nested
        @DisplayName("product가 null인 객체를 받으면")
        class ContextReceiveNullProduct {

            Product product = null;

            @Test
            @DisplayName("잘못된 인자 예외를 발생시킨다.")
            void ThrowIllegalArgumentException() {

                Assertions.assertThatThrownBy(() -> productService.updateProductById(1, product))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("존재하지 않는 id를 인자로 받으면")
        class ContextReceiveNotExistId {

            long id = -1;

            Product product = new Product(id, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("NotFound 예외를 발생시킨다.")
            void ThrowNotFoundException() {

                when(productRepository.findById(id)).thenThrow(NotFoundException.class);

                Assertions.assertThatThrownBy(() -> productService.updateProductById(id, product))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 Id와 product 도메인을 인자로 받으면")
        class ContextReceiveExistId {

            long id = 1;

            Product product = new Product(id, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("service의 getProdctId를 호출한다..")
            void itCallServiceGetProductById() {

                when(productRepository.findById(id)).thenReturn(Optional.of(product));

                productService.updateProductById(id, product);

                verify(productRepository).findById(id);
            }

            @Test
            @DisplayName("repository의 save메서드를 호출한다.")
            void itCallRepositorySave() {

                when(productRepository.findById(id)).thenReturn(Optional.of(product));

                productService.updateProductById(id, product);

                verify(productRepository).save(any(Product.class));
            }
        }
    }

    @Nested
    @DisplayName("서비스의 deleteProductById 메서드는")
    class DescribeDeleteProductById {

        @Nested
        @DisplayName("존재하지 않는 id를 인자로 받으면")
        class ContextReceiveNotExistId {

            long id = -1;

            @Test
            @DisplayName("NotFound예외를 반환한다.")
            void itThrowNotFoundException() {

                Assertions.assertThatThrownBy(() -> productService.deleteProductById(id))
                        .isInstanceOf(NotFoundException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 id를 인자로 받으면")
        class ContextReceiveExistId {

            long id = 1;

            Product product = new Product(id, "aa", Category.COFFEE_BEAN_PACKAGE,  1, "", LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("repository의 deleteById메서드를 호출한다")
            void itCallRepositoryDeleteById() {

                when(productRepository.findById(id)).thenReturn(Optional.of(product));

                productService.deleteProductById(id);

                verify(productRepository).deleteById(id);
            }
        }
    }


}
