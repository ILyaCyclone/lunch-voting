package cyclone.lunchvoting.web;

import cyclone.lunchvoting.AssertUtils;
import cyclone.lunchvoting.JsonUtils;
import cyclone.lunchvoting.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static cyclone.lunchvoting.TestData.NEW_RESTAURANT;
import static cyclone.lunchvoting.UserTestData.ADMIN100;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestRepositoryTest extends AbstractControllerTest {
    private static final String URL = "/api/admin/restaurants";

    @Test
    public void testCreate() throws Exception {
        Restaurant newRestaurant = NEW_RESTAURANT;
        MvcResult mvcResult = mockMvc.perform(post(URL)
                .with(httpBasicAuth(ADMIN100))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.writeValue(super.objectMapper, newRestaurant)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        Restaurant createdRestaurant = JsonUtils.readValueFromMvcResult(super.objectMapper, mvcResult, Restaurant.class);

        newRestaurant.setId(createdRestaurant.getId());

        AssertUtils.assertRestaurantEquals(createdRestaurant, newRestaurant);
    }

}
