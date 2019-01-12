package cyclone.lunchvoting.web;

import cyclone.lunchvoting.JsonUtils;
import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.service.VotingStatusService;
import cyclone.lunchvoting.util.DateTimeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static cyclone.lunchvoting.UserTestData.USER200;
import static cyclone.lunchvoting.web.UserController.URL;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

abstract class AbstractVotingActiveMockUserControllerTest extends AbstractControllerTest {

    @MockBean
    VotingStatusService votingStatusService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    public void setUp() {
        // rely on @AutoconfigureMockMvc

//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
//                .apply(springSecurity()) // https://www.baeldung.com/spring-security-integration-tests chapt. 6
//                .build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
//                .build();

        when(votingStatusService.isVotingActive(Mockito.any()))
                .thenReturn(isVotingActiveMock());
        when(votingStatusService.getVotingStatus((Mockito.any())))
                .thenCallRealMethod();
    }

    protected abstract boolean isVotingActiveMock();

    @Test
    void isVotingActive() {
        Assertions.assertThat(votingStatusService.isVotingActive(DateTimeUtils.now())).isEqualTo(isVotingActiveMock());
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

        Assertions.assertThat(votingStatus.isVotingActive()).isEqualTo(isVotingActiveMock());
    }

}
