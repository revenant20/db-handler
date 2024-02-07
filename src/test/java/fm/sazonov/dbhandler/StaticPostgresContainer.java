package fm.sazonov.dbhandler;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@Slf4j
public class StaticPostgresContainer {
    public static PostgreSQLContainer<?> getContainer(StorageType storageType) {
        final PostgreSQLContainer<?> container = LazyPostgresContainer.INSTANCE;
        if (storageType == StorageType.PERMANENT_STORAGE) {
            final var homeDir = Path.of(System.getProperty("user.dir"));
            final var pgdata = homeDir.resolve("pgdata");
            if (Files.notExists(pgdata)) {
                try {
                    Files.createDirectory(pgdata);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            container.withFileSystemBind(pgdata.toString(), "/var/lib/postgresql/data");
        }
        container.start();
        container.followOutput(s -> log.info(s.getUtf8String()));
        return container;
    }

    private static class LazyPostgresContainer {
        private static final PostgreSQLContainer<?> INSTANCE = makeContainer();


        private static PostgreSQLContainer<?> makeContainer() {
            final var container = new PostgreSQLContainer<>("postgres:14.6-alpine3.17")
                    .withDatabaseName("foo")
                    .withUsername("foo")
                    .withPassword("secret")
                    .withStartupTimeout(Duration.ofSeconds(10));
            container.setWaitStrategy(new HostPortWaitStrategy());
            return container;
        }
    }

    public enum StorageType {
        PERMANENT_STORAGE,
        TEMPORARY_STORAGE,
    }
}
