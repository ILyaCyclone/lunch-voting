package cyclone.lunchvoting;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class LunchVotingApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunchVotingApplication.class, args);
    }

    @Autowired
    DataSource ds;

    /**
     * TCP connection to connect with SQL clients to the embedded h2 database.
     *
     * Connect to "jdbc:h2:tcp://localhost:9092/mem:testdb", username "sa", password empty.
     */
    @Bean
//    @ConditionalOnExpression("${h2.tcp.enabled:false}")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092").start();
    }

}

