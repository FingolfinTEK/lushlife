package lush.plugins.model;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultModelUpdate.class)
public interface ModelUpdater {

	public <T> T update(Class<T> clazz);

}
