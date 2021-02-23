public class Deal {

	private String internalName;
	private String title;
	private String metacriticLink;
	private String dealID;
	private String storeID;
	private String gameID;
	private String salePrice;
	private String normalPrice;
	private String isOnSale;
	private String savings;
	private String metacriticScore;
	private String steamRatingText;
	private String steamRatingPercent;
	private String steamRatingCount;
	private String steamAppID;
	private long releaseDate;
	private long lastChange;
	private String dealRating;
	private String thumb;

	public String getInternalName () {
		return internalName;
	}

	public String getTitle () {
		return title;
	}

	public String getMetacriticLink () {
		return metacriticLink;
	}

	public String getDealID () {
		return dealID;
	}

	public String getStoreID () {
		return storeID;
	}

	public String getGameID () {
		return gameID;
	}

	public String getSalePrice () {
		return salePrice;
	}

	public String getNormalPrice () {
		return normalPrice;
	}

	public String getIsOnSale () {
		return isOnSale;
	}

	public String getSavings () {
		return savings;
	}

	public String getMetacriticScore () {
		return metacriticScore;
	}

	public String getSteamRatingText () {
		return steamRatingText;
	}

	public String getSteamRatingPercent () {
		return steamRatingPercent;
	}

	public String getSteamRatingCount () {
		return steamRatingCount;
	}

	public String getSteamAppID () {
		return steamAppID;
	}

	public long getReleaseDate () {
		return releaseDate;
	}

	public long getLastChange () {
		return lastChange;
	}

	public String getDealRating () {
		return dealRating;
	}

	public String getThumb () {
		return thumb;
	}

	@Override
	public String toString () {
		return "Deal{" + "internalName='" + internalName + '\'' + ", title='" + title + '\'' + ", metacriticLink='"
			+ metacriticLink + '\'' + ", dealID='" + dealID + '\'' + ", storeID='" + storeID + '\'' + ", gameID='"
			+ gameID + '\'' + ", salePrice='" + salePrice + '\'' + ", normalPrice='" + normalPrice + '\'' + ", isOnSale='"
			+ isOnSale + '\'' + ", savings='" + savings + '\'' + ", metacriticScore='" + metacriticScore + '\''
			+ ", steamRatingText='" + steamRatingText + '\'' + ", steamRatingPercent='" + steamRatingPercent + '\''
			+ ", steamRatingCount='" + steamRatingCount + '\'' + ", steamAppID='" + steamAppID + '\'' + ", releaseDate="
			+ releaseDate + ", lastChange=" + lastChange + ", dealRating='" + dealRating + '\'' + ", thumb='" + thumb
			+ '\'' + '}';
	}
}
