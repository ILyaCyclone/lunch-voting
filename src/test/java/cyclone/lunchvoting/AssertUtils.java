package cyclone.lunchvoting;

import cyclone.lunchvoting.entity.MenuItem;
import cyclone.lunchvoting.entity.Restaurant;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.BigDecimalComparator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AssertUtils {
    private AssertUtils() {
    }

    public static void assertRestaurantsContainsExactly(List<Restaurant> actual, Restaurant... expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("menu").containsExactly(expected);
    }

    public static void assertMenuEquals(List<MenuItem> actual, MenuItem... expected) {
        assertMenuEquals(actual, Arrays.asList(expected));
    }

    public static void assertMenuEquals(List<MenuItem> actual, List<MenuItem> expected) {
        Assertions.assertThat(actual)
                // solve error when 130 is not equal to 130.00 or 99.5 is not equal to 99.50
                // BigDecimalComparator compares BigDecimals using compareTo
                .usingComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
