package io.github.wolfandw.myblog.backend.itest;

import io.github.wolfandw.myblog.backend.itest.configuration.DataSourceConfigurationIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Абстрактный интеграционный тест.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Import(DataSourceConfigurationIntegrationTest.class)
public abstract class AbstractPostIntegrationTest {
    @Autowired
    protected DataSource dataSource;

    @Autowired
    protected MockMvc mockMvc;

    @Value("${my.blog.back.app.upload.posts.dir}")
    private String fileDir;

    @BeforeEach
    void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("test-data.sql"));
        populator.execute(dataSource);

        Path pathDir = Paths.get(fileDir);
        if (Files.exists(pathDir)) {
            Iterator<Path> it = pathDir.iterator();
            try {
                while (it.hasNext()) {
                    Path filePath = it.next();
                    Files.deleteIfExists(filePath);
                }
                Files.deleteIfExists(pathDir);
            } catch (IOException e) {
                // Do nothing;
            }
        }
    }
}
