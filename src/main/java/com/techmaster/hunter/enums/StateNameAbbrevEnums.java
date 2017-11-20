package com.techmaster.hunter.enums;

public enum StateNameAbbrevEnums {
	
	ARIZONA("Arizona","AZ"),
	ARKANSAS("Arkansas","AR"),
	CALIFORNIA("California","CA"),
	COLORADO("Colorado","CO"),
	CONNECTICUT("Connecticut","CT"),
	DELAWARE("Delaware","DE"),
	DISTRICT_OF_COLUMBIA("District Of Colombia","DC"),
	FEDERATED_STATES_OF_MICRONESIA("Federated State Of Micronesia","FM"),
	FLORIDA("Florida","FL"),
	GEORGIA("Georgia","GA"),
	GUAM("Guam","GU"),
	HAWAII("Hawaii","HI"),
	IDAHO("Idaho","ID"),
	ILLINOIS("Illinois","IL"),
	INDIANA("Indiana","IN"),
	IOWA("Iowa","IA"),
	KANSAS("Kansas","KS"),
	KENTUCKY("Kentucky","KY"),
	LOUISIANA("Louisiana","LA"),
	MAINE("Maine","ME"),
	MARSHALL_ISLANDS("Marshall Islands","MH"),
	MARYLAND("Marylangat","MD"),
	MASSACHUSETTS("Massachusetts","MA"),
	MICHIGAN("Michigan","MI"),
	MINNESOTA("Minnesota","MN"),
	MISSISSIPPI("Mississippi","MS"),
	MISSOURI("Missouri","MO"),
	MONTANA("Montanta","MT"),
	NEBRASKA("Nebraska","NE"),
	NEVADA("Nevada","NV"),
	NEW_HAMPSHIRE("New Hamshire","NH"),
	NEW_JERSEY("New Jersey","NJ"),
	NEW_MEXICO("New Mexico","NM"),
	NEW_YORK("New York","NY"),
	NORTH_CAROLINA("North Carolina","NC"),
	NORTH_DAKOTA("North Dakota","ND"),
	NORTHERN_MARIANA_ISLANDS("Norhtern Mariana","MP"),
	OHIO("Ohio","OH"),
	OKLAHOMA("Oklahoma","OK"),
	OREGON("Oregon","OR"),
	PALAU("Palau","PW"),
	PENNSYLVANIA("Pennsylvania","PA"),
	PUERTO_RICO("Puerto Rico","PR"),
	RHODE_ISLAND("Rhode Island","RI"),
	SOUTH_CAROLINA("South Carolina","SC"),
	SOUTH_DAKOTA("South Dakota","SD"),
	TENNESSEE("Tennessee","TN"),
	TEXAS("Texas","TX"),
	UTAH("Utah","UT"),
	VERMONT("Vermont","VT"),
	VIRGIN_ISLANDS("Virgin Islands","VI"),
	VIRGINIA("Virginia","VA"),
	WASHINGTON("Washington","WA"),
	WEST_VIRGINIA("West Virginia","WV"),
	WISCONSIN("Wisconsin","WI"),
	WYOMING("Wyoming","WY");
	
	private final String stateName;
	private final String stateAbbrev;
	
	StateNameAbbrevEnums(String stateName, String stateAbbrev) {
		this.stateName = stateName; 
		this.stateAbbrev = stateAbbrev;
	}

	public String getStateName() {
		return stateName;
	}

	public String getStateAbbrev() {
		return stateAbbrev;
	}
	
	

}
