package w2.app.model;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Course implements Serializable {

	private static final long serialVersionUID = 3452210462970966598L;

	@Id
	private Long id;
	private String name;
	private String adress;

	@Embedded
	private SLatLng latLng;

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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public SLatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(SLatLng latLng) {
		this.latLng = latLng;
	}

}
