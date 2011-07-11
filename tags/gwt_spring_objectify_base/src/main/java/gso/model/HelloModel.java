package gso.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class HelloModel {
	@Id
	Long id;
	String message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
