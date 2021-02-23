import java.util.Map;

public class Store {
	private String storeID;
	private String storeName;
	private int isActive;
	private Map<String, String> images;

	public String getStoreID () {
		return storeID;
	}

	public String getStoreName () {
		return storeName;
	}

	public int getIsActive () {
		return isActive;
	}

	public Map<String, String> getImages () {
		return images;
	}

	@Override
	public String toString () {
		return storeName;
	}
}