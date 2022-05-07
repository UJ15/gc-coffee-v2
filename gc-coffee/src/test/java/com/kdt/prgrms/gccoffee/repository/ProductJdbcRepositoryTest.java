package com.kdt.prgrms.gccoffee.repository;


import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Product;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config= aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("order_mgmt", ScriptResolver.classPathScript("schema-test.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private final DataSource dataSource = DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:2215/order_mgmt")
            .username("test")
            .password("test1234!")
            .type(HikariDataSource.class)
            .build();

    private final ProductJdbcRepository productJdbcRepository = new ProductJdbcRepository(new NamedParameterJdbcTemplate(dataSource));

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
        @DisplayName("인자로 id 가 0인 product를 받으면")
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

        @Nested
        @Order(2)
        @DisplayName("인자로 id 가 0이 아닌 product를 받으면")
        class ContextReceiveExistIdProduct {

            Product product = new Product("coffee", Category.COFFEE_BEAN_PACKAGE, 1001, "");
            Product updateProduct = Product.toEntity(1, product);
            @Test
            @DisplayName("해당 product를 update한다.")
            void itUpdateProduct() {

                Product productCheck = productJdbcRepository.save(updateProduct);

                Assertions.assertThat(productCheck.getProductName()).isEqualTo("coffee");
                Assertions.assertThat(productCheck.getPrice()).isEqualTo(1001);
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
            @DisplayName("해당 database의 product들을 리스트로 반환한다.")
            void itReturnProducts() {

                List<Product> products = productJdbcRepository.findAll();

                Assertions.assertThat(products.size()).isNotEqualTo(0);
            }
        }
    }

    @Nested
    @Order(3)
    @DisplayName("findById 메서드는")
    class DescribeFindById {

        @Nested
        @DisplayName("존재하지 않는 id를 인자로 받으면")
        class ContextReceiveNotExistId {

            long id = -1;

            @Test
            @DisplayName("비어있는 Optinal을 반환한다.")
            void itReturnOptionalEmpty() {

                Optional<Product> product = productJdbcRepository.findById(id);

                Assertions.assertThat(product).isEqualTo(Optional.empty());
            }
        }

        @Nested
        @DisplayName("존재하는 id를 인자로 받으면")
        class ContextReceiveExistId {

            long id = 1;

            @Test
            @DisplayName("해당 product를 Optianl로 반환한다.")
            void itReturnOptionalProduct() {

                Optional<Product> product = productJdbcRepository.findById(id);

                Assertions.assertThat(product.isPresent()).isTrue();
                Assertions.assertThat(product.get().getProductId()).isEqualTo(1);
                Assertions.assertThat(product.get().getProductName()).isEqualTo("coffee");
            }
        }
    }

    @Nested
    @Order(4)
    @DisplayName("deleteById 메서드는")
    class DescribeDeleteById {

        @Nested
        @DisplayName("존재하지 않는 id를 인자로 받으면")
        class ContextReceiveNotExistId {

            long id = -1;

            @Test
            @Order(1)
            @DisplayName("서버 에러 예외를 반환한다.")
            void itThrowIllegalStatementException() {

                Assertions.assertThatThrownBy(() -> productJdbcRepository.deleteById(id))
                        .isInstanceOf(IllegalStateException.class);
            }
        }

        @Nested
        @DisplayName("존재하는 id를 인자로 받으면")
        class ContextReceiveExistId {

            long id = 1;

            @Test
            @Order(2)
            @DisplayName("해당 Id를 가진 product를 삭제한다.")
            void itDeleteProduct() {

                productJdbcRepository.deleteById(id);

                Assertions.assertThatThrownBy(() -> productJdbcRepository.deleteById(id))
                        .isInstanceOf(IllegalStateException.class);
            }
        }
    }
}
