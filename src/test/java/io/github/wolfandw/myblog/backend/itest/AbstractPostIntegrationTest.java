package io.github.wolfandw.myblog.backend.itest;

import io.github.wolfandw.myblog.backend.itest.configuration.DataSourceConfigurationIntegrationTest;
import io.github.wolfandw.myblog.backend.itest.configuration.WebConfigurationIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

/**
 * Абстрактный интеграционный тест.
 */
@SpringJUnitConfig(classes = {
        DataSourceConfigurationIntegrationTest.class,
        WebConfigurationIntegrationTest.class
})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class AbstractPostIntegrationTest {
    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        DataSource dataSource = wac.getBean(DataSource.class);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("test-data.sql"));
        populator.execute(dataSource);
    }
}
