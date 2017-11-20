package com.techmaster.hunter.rawreceivers;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.picketbox.commons.cipher.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.cache.HunterCacheUtil;
import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.proc.ProcedureHandler;
import com.techmaster.hunter.dao.types.HunterJDBCExecutor;
import com.techmaster.hunter.dao.types.HunterMessageReceiverDao;
import com.techmaster.hunter.dao.types.HunterRawReceiverDao;
import com.techmaster.hunter.dao.types.HunterRawReceiverUserDao;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.json.HunterRawReceiverJson;
import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.obj.beans.Constituency;
import com.techmaster.hunter.obj.beans.ConstituencyWard;
import com.techmaster.hunter.obj.beans.Country;
import com.techmaster.hunter.obj.beans.County;
import com.techmaster.hunter.obj.beans.HunterMessageReceiver;
import com.techmaster.hunter.obj.beans.HunterRawReceiver;
import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.region.RegionService;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class RawReceiverServiceImpl implements RawReceiverService {
	
	@Autowired private HunterRawReceiverDao hunterRawReceiverDao;
	@Autowired private HunterJDBCExecutor hunterJDBCExecutor;
	@Autowired private HunterRawReceiverUserDao hunterRawReceiverUserDao;
	@Autowired private ProcedureHandler get_region_names_for_ids;
	@Autowired private RegionService regionService;
	
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public HunterRawReceiverUser registerRawReceiverUser(HunterRawReceiverUser rawReceiverUser) {
		logger.debug("Registering lograwReceiverUser ..."); 
		hunterRawReceiverUserDao.insertRawUser(rawReceiverUser); 
		logger.debug("Done registering lograwReceiverUser!!"); 
		return rawReceiverUser;
	}

	@Override
	public List<HunterRawReceiver> getPayableRawReceivers(HunterRawReceiverUser hunterRawReceiverUser) {
		return hunterRawReceiverDao.getAllHunterRawReceiversByRawUser(hunterRawReceiverUser);
	}


	@Override
	public int getRawReceiverVersion(String givenByUserName) {
		List<Object> values = new ArrayList<>();
		values.add("givenByUserName"); 
		Object nextVersion = hunterJDBCExecutor.executeQueryForOneReturn(hunterJDBCExecutor.getQueryForSqlId("getNextRawReceiverVersion"), values);
		if(nextVersion != null){
			return Integer.parseInt(nextVersion.toString());
		}
		logger.debug("Returning version one.."); 
		return 1;
	}

	@Override
	public Map<String, String> validateRawReceiver(HunterRawReceiver hunterRawReceiver) {
		return null;
	}
	

	@Override
	public Map<String, String> payRawReceiverUser(HunterRawReceiverUser rawReceiverUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HunterRawReceiver> getDefaultRawReceivers() {
		List<HunterRawReceiver>  receivers = new ArrayList<HunterRawReceiver>();
		List<HunterRawReceiver> existing = getAllRawReceiversForUser(hunterRawReceiverUserDao.getRawUserByUserName("admin"));
		boolean insert = existing != null && !existing.isEmpty();
		
		if(insert){
			return existing;
		}
		
		for(int i=0; i<500; i++){
			HunterRawReceiver receiver = new HunterRawReceiver();
			receiver.setAuditInfo(HunterUtility.getAuditInfoFromRequestForNow(null, "admin"));
			receiver.setConsId(9L);
			receiver.setConsName("Bomet East");
			receiver.setConsWardId(37L);
			receiver.setConsWardName("Chemaner");
			receiver.setCountryId(1L);
			receiver.setCountryName("Kenya"); 
			receiver.setCountyId(4L); 
			receiver.setCountyName("Bomet"); 
			receiver.setErrorMessage("");
			receiver.setFirstName("Hillary " + i); 
			receiver.setLastName("Langat " + i); 
			receiver.setGivenByUserName("rlangat");  
			receiver.setRawReceiverId(i); 
			receiver.setReceiverContact("73247048" + i); 
			receiver.setReceiverType("text"); 
			receiver.setReceiverVersion(1);
			receiver.setVerified(i%3 == 0 ? false : true);
			receiver.setVerifiedBy(i%3 == 0 ? null : "admin");  
			receiver.setVillage(null);
			receiver.setVillageId(0L); 
			receivers.add(receiver);
			setRegionIdsForReceiver(receiver);
		}
		
		
		if(!insert){
			hunterRawReceiverDao.insertHunterRawReceivers(receivers); 
		}
		
		return receivers;
	}


	@Override
	public List<HunterRawReceiver> getAllRawReceiversForUser(HunterRawReceiverUser hunterRawReceiverUser) {
		String query = "FROM HunterRawReceiver r WHERE r.givenByUserName = '" + hunterRawReceiverUser.getRawUserName() + "'";
		logger.debug("Executing query : " + query); 
		List<HunterRawReceiver> list =  HunterDaoFactory.getObject(HunterHibernateHelper.class).executeQueryForObjList(HunterRawReceiver.class, query);
		if(list != null && !list.isEmpty()){
			logger.debug("Setting region ids for raw receivers..."); 
			for(HunterRawReceiver rawReceiver : list){
				setRegionIdsForReceiver(rawReceiver); 
			}
			logger.debug("Finished setting region IDS for raw receivers...");
		}
		logger.debug("Found ( " + list.size() + " ) raw receivers for user ( " + hunterRawReceiverUser.getRawUserName() + " )");  
		return list;
	}

	@Override
	public HunterRawReceiver createRawReceiver(Map<String, String> params, AuditInfo auditInfo) {
		logger.debug("Creating hunter raw receiver..." + params.get("receiverContact"));  
		HunterRawReceiver receiver = new HunterRawReceiver();
		receiver.setRawReceiverId(0);
		receiver.setReceiverContact(params.get("receiverContact"));
		receiver.setReceiverType(params.get("receiverType"));
		receiver.setFirstName(params.get("firstName"));
		receiver.setLastName(params.get("lastName"));
		receiver.setCountryName(params.get("countryName"));
		receiver.setCountyName(params.get("countyName"));
		receiver.setConsName(params.get("consName"));
		receiver.setConsWardName(params.get("consWardName"));
		receiver.setVerified(Boolean.valueOf(params.get("verified")));
		receiver.setGivenByUserName(params.get("givenByUserName"));
		hunterRawReceiverDao.insertHunterRawReceiver(receiver); 
		logger.debug("Successfully creatred hunter raw receiver..." + params.get("receiverContact")); 
		return receiver;
	}

	@Override
	public HunterRawReceiver updateRawReceiver(Map<String, String> params, AuditInfo auditInfo) {
		String rawReceiverId = params.get("rawReceiverId");
		logger.debug("Updating contact with id : " + rawReceiverId + ", and contact : " + params.get("receiverContact")); 
		Long receiverId = HunterUtility.getLongFromObject(rawReceiverId); 
		HunterRawReceiver receiver = hunterRawReceiverDao.getHunterRawReceiverById(receiverId);
		receiver.setRawReceiverId(0);
		receiver.setReceiverContact(params.get("receiverContact"));
		receiver.setReceiverType(params.get("receiverType"));
		receiver.setFirstName(params.get("firstName"));
		receiver.setLastName(params.get("lastName"));
		receiver.setCountryName(params.get("countryName"));
		receiver.setCountyName(params.get("countyName"));
		receiver.setConsName(params.get("consName"));
		receiver.setConsWardName(params.get("consWardName"));
		receiver.setVerified(Boolean.valueOf(params.get("verified")));
		receiver.setGivenByUserName(params.get("givenByUserName"));
		receiver.setReceiverVersion(getRawReceiverVersion(params.get("givenByUserName")));
		receiver.getAuditInfo().setLastUpdate(new Date()); 
		receiver.getAuditInfo().setLastUpdatedBy(auditInfo.getLastUpdatedBy());
		hunterRawReceiverDao.insertHunterRawReceiver(receiver);
		logger.debug("Successfully updated contact!");
		return receiver;
	}

	@Override
	public void setRegionIdsForReceiver(HunterRawReceiver hunterRawReceiver) {
		
		String countryName = hunterRawReceiver.getCountryName();
		String countyName = hunterRawReceiver.getCountyName();
		String consName = hunterRawReceiver.getConsName();
		String wardName = hunterRawReceiver.getConsWardName();
		
		Long 
		countryId 	= 0L,
		countyId 	= 0L,
		consId 		= 0L,
		wardId 		= 0L;
		
		List<Country> countries = HunterCacheUtil.getInstance().getAllCountries();
		
		country:for(Country country : countries){
			if(country.getCountryName().equals(countryName)){
				countryId = country.getCountryId();
				Set<County> counties = country.getCounties();
				for(County county : counties){
					if(county.getCountyName().equals(countyName)){
						countyId = county.getCountryId();
						Set<Constituency> constituencies = county.getConstituencies();
						for(Constituency constituency : constituencies) {
							if(constituency.getCnsttncyName().equals(consName)){
								consId = constituency.getCnsttncyId();
								Set<ConstituencyWard> wards = constituency.getConstituencyWards();
								for(ConstituencyWard ward : wards){
									if(ward.getWardName().equals(wardName)){
										wardId = ward.getWardId();
										break country;
									}
								}
							}
						}
					}
				}
			}
		}
		
		hunterRawReceiver.setCountryId(countryId);
		hunterRawReceiver.setCountyId(countyId);
		hunterRawReceiver.setConsId(consId);
		hunterRawReceiver.setConsWardId(wardId); 
		
	}

	@Override
	public JSONObject createOrUpdateRawReceiver(Map<String, String> rawReceiverData, AuditInfo auditInfo) {
		
		HunterUtility.threadSleepFor(1000); 
		JSONObject json = new JSONObject();
		HunterRawReceiver receiver = new HunterRawReceiver();
		
		Map<String,Long> regionsIds = new HashMap<>();
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_COUNTRY, Long.parseLong(rawReceiverData.get("country")+""));
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_COUNTY, Long.parseLong(rawReceiverData.get("county")+""));
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, Long.parseLong(rawReceiverData.get("cons")+""));
		regionsIds.put(HunterConstants.RECEIVER_LEVEL_WARD, Long.parseLong(rawReceiverData.get("ward")+""));
		
		Map<Long,String> country = HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTRY, regionsIds);
		Map<Long,String> county = HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_COUNTY, regionsIds);
		Map<Long,String> constituency = HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_CONSITUENCY, regionsIds);
		Map<Long,String> ward = HunterCacheUtil.getInstance().getNameIdForId(HunterConstants.RECEIVER_LEVEL_WARD, regionsIds);
		
		String countryName = country.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_COUNTRY)); 
		String countyName = county.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_COUNTY));
		String constituencyName = constituency.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_CONSITUENCY));
		String wardName = ward.get(regionsIds.get(HunterConstants.RECEIVER_LEVEL_WARD));
		
		
		Long rawReceiverId = HunterUtility.getLongFromObject(rawReceiverData.get("id"));
		int version = 1;
		boolean update = false;
				
		if( rawReceiverId != null && rawReceiverId > 0 ){
			version = getRawReceiverVersion(receiver.getGivenByUserName());
			update = true;
		}
		
		try {
			
			receiver.setRawReceiverId(rawReceiverId);
			receiver.setReceiverContact(rawReceiverData.get("contact"));
			receiver.setReceiverType(rawReceiverData.get("conactType"));
			receiver.setFirstName(rawReceiverData.get("firstName"));
			receiver.setLastName(rawReceiverData.get("lastName"));
			receiver.setGivenByUserName(auditInfo.getLastUpdatedBy());
			receiver.setCountryName(countryName);
			receiver.setCountyName(countyName);
			receiver.setConsName(constituencyName);
			receiver.setConsWardName(wardName);
			receiver.setReceiverVersion(version); 
			receiver.setAuditInfo(HunterUtility.getAuditInfoFromRequestForNow(null, auditInfo.getLastUpdatedBy())); 
			
			receiver.setVerified(false); 
			receiver.setErrorMessage(null); 
			receiver.setVerifiedBy(null);
			receiver.setVillageId(null);
			receiver.setVillage(null); 
			
			Map<String, String> validate = validateRawReceiver(receiver);
			
			if( validate != null && !validate.isEmpty()){
				StringBuilder message = new StringBuilder();
				for(Map.Entry<String, String> entry : validate.entrySet()){
					String value = entry.getValue();
					message.append(value).append(",");
				}
				String msgStr = message.toString();
				if(msgStr.endsWith(",")){
					msgStr = msgStr.substring(0,msgStr.length() - 1);
				}
				HunterUtility.setJSONObjectForFailure(json, "Invalid data : " +msgStr);
			}else{
				if(update){
					hunterRawReceiverDao.updateHunterRawReceiver(receiver);
				}else{
					hunterRawReceiverDao.insertHunterRawReceiver(receiver);
					updateRawReceiverCountsForUser(auditInfo.getLastUpdatedBy());
				}
				HunterUtility.setJSONObjectForSuccess(json, "Successfully "+ ( update? "updated" : "created"  )+" contact!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			HunterUtility.setJSONObjectForFailure(json, "And error occurred while "+ ( update? "updating" : "creating"  )+" this contact! Please contact Hunter Support!");
		} 
		
		return json;
	}

	@Override
	public void updateRawReceiverCountsForUser(String userName) {
		logger.debug("Updating the count of raw receivers for user : " + userName); 
		String query = hunterJDBCExecutor.getQueryForSqlId("updateRawReceiverCountsForFieldUser");
		List<Object> values = new ArrayList<>();
		values.add(userName);
		values.add(userName);
		hunterJDBCExecutor.executeUpdate(query, values);
		logger.debug("Successfully finished updating the count!"); 
	}

	@Override
	public String getBase64PhotoForUser(String userName) {
		HunterUser user = HunterDaoFactory.getObject(HunterUserDao.class).getUserByUserName(userName);
		Blob userPhoto = user.getUserProfPhoto() != null ? user.getUserProfPhoto().getPhotoBlob() : null;
		byte[] bytes = null;
		
		if( userPhoto != null ){
			try {
				bytes = userPhoto.getBytes(1, (int)userPhoto.length());
				if( bytes != null && bytes.length > 0 ){
					String b64Str = Base64.encodeBytes(bytes);
					logger.debug(b64Str); 
					return b64Str;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public List<String> getDistinctContactsForUser(String userName) {
		logger.debug("Pulling up existing contacts for the user : " + userName); 
		List<String> contacts = new ArrayList<>();
		String query = hunterJDBCExecutor.getQueryForSqlId("getDstntRwRcvrCntctForUser");
		List<Object> values = new ArrayList<>();
		values.add(userName);
		Map<Integer, List<Object>> rowMapLists = hunterJDBCExecutor.executeQueryRowList(query, values);
		if(rowMapLists != null && !rowMapLists.isEmpty()){
			for(Map.Entry<Integer, List<Object>> entry : rowMapLists.entrySet()){
				List<Object> contact_ = entry.getValue();
				contacts.add(HunterUtility.getStringOrNullOfObj(contact_ != null && !contact_.isEmpty() ? contact_.get(0) : null)); 
			}
		}
		logger.debug("Successfully pulled up distinct contacts. Size ( "+ contacts.size() +" )");
		return contacts;
	}

	@Override
	public List<HunterRawReceiverJson> getRawReceiverJsonForDbMap(List<Map<String, Object>> rowMapList) {
		
		List<HunterRawReceiverJson> receiverJsons = new ArrayList<>();
		
		for(Map<String,Object> rowMap : rowMapList){
			HunterRawReceiverJson receiverJson = new HunterRawReceiverJson();
			receiverJson.setCretDate( HunterUtility.parseDate(HunterUtility.getStringOrNullOfObj( rowMap.get("CRET_DATE")), HunterConstants.DATE_FORMAT_STRING ) );  
			receiverJson.setCreatedBy( HunterUtility.getStringOrNullOfObj( rowMap.get("CRTD_BY") ));
			receiverJson.setLastUpdate( HunterUtility.parseDate(HunterUtility.getStringOrNullOfObj( rowMap.get("LST_UPDT_DATE")), HunterConstants.DATE_FORMAT_STRING ) );
			receiverJson.setLastUpdatedBy( HunterUtility.getStringOrNullOfObj( rowMap.get("LST_UPDTD_BY") )); 
			receiverJson.setConsId(null);
			receiverJson.setConsName( HunterUtility.getStringOrNullOfObj( rowMap.get("CONS_NAM")) );
			receiverJson.setConsWardId(null);
			receiverJson.setConsWardName( HunterUtility.getStringOrNullOfObj( rowMap.get("WRD_NAM")) );
			receiverJson.setCountryId(null);
			receiverJson.setCountryName( HunterUtility.getStringOrNullOfObj( rowMap.get("CNTRY_NAM")) ); 
			receiverJson.setCountyId(null); 
			receiverJson.setCountyName( HunterUtility.getStringOrNullOfObj( rowMap.get("CNTY_NAM")) ); 
			receiverJson.setErrorMessage( HunterUtility.getStringOrNullOfObj( rowMap.get("ERR_MSG")) );
			receiverJson.setFirstName( HunterUtility.getStringOrNullOfObj( rowMap.get("FRST_NAM")) ); 
			receiverJson.setLastName( HunterUtility.getStringOrNullOfObj( rowMap.get("LST_NAM")) ); 
			receiverJson.setGivenByUserName( HunterUtility.getStringOrNullOfObj( rowMap.get("GVN_BY_USR_NAM")) );  
			receiverJson.setRawReceiverId( HunterUtility.getLongFromObject( rowMap.get("RW_RCVR_ID") ) );  
			receiverJson.setReceiverContact( HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_CNTCT")) ); 
			receiverJson.setReceiverType( HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_TYP")) ); 
			receiverJson.setReceiverVersion(1);
			receiverJson.setVerified( HunterUtility.getBooleanForYN( rowMap.get("VRYFD") + "" ) ); 
			receiverJson.setVerifiedBy( HunterUtility.getStringOrNullOfObj( rowMap.get("VRYFD_BY")) );  
			receiverJson.setVillage( HunterUtility.getStringOrNullOfObj( rowMap.get("VLLG")) );
			receiverJson.setVillageId(null); 
			receiverJsons.add(receiverJson);
		}
		
		return receiverJsons;
	}

	
	
	
	@Override
	public List<HunterMessageReceiver> createHntrMsgReceiversForRawReceivers(String rawReceiverIds, AuditInfo auditInfo) {
		
		List<HunterMessageReceiver> hunterMessageReceivers = new ArrayList<>();
		HunterMessageReceiverDao msgReceiverDao = HunterDaoFactory.getObject(HunterMessageReceiverDao.class);
		HunterJDBCExecutor hunterJDBCExecutor = HunterDaoFactory.getObject(HunterJDBCExecutor.class);
		Map<String,Object> params = new HashMap<>();
		params.put(":receiverIds", rawReceiverIds);
		String query = hunterJDBCExecutor.getReplacedAllColonedParamsQuery("getJoinedHntrMsgRcvrsAndRawRcvrs", params);
		List<Map<String, Object>> rowMapList = hunterJDBCExecutor.executeQueryRowMap(query, null);
		StringBuilder activeReceiverIds = new StringBuilder();
		
		if( HunterUtility.isCollectionNotEmpty(rowMapList) ){ 
			
			for(Map<String, Object> rowMap : rowMapList){
				
				String contact = HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_CNTCT_MSG") ) ;
				String receiverId = HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_ID") ) ;
				boolean exists = HunterUtility.notNullNotEmpty(contact);
				
				if( exists ){
					
					activeReceiverIds.append( receiverId ).append(",");  
					
				}else{
					
					String countryName = HunterUtility.getStringOrNullOfObj( rowMap.get("CNTRY_NAM") );
					String consName = HunterUtility.getStringOrNullOfObj( rowMap.get("CONS_NAM") );
					String countyName = HunterUtility.getStringOrNullOfObj( rowMap.get("CNTY_NAM") );
					String wardName = HunterUtility.getStringOrNullOfObj( rowMap.get("WRD_NAM") );
					String receiverType = HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_TYP") );
					
					
					HunterMessageReceiver hunterMessageReceiver = new HunterMessageReceiver();
					hunterMessageReceiver.setReceiverContact( HunterUtility.getStringOrNullOfObj( rowMap.get("RCVR_CNTCT") ) ); 
					hunterMessageReceiver.setGivenByUserName( HunterUtility.getStringOrNullOfObj( rowMap.get("GVN_BY_USR_NAM") ));
					hunterMessageReceiver.setActive(true);
					hunterMessageReceiver.setAuditInfo(auditInfo); 
					hunterMessageReceiver.setBlocked(false);
					hunterMessageReceiver.setConsName( consName ); 
					hunterMessageReceiver.setConsWardName( wardName );
					hunterMessageReceiver.setCountryName( countryName  ); 
					hunterMessageReceiver.setCountyName( countyName ); 
					hunterMessageReceiver.setFailDeliveryTimes( 0 ); 
					hunterMessageReceiver.setFirstName( HunterUtility.getStringOrNullOfObj( rowMap.get("FRST_NAM") ) ); 
					hunterMessageReceiver.setLastName( HunterUtility.getStringOrNullOfObj( rowMap.get("FRST_NAM") )  );
					hunterMessageReceiver.setReceiverRegionLevel( HunterUtility.getLevelNameOrType(HunterConstants.REGION_LEVEL_Type, countryName, countyName, consName, wardName) );  
					hunterMessageReceiver.setReceiverRegionLevelName( HunterUtility.getLevelNameOrType(HunterConstants.REGION_LEVEL_Type, countryName, countyName, consName, wardName) ); 
					hunterMessageReceiver.setReceiverType(receiverType); 
					hunterMessageReceiver.setStateName(null);
					hunterMessageReceiver.setStateName(null); 
					hunterMessageReceiver.setSuccessDeliveryTimes(0); 
				
					hunterMessageReceivers.add(hunterMessageReceiver);
					
				}
				
			}
			
			
			if( HunterUtility.isCollectionNotEmpty(hunterMessageReceivers) ){
				logger.debug("HunterMessageReceivers created. Size : " + hunterMessageReceivers.size()); 
				msgReceiverDao.insertHunterMessageReceivers(hunterMessageReceivers); 
			}else{
				logger.debug("No hunter message receivers created! Moving on..."); 
			}
			
			if( HunterUtility.notNullNotEmpty( activeReceiverIds.toString() )){
				
				String activeReceiverIdsStr = null;
				
				if( activeReceiverIds.toString().endsWith(",") ){
					activeReceiverIdsStr = activeReceiverIds.substring(0, activeReceiverIds.length() - 1);
				}else{
					activeReceiverIdsStr = activeReceiverIds.toString();
				}
				
				logger.debug("There are some receivers who are already there that need to be updated("+ activeReceiverIds +"). Updating the receivers...");   
				Map<String,Object> repParams = new HashMap<>();
				repParams.put(":RCVR_ID", activeReceiverIdsStr);
				repParams.put(":lst_updated_by", HunterUtility.singleQuote(auditInfo.getLastUpdatedBy())); 
				String update = hunterJDBCExecutor.getReplacedAllColonedParamsQuery("getUpdateHunterMsgReceiversBadedOnRawReceivers", repParams);
				int rowsUpdated = hunterJDBCExecutor.executeUpdate(update, null);
				logger.debug("Rows updated : " + rowsUpdated); 
			}
			
		}
		
		return hunterMessageReceivers;
	}
	
	
	
	
	
	
	
	
	
	

}
