package beans;

import java.io.Serializable;
import java.util.Objects;

public class UserBean implements Serializable {
	
	private String firstName, lastName, username, password, email,city;
	private boolean locked, enabled;
	private int id, userTypeId;

	@Override
	public int hashCode() {
		return Objects.hash(city, email, enabled, firstName, id, lastName, locked, password, userTypeId, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		return Objects.equals(city, other.city) && Objects.equals(email, other.email) && enabled == other.enabled
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && locked == other.locked
				&& Objects.equals(password, other.password) && userTypeId == other.userTypeId
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserBean [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password="
				+ password + ", email=" + email + ", city=" + city + ", locked=" + locked + ", enabled=" + enabled
				+ ", id=" + id + ", userTypeId=" + userTypeId + "]";
	}

	public UserBean(String firstName, String lastName, String username, String password, String email, String city,
			boolean locked, boolean enabled, int id, int userTypeId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.city = city;
		this.locked = locked;
		this.enabled = enabled;
		this.id = id;
		this.userTypeId = userTypeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public UserBean() {
		// TODO Auto-generated constructor stub
	}

}
