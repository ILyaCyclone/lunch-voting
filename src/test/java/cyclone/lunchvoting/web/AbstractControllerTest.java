package cyclone.lunchvoting.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyclone.lunchvoting.StopWatchExtension;
import cyclone.lunchvoting.entity.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class) // integrates the Spring TestContext Framework into JUnit 5
@ExtendWith(StopWatchExtension.class) // prints test results
@AutoConfigureMockMvc
@Transactional
abstract class AbstractControllerTest {
//    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();
//
//    static {
//        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
//        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
//    }

    @Autowired
    protected MockMvc mockMvc;


    @Autowired
    protected ObjectMapper objectMapper;

    public static RequestPostProcessor httpBasicAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }


    // replace this with @AutoConfigureMockMvc
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//    @PostConstruct
//    private void postConstruct() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webApplicationContext)
//                .addFilter(CHARACTER_ENCODING_FILTER)
//                .apply(springSecurity())
//                .build();
//    }
}
