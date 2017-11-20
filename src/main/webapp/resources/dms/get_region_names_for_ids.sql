create or replace Procedure get_region_names_for_ids 

(
    country_id IN CNTRY.CNTRY_ID%TYPE,
    county_id IN CNTY.CNTY_ID%TYPE,
    constituency_id IN CNSTTNCY.CNSTTNCY_ID%TYPE,
    constituency_ward_id IN CNSTTNCY_WRD.CNSTTNCY_ID%TYPE,
    
    country_name OUT VARCHAR2,
    county_name OUT VARCHAR2,
    constituency_name OUT VARCHAR2,
    constituency_ward_name OUT VARCHAR2
  
 )
 
IS
 	
BEGIN

	IF(country_id IS NOT NULL AND country_id != 0) THEN
		SELECT ct.CNTRY_NAM INTO country_name from CNTRY ct where ct.CNTRY_ID = country_id;
	END IF;
  
  IF(county_id IS NOT NULL AND county_id != 0) THEN
		SELECT cn.CNTY_NAM INTO county_name from CNTY cn where cn.CNTRY_ID = country_id and cn.CNTY_ID = county_id;
	END IF;
  
  IF(constituency_id IS NOT NULL AND constituency_id != 0) THEN
		SELECT cns.CNSTTNCY_NAM INTO constituency_name from CNSTTNCY cns where cns.CNSTTNCY_ID = constituency_id and cns.CNTY_ID = county_id;
	END IF;
  
  IF(constituency_ward_id IS NOT NULL AND constituency_ward_id != 0) THEN
		SELECT wrd.WRD_NAM INTO constituency_ward_name from CNSTTNCY_WRD wrd where wrd.WRD_ID = constituency_ward_id and wrd.CNSTTNCY_ID = constituency_id;
	END IF;
  
  dbms_output.put_line('country >> '  || country_name);
  dbms_output.put_line('county >> '  || county_name);
  dbms_output.put_line('cons >> '  || constituency_name);
  dbms_output.put_line('ward >> '  || constituency_ward_name);
  
   
END get_region_names_for_ids;