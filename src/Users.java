import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Users {
    List<User> users = new ArrayList<>();

    public User getUser(String name) {
        User erg = null;
        try {
            List<String> input = Files.readAllLines(Path.of("src/Userlist.txt"));
            for (String s : input) {
                s.trim();
                users.add(new User(s));
                if (s.equals(name)) {
                    System.out.println("hit");
                    erg = users.getLast();
                }
            }
        } catch (IOException e) {
        }
        System.out.println(erg);
        return erg;
    }

    public void add(User user) {
        users.add(user);
        System.out.println(users);
        List<String> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.getUsername());
        }
        try {
            Files.write(Path.of("src/Userlist.txt"), out);
        } catch (IOException e) {
        }
    }
}
