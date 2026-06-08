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
     *
     * @param name name des zu liefernden users
     * @return den user
     */
    public User getUser(String name) {
        User erg = null;
        try {
            List<String> input = Files.readAllLines(Path.of("src/Userlist.txt"));
            for (String s : input) {
                String[] sa = s.split(";");
                if (s.contains(";"))
                    users.add(new User(sa[0], Integer.parseInt(sa[1]), Integer.parseInt(sa[2]), Integer.parseInt(sa[3])));
                if (sa[0].equals(name)) {
                    erg = users.getLast();
                }
            }
        } catch (IOException e) {
        }
        //   System.out.println(users);
        return erg;
    }

    /**
     * fügt einen user zur userliste hinzu und speichert diese
     *
     * @param user hinzuzufügender user
     */
    public void add(User user) {
        users.add(user);
        List<String> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.toString());
        }
        try {
            Files.write(Path.of("src/Userlist.txt"), out);
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei " + e.getMessage());
        }
    }

    /**
     * Aktualisiert einen vorhandenen Benutzer in der Benutzerliste und speichert die
     * aktualisierte Liste in einer Datei.
     * Falls ein Benutzer mit demselben Benutzernamen wie der übergebene Benutzer in
     * der Liste existiert, wird dieser durch die aktualisierten Benutzerdaten ersetzt.
     * Anschließend wird die aktualisierte Liste wieder in die Datei
     * "src/Userlist.txt" geschrieben.
     *
     * @param updatedUser Das Benutzerobjekt mit den aktualisierten Informationen.
     */
    public void update(User updatedUser) {
        getUser(updatedUser.getUsername()); // aktuelle Liste laden
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedUser.getUsername())) {
                users.set(i, updatedUser);
            }
        }

        List<String> out = new ArrayList<>();
        for (User u : users) {
            out.add(u.toString());
        }
        try {
            Files.write(Path.of("src/Userlist.txt"), out);
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben: " + e.getMessage());
        }
    }
}
