package projectzero.model;

public class User {

    private final String email; // unique
    private String pass;
    private Role role;

    public User(String email, String pass, Role role) {
        this.email = email;
        this.pass = pass;
        this.role = role;
    }

    public User(String email, String pass) {
        this.email = email;
        this.pass = pass;
        this.role = Role.USER;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "USER{" +
                "email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", role=" + role +
                '}';
    }
}
