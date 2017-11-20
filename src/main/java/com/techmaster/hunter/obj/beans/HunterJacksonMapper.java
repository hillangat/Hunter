package com.techmaster.hunter.obj.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.techmaster.hunter.constants.HunterConstants;



public class HunterJacksonMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5349858647635851774L;

	public HunterJacksonMapper() {
		
        DateFormat myDateFormat = new SimpleDateFormat(HunterConstants.HUNTER_DATE_FORMAT_MIN);
    }
	
}
