package io.github.wolfandw.itest;

import io.github.wolfandw.itest.configuration.DataSourceConfigurationTest;
import io.github.wolfandw.itest.configuration.WebConfigurationTest;
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

@SpringJUnitConfig(classes = {
        DataSourceConfigurationTest.class,
        WebConfigurationTest.class
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
