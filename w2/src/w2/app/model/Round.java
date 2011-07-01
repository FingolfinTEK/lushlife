package w2.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Id;

import w2.app.model.ref.CourseRef;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Round implements Serializable {

	private static final long serialVersionUID = 4576342143720031065L;

	@Id
	private Long id;
	private String name;
	@Embedded
	private CourseRef course;
	private Set<Long> participators = new HashSet<Long>();
	private Set<Long> drivers = new HashSet<Long>();

	private String information;

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CourseRef getCourse() {
		return course;
	}

	public void setCourse(CourseRef course) {
		this.course = course;
	}

	public Set<Long> getParticipators() {
		return participators;
	}

	public void setParticipators(Set<Long> participators) {
		this.participators = participators;
	}

	public Set<Long> getDrivers() {
		return drivers;
	}

	public void setDrivers(Set<Long> dirvers) {
		this.drivers = dirvers;
	}

}
