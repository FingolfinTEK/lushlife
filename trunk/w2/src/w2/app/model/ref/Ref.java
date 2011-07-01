package w2.app.model.ref;

import java.io.Serializable;

import javax.persistence.Id;

abstract public class Ref<T> implements Serializable {

	private static final long serialVersionUID = -7566875354380552923L;
	@Id
	Long id;
	transient Class<T> clazz;
	long refId;

	public Ref() {
	}

	public Ref(Long refId) {
		this.refId = refId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getRefId() {
		return refId;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	abstract public Class<T> getClazz();

}
