package cyclone.lunchvoting.config;

import org.h2.jdbc.JdbcSQLException;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("dev")
public class H2Configuration {
private static final Logger logger = LoggerFactory.getLogger(H2Configuration.class);


    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * Connect to "jdbc:h2:tcp://localhost:9092/mem:testdb", username "sa", password empty.
     */
    @Bean
//    @ConditionalOnExpression("${h2.tcp.enabled:false}")
    public Server h2TcpServer() throws SQLException {
        try {
            return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
        } catch (JdbcSQLException e) {
            logger.error(e.toString());
            return null;
        }
    }
}
