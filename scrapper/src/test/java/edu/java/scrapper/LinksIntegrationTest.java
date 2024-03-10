package edu.java.scrapper;

import edu.java.configuration.properties.ApplicationConfig;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class LinksIntegrationTest extends IntegrationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ApplicationConfig applicationConfig;

    @Test
    public void insertLink() {
        assertDoesNotThrow(
                () -> jdbcTemplate.execute("""
                        insert into links (url, last_check_at, created_at)
                        values (
                            'https://github.com/Anoldyouth/Java-Telegram-Notifications',
                            '2024-03-10 15:57:38.593000 +00:00',
                            '2024-03-10 15:57:40.799000 +00:00'
                        )
                        """)
        );
    }
}
