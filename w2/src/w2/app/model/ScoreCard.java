package w2.app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;

@Entity
public class ScoreCard implements Serializable {

	@Id
	long id;

	private Key<Golfer> golfer;

	Key<Course> zenhanCourse;

	List<Score> zenhanScoure;

	Key<Course> kouhanCourse;

	List<Score> kouhanScoure;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Key<Golfer> getGolfer() {
		return golfer;
	}

	public void setGolfer(Key<Golfer> golfer) {
		this.golfer = golfer;
	}

	public Key<Course> getZenhanCourse() {
		return zenhanCourse;
	}

	public void setZenhanCourse(Key<Course> zenhanCourse) {
		this.zenhanCourse = zenhanCourse;
	}

	public List<Score> getZenhanScoure() {
		return zenhanScoure;
	}

	public void setZenhanScoure(List<Score> zenhanScoure) {
		this.zenhanScoure = zenhanScoure;
	}

	public Key<Course> getKouhanCourse() {
		return kouhanCourse;
	}

	public void setKouhanCourse(Key<Course> kouhanCourse) {
		this.kouhanCourse = kouhanCourse;
	}

	public List<Score> getKouhanScoure() {
		return kouhanScoure;
	}

	public void setKouhanScoure(List<Score> kouhanScoure) {
		this.kouhanScoure = kouhanScoure;
	}

}
