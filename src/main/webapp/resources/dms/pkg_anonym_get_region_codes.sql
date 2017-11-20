SET SERVEROUTPUT ON;

DECLARE
    
    country_name VARCHAR2(100) := 'Kenya';
    county_name VARCHAR2(100) := 'Bomet';
    state_name VARCHAR2(100) := NULL;
    constituency_name VARCHAR2(100) := 'Bomet Central';
    constituency_ward_name VARCHAR2(100) := 'Chesoen';
    
    country_code VARCHAR2(100) := '';
    county_code VARCHAR2(100) := '';
    state_code VARCHAR2(100) := '';
    constituency_code VARCHAR2(100) := '';
    constituency_ward_code VARCHAR2(100)  := '';


	country_id CNTRY.CNTRY_ID%TYPE;
	county_id CNTY.CNTY_ID%TYPE;
	state_id STATE.STATEID%TYPE;
	constituency_id CNSTTNCY.CNSTTNCY_ID%TYPE;
	constituency_ward_id CNSTTNCY_WRD.WRD_ID%TYPE;
  
 	region_count INTEGER(20);

BEGIN

  dbms_output.put_line('Getting started...');
  
  IF(country_name IS NOT NULL) THEN
   select count(*) into region_count from  CNTRY cc where cc.CNTRY_NAM = country_name  and ROWNUM = 1;
    IF(region_count >= 1) then 
      dbms_output.put_line('Country data found!');
      select cc.CNTRY_CODE,cc.CNTRY_ID INTO country_code,country_id from  CNTRY cc where cc.CNTRY_NAM = country_name  and ROWNUM = 1;
    ELSE 
  	 	dbms_output.put_line('No countries found!');
    end if;
  END IF;

  IF(state_name IS NOT NULL) THEN 
  	 select count(*) into region_count from  STATE st where st.CNTRY_ID = country_id  and  st.STATE_NAM = state_name  and ROWNUM = 1;
  	 IF(region_count >= 1) then
  	  	dbms_output.put_line('County data found!');
  	 	select st.STATE_CDE,st.STATE_ID INTO state_code,state_id  from  STATE st where st.CNTRY_ID = country_id  and  st.STATE_NAM = state_name  and ROWNUM = 1;
  	 ELSE 
  	 	dbms_output.put_line('No states found!');
  	 END IF; 
  END IF; 
  
   
  IF(county_name IS NOT NULL) THEN
  	 select count(*) into region_count from cnty cn where cn.CNTRY_ID = country_id and cn.CNTY_NAM = county_name  and ROWNUM = 1;
  	 IF(region_count >= 1) then
  	 	select cn.CNTY_CDE,cn.CNTY_ID INTO county_code,county_id  from  cnty cn where cn.CNTRY_ID = country_id and cn.CNTY_NAM = county_name  and ROWNUM = 1;
  	 ELSE 
  	 	dbms_output.put_line('No counties found!');
  	 END IF;
  END IF; 
  
  IF(constituency_name IS NOT NULL) THEN 
   	 select count(*) into region_count from CNSTTNCY cns where cns.CNTY_ID = county_id AND cns.CNSTTNCY_NAM = constituency_name  and ROWNUM = 1;
   	 IF(region_count >= 1) then
   	 	select cns.CNSTTNCY_CDE, cns.CNSTTNCY_ID INTO constituency_code,constituency_id  from  CNSTTNCY cns where cns.CNTY_ID = county_id AND cns.CNSTTNCY_NAM = constituency_name and ROWNUM = 1;
      	dbms_output.put_line('Cons Id >> ' || constituency_id);
  	 ELSE
      dbms_output.put_line('constituency_name >> ' || constituency_name);
  	 	dbms_output.put_line('No constituencies found!');
   	 END IF;
  END IF;  
  
  
  IF(constituency_ward_name IS NOT NULL) THEN
   	select count(*) into region_count from  CNSTTNCY_WRD cwrd where cwrd.CNSTTNCY_ID = constituency_id AND cwrd.WRD_NAM = constituency_ward_name and ROWNUM = 1;
    IF(region_count >= 1) then
     	select cwrd.CNSTTNCY_WRD_CODE, cwrd.WRD_ID INTO constituency_ward_code,constituency_ward_id  from  CNSTTNCY_WRD cwrd where cwrd.CNSTTNCY_ID = constituency_id AND cwrd.WRD_NAM = constituency_ward_name and ROWNUM = 1;
   	ELSE 
  	 	dbms_output.put_line('No constituenc wards found!');
    END IF;
  END IF; 
   
   dbms_output.put_line('Country code >> ' || country_code);
   dbms_output.put_line('County code >> ' || county_code);
   dbms_output.put_line('State code >> ' || state_code);
   dbms_output.put_line('Constituency code >> ' || constituency_code );
   dbms_output.put_line('Constituency Ward code >> ' || constituency_ward_code);
   
END;