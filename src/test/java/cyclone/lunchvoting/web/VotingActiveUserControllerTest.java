package cyclone.lunchvoting.web;

import cyclone.lunchvoting.JsonUtils;
import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.service.VoteService;
import cyclone.lunchvoting.service.VotingStatusService;
import cyclone.lunchvoting.util.DateTimeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static cyclone.lunchvoting.UserTestData.USER200;
import static cyclone.lunchvoting.web.UserController.URL;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
//@DirtiesContext
public class VotingActiveUserControllerTest extends AbstractControllerTest {

//    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        @Override
//        public void initialize(ConfigurableApplicationContext ctx) {
//            TestPropertyValues.of(
//                    "spring.redis.host:" + redis.getContainerIpAddress(),
//                    "spring.redis.port:" + redis.getMappedPort(REDIS_PORT))
//                    .applyTo(ctx);
//        }
//    }

    @Mock
    VotingStatusService votingStatusService;

    @InjectMocks
    VoteService voteService;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        when(votingStatusService.isVotingActive(Mockito.any()))
                .thenReturn(true);
    }

    @Test
    void isVotingActive() {
        Assertions.assertThat(votingStatusService.isVotingActive(DateTimeUtils.now())).isTrue();
    }

    @Test
    void getVotingStatus() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get(URL + "/voting-status")
                        .with(httpBasicAuth(USER200)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();
        VotingStatus votingStatus = JsonUtils.readValueFromMvcResult(super.objectMapper, mvcResult, VotingStatus.class);

        Assertions.assertThat(votingStatus.isVotingActive()).isTrue();
    }

}
