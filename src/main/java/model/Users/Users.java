package model.Users;

import enums.Role;

public abstract class Users {

    private String name;

    private int userId;
    Role role;

    private String requestedBookTitle;

    public Users(String name, int userId) {
        this.name = name;
        this.userId = userId;

    }

    public Users(String name, int id, Role role) {
        this.name = name;
        this.userId = id;
        this.role = role; // Assuming you have a field 'role' in Users class.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public String getRequestedBookTitle() {
        return requestedBookTitle;
    }

    public void setRequestedBookTitle(String requestedBookTitle) {
        this.requestedBookTitle = requestedBookTitle;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", role=" + role +
                '}';
    }
}
