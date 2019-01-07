package cyclone.lunchvoting;

import cyclone.lunchvoting.entity.MenuItem;
import cyclone.lunchvoting.entity.Restaurant;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestData {
    private static final LocalDate TODAY = LocalDate.now();

    private TestData(){}

    public static final Restaurant
            RESTAURANT1 = new Restaurant(1, "Funny Pork")
            , RESTAURANT2 = new Restaurant(2, "Gone Hungry")
            , RESTAURANT3 = new Restaurant(3, "Tables&Chairs")

            ,NEW_RESTAURANT = new Restaurant("Brave new world");

    public static final List<Restaurant> RESTAURANTS = Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3);


    /*
    insert into menu(id_restaurant, name, price, date) values(1, 'Yesterday''s dinner', 50, '2019-01-01');
insert into menu(id_restaurant, name, price) values(1, 'When pigs fly', 99.50);
insert into menu(id_restaurant, name, price) values(1, 'Sand Witch', 135);

insert into menu(id_restaurant, name, price) values(2, 'Nullified Burgers', 299.99);
insert into menu(id_restaurant, name, price) values(2, 'Smell of pizza', 75);
insert into menu(id_restaurant, name, price) values(2, 'Pure Water', 0.50);
     */

    // BigDecimal(String) constructor is recommended according to BigDecimal Javadoc
    public static final MenuItem
            MENUITEM1 = new MenuItem(1, "Yesterday's dinner", new BigDecimal("50"), LocalDate.of(2019, 1, 1))
            , MENUITEM11 = new MenuItem(11, "Sand Witch", new BigDecimal("135"), TODAY)
            , MENUITEM12 = new MenuItem(12, "When pigs fly", new BigDecimal("99.50"), TODAY)

            , MENUITEM21 = new MenuItem(21, "Nullified Burgers", new BigDecimal("299.99"), TODAY)
            , MENUITEM22 = new MenuItem(22, "Pure Water", new BigDecimal("0.50"), TODAY)
            , MENUITEM23 = new MenuItem(23, "Smell of pizza", new BigDecimal("75"), TODAY)
            ;

    public static final List<MenuItem> RESTAURANT1_MENU = Arrays.asList(MENUITEM11, MENUITEM12)
                                    , RESTAURANT2_MENU = Arrays.asList(MENUITEM21, MENUITEM22, MENUITEM23);

//    static {
//        RESTAURANT1.setMenu(RESTAURANT1_MENU);
//        RESTAURANT2.setMenu(RESTAURANT2_MENU);
//    }


}
