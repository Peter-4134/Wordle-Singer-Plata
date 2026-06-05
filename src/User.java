/**
 * erstellt einen User der einen Usernamen hat
 */

public class User {
    private String username;
    private int easyWins;
    private int mediumWins;
    private int hardWins;

    public User(String username, int easyWins, int mediumWins, int hardWins) {
        this.username = username;
        this.easyWins = easyWins;
        this.mediumWins = mediumWins;
        this.hardWins = hardWins;
    }

    /**
     * überprüft, ob der name aktzeptabel für einen user ist
     * @param name ist zu überprüfen
     * @return ob er aktzeptiert wird
     */

    public boolean checkUser(String name) {
        if (name.trim().equals("") || name.equals("Benutzername:")) {
            return false;
        }
        return true;
    }

    /**
     * Bekommt einen String und liefert entweder einen neuen User,
     * oder einen Bestehenden user
     * @param name username des zu liefernden users
     * @return einen user
     */

    public User selectUser(String name) {
        Users users = new Users();
        if (users.getUser(name) != null) {
            return users.getUser(name);
        }
        User erg = new User(name, easyWins, mediumWins, hardWins);
        users.add(erg);
        return erg;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username + ";" + easyWins + ";" + mediumWins + ";" + hardWins;
    }
}
