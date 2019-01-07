package cyclone.lunchvoting;

import cyclone.lunchvoting.entity.User;

public class UserTestData {
    private UserTestData(){}

    public static final User USER200 = new User(200, "User 1", "user1@mail.org", "userpass");

//    insert into users(id, name, email, password) values(200, 'User 1', 'user1@mail.org', '{noop}userpass');

}
