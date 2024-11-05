package services.impl;

import model.Users.Users;
import services.UserService;

public class UserServiceImpl implements UserService {


    @Override
    public String requestBook(Users user) {
        return "Customer Name: " + user.getName() + "is asking for money";

    }
}
