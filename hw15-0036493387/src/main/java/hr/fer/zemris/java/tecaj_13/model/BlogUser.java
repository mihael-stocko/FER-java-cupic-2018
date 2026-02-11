package hr.fer.zemris.java.tecaj_13.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class models a blog user.
 * 
 * @author Mihael Stočko
 *
 */
@Entity
@Table(name="blog_user")
public class BlogUser {
	/**
	 * User id
	 */
	private Long id;
	
	/**
	 * First name
	 */
	private String firstName;
	
	/**
	 * Last name
	 */
	private String lastName;
	
	/**
	 * Nick
	 */
	private String nick;
	
	/**
	 * Email
	 */
	private String email;
	
	/**
	 * Password hash
	 */
	private String passwordHash;

	/**
	 * List of blog entries
	 */
	private List<BlogEntry> entries;
	
	/**
	 * Default constructor
	 */
	public BlogUser() {
		
	}
	
	/**
	 * Constructor
	 */
	public BlogUser(Long id, String firstName, String lastName, String nick, String email, String passwordHash) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for firstName
	 */
	@Column(length=200, nullable=false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for lastName
	 */
	@Column(length=200, nullable=false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for nick
	 */
	@Column(length=200, nullable=false, unique=true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for email
	 */
	@Column(length=200, nullable=false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for passwordHash
	 */
	@Column(length=4096, nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Getter for entries
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}
}
