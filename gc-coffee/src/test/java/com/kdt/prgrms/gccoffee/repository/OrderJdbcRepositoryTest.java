package com.kdt.prgrms.gccoffee.repository;


import com.kdt.prgrms.gccoffee.models.*;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Order;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderJdbcRepositoryTest {

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
    private final OrderJdbcRepository orderJdbcRepository = new OrderJdbcRepository(new NamedParameterJdbcTemplate(dataSource));

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

                Assertions.assertThatThrownBy(() -> orderJdbcRepository.save(order))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @Order(2)
        @DisplayName("인자로 order를 받으면")
        class ContextReceiveOrder {

            com.kdt.prgrms.gccoffee.models.Order order = new com.kdt.prgrms.gccoffee.models.Order("adw@dad.com", "경기도 구리시", "1323", new ArrayList<>(), OrderStatus.ACCEPTED);

            @Test
            @DisplayName("해당 order를 Database에 저장한다.")
            void itSaveProduct() {

                com.kdt.prgrms.gccoffee.models.Order orderCheck = orderJdbcRepository.save(order);

                Assertions.assertThat(orderCheck.getAddress()).isEqualTo("경기도 구리시");
                Assertions.assertThat(orderCheck.getPostcode()).isEqualTo("1323");
            }
        }
    }
}
