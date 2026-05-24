public class User {
    String username;

    public User(String username) {
        this.username = username;
    }

    public boolean checkUser(String name) {
        if (name.trim() != null && name != "Benutzername:") {
            return true;
        }
        return false;
    }

    public User selectUser(String name) {
        Users users = new Users();
        if (users.getUser(name) != null) {
            return users.getUser(name);
        }
        User erg = new User(name);
        users.add(erg);
        return erg;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
