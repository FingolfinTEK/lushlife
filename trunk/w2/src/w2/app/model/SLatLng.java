package w2.app.model;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class SLatLng implements Serializable {

	private static final long serialVersionUID = 3984010347182481253L;

	@Id
	private long id;
	private int zoom;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
