package lushfile.core.controller;

public interface LushController {

	LushController init(String packageName);

	public String getPackageName();

	void controll();

	LushController init(Package pack);

}
