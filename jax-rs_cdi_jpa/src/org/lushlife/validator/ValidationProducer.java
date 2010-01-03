package org.lushlife.validator;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Singleton
public class ValidationProducer {
	private ValidatorFactory factory;

	@PostConstruct
	public void init() {
		this.factory = Validation.buildDefaultValidatorFactory();
	}

	@Produces
	public ValidatorFactory getFactory() {
		return factory;
	}

	@Produces
	public Validator getValidator() {
		return factory.getValidator();
	}
}
