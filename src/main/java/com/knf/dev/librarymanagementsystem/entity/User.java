package com.knf.dev.librarymanagementsystem.entity;

import java.util.Collection;
import javax.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email") // Ensures no duplicate emails
)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // Automatically generates unique IDs
	private Long id;

	@Column(name = "first_name") // Maps this field to the 'first_name' column in the table
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	// Defines a many-to-many relationship with the Role entity
	@JoinTable(name = "users_roles", // Join table name
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), // Foreign key for User
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // Foreign key for Role
	)
	private Collection<Role> roles; // Stores the roles assigned to the user

	public User() {
	}

	public User(String firstName, String lastName, String email, String password, Collection<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
