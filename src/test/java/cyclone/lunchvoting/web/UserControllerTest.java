package cyclone.lunchvoting.web;

import cyclone.lunchvoting.AssertUtils;
import cyclone.lunchvoting.JsonUtils;
import cyclone.lunchvoting.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static cyclone.lunchvoting.TestData.*;
import static cyclone.lunchvoting.UserTestData.USER200;
import static cyclone.lunchvoting.web.UserController.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

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
}