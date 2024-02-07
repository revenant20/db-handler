package fm.sazonov.dbhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

import static fm.sazonov.dbhandler.StaticPostgresContainer.StorageType.PERMANENT_STORAGE;
import static fm.sazonov.dbhandler.StaticPostgresContainer.StorageType.TEMPORARY_STORAGE;

@Slf4j
public class TestcontainersInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        final var testcontainersEnabled = context.getEnvironment()
                .getProperty("keysetpagination.testcontainers.enabled");
        if (!"true".equals(testcontainersEnabled)) {
            var env = context.getEnvironment();
            env.getPropertySources().addFirst(new MapPropertySource(
                    "testcontainers",
                    Map.of(
                            "spring.liquibase.enabled", "false",
                            "spring.jpa.hibernate.ddl-auto", "none",
                            "keysetpagination.testcontainers.enabled", "false"
                    )
            ));
            return;
        }
        final var permanentStorageEnabled = context.getEnvironment()
                .getProperty("keysetpagination.testcontainers.permanentStorage.enabled");
        final var storageType = ("true".equals(permanentStorageEnabled))
                ? PERMANENT_STORAGE : TEMPORARY_STORAGE;
        final var container = StaticPostgresContainer.getContainer(storageType);
        final String jdbcUrl = container.getJdbcUrl();
        var env = context.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource(
                "testcontainers",
                Map.of(
                        "spring.datasource.url", jdbcUrl,
                        "spring.datasource.username", container.getUsername(),
                        "spring.datasource.password", container.getPassword()
                )
        ));
    }

}
