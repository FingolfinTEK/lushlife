package lush.application.lushup.model;

import java.util.List;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
public class Shops {
	List<Shop> shops;

	public List<Shop> getShops() {
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

}
