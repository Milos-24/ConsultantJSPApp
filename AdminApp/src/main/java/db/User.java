package db;

public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private boolean locked;
    private boolean enabled;
    private String avatar;
    private int user_type_id;
    private String city;

    // Constructors
    public User(int id, String firstname, String lastname, String username, String password, String email,
                boolean locked, boolean enabled, String avatar,int user_type_id, String city) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.locked = locked;
        this.enabled = enabled;
        this.avatar = avatar;
        this.user_type_id = user_type_id;
        this.city = city;
    }
    public User() {
		// TODO Auto-generated constructor stub
	}

    // Getter and Setter methods for each field

    public int getId() {
        return id;
    }

    @Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", locked=" + locked + ", enabled=" + enabled
				+ ", avatar=" + avatar + ", user_type_id= " + user_type_id + ", city=" + city + "]";
	}
	public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getUser_type_id() {
		return user_type_id;
	}
	public void setUser_type_id(int user_type_id) {
		this.user_type_id = user_type_id;
	}
	public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
