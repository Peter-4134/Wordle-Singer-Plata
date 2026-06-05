import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * speichert alle user in einer Liste
 */

public class Users {
    List<User> users = new ArrayList<>();

    /**
     * liefert einen User und schaut ob dieser user schon besteht, sonst wird null geliefert
     * @param name name des zu liefernden users
     * @return den user
     */

    public User getUser(String name) {
        User erg = null;
        try {
            List<String> input = Files.readAllLines(Path.of("src/Userlist.txt"));
            for (String s : input) {
                s.trim();
                users.add(new User(s));
                if (s.equals(name)) {
                    erg = users.getLast();
                }
            }
        } catch (IOException e) {
        }
        return erg;
    }

    /**
     * fügt einen user zur userliste hinzu und speichert diese
     * @param user hinzuzufügender user
     */

    public void add(User user) {
        users.add(user);
        List<String> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.getUsername());
        }
        try {
            Files.write(Path.of("src/Userlist.txt"), out);
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei " + e.getMessage());
        }
    }
}
