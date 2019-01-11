package cyclone.lunchvoting.web;

import cyclone.lunchvoting.JsonUtils;
import cyclone.lunchvoting.dto.ErrorResponse;
import cyclone.lunchvoting.exception.VotingIsNotActiveException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static cyclone.lunchvoting.TestData.RESTAURANT1;
import static cyclone.lunchvoting.TestData.RESTAURANT3;
import static cyclone.lunchvoting.UserTestData.USER200;
import static cyclone.lunchvoting.web.UserController.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VotingNotActiveUserControllerTest extends AbstractVotingActiveMockUserControllerTest {

    @Override
    protected boolean isVotingActiveMock() {
        return false;
    }

    @Test
    void vote_notActive() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(post(URL + "/vote/" + RESTAURANT3.getId())
                        .with(httpBasicAuth(USER200)))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();

        ErrorResponse errorResponse = JsonUtils.readValueFromMvcResult(super.objectMapper, mvcResult, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getError()).isEqualTo(VotingIsNotActiveException.class.getSimpleName());
    }

    @Test
    void getVotingResult() throws Exception {

//        MvcResult mvcResult =
        mockMvc.perform(get(URL + "/voting-result")
                .with(httpBasicAuth(USER200)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(RESTAURANT1.getId()))
                .andExpect(jsonPath("$[0].name").value(RESTAURANT1.getName()))
                .andExpect(jsonPath("$[0].votes").value(2))
                .andExpect(jsonPath("$.length()").value(1))
        ;
    }

}
