package it.discovery.repository;

import it.discovery.config.AppConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(AppConfiguration.PersistenceConfiguration.class)
@TestPropertySource(properties = """
        current.profiles=prod
        db.name=test
        db.server=base""")
class DBBookRepositoryTest {

    @Autowired
    DBBookRepository repository;

    @Test
    void getServer_serverNameOverridden_newServerNameUsed() {
        assertEquals("base", repository.getServer());
    }
}