package lush.application.lushup.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.inject.Inject;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Member {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String email;

	@Inject
	public Member() {
	}

	public Member(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String firstName) {
		this.email = firstName;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

}