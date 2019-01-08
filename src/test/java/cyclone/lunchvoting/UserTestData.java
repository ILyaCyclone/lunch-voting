package cyclone.lunchvoting;

import cyclone.lunchvoting.entity.User;

public class UserTestData {
    private UserTestData(){}

    public static final User USER200 = new User(200, "User 1", "user1@mail.org", "userpass"), ADMIN100 = new User(100, "Admin 1", "admin1@mail.org", "adminpass");

}
