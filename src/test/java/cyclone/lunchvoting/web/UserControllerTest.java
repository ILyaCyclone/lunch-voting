package cyclone.lunchvoting.web;

import cyclone.lunchvoting.AssertUtils;
import cyclone.lunchvoting.JsonUtils;
import cyclone.lunchvoting.dto.ErrorResponse;
import cyclone.lunchvoting.dto.VotingStatus;
import cyclone.lunchvoting.entity.Restaurant;
import cyclone.lunchvoting.exception.VotingIsNotActiveException;
import cyclone.lunchvoting.exception.VotingIsNotFinishedException;
import cyclone.lunchvoting.util.DateTimeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalTime;
import java.util.List;

import static cyclone.lunchvoting.TestData.*;
import static cyclone.lunchvoting.UserTestData.USER200;
import static cyclone.lunchvoting.web.UserController.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends AbstractControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Value("${cyclone.lunchvoting.voting-ends}")
    private String votingEnds;


    @Test
    void getTodaysRestaurants() throws Exception {
        MvcResult mvcResult =
                mockMvc.perform(get(URL + "/list")
                        .with(httpBasicAuth(USER200)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(mvcResult -> assertMvcRestaurantWithMenuEquals(mvcResult, RESTAURANT1, RESTAURANT1_MENU))
                        .andReturn();

        List<Restaurant> actualRestaurants = JsonUtils.readListFromMvcResult(super.objectMapper, mvcResult, Restaurant.class);
        AssertUtils.assertRestaurantsContainsExactly(actualRestaurants, RESTAURANT1, RESTAURANT2);

        AssertUtils.assertMenuEquals(actualRestaurants.get(0).getMenu(), RESTAURANT1_MENU);
        AssertUtils.assertMenuEquals(actualRestaurants.get(1).getMenu(), RESTAURANT2_MENU);
    }


    @Test
    void vote_successful() throws Exception {
        assumeVotingIsActive();

        mockMvc.perform(post(URL + "/vote/" + RESTAURANT3.getId())
                .with(httpBasicAuth(USER200))
        )
                .andExpect(status().isNoContent());
    }

    @Test
    void vote_notActive() throws Exception {
        assumeVotingIsNotActive();

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
    void getVotingStatus() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get(URL + "/voting-status")
                        .with(httpBasicAuth(USER200)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();
        VotingStatus actualVotingStatus = JsonUtils.readValueFromMvcResult(super.objectMapper, mvcResult, VotingStatus.class);

        LocalTime expectedVotingEndTime = DateTimeUtils.hhDashMmToLocalTime(votingEnds);

        Assertions.assertThat(actualVotingStatus.getVotingEndTime()).isEqualTo(expectedVotingEndTime);
        // depend on server time rather than client time so that our tests won't fail at 10:59.999
        Assertions.assertThat(actualVotingStatus.isVotingActive()).isEqualTo(votingIsActive(actualVotingStatus.getServerTime()));
    }


    @Test
    void getVotingResult_successful() throws Exception {
        assumeVotingIsNotActive();

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

    @Test
    void getVotingResult_votingIsNotFinished() throws Exception {
        assumeVotingIsActive();

        MvcResult mvcResult =
                mockMvc.perform(get(URL + "/voting-result")
                        .with(httpBasicAuth(USER200)))
                        .andDo(print())
                        .andExpect(status().isUnprocessableEntity())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn();

        ErrorResponse errorResponse = JsonUtils.readValueFromMvcResult(super.objectMapper, mvcResult, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getError()).isEqualTo(VotingIsNotFinishedException.class.getSimpleName());
    }


    private void assumeVotingIsActive() {
        Assumptions.assumeTrue(votingIsActive(), "Voting is currently not active");
    }

    private void assumeVotingIsNotActive() {
        Assumptions.assumeFalse(votingIsActive(), "Voting is currently active");
    }

    private boolean votingIsActive() {
        return DateTimeUtils.hhDashMmToLocalTime(votingEnds).isAfter(DateTimeUtils.now());
    }

    private boolean votingIsActive(LocalTime dateTime) {
        return DateTimeUtils.hhDashMmToLocalTime(votingEnds).isAfter(dateTime);
    }
}