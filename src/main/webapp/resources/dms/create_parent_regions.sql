create or replace PROCEDURE create_parent_regions 
(
  user_name IN varchar2 DEFAULT 'admin' 
) AS 
BEGIN
  DECLARE
  
    var_curr_region_id  RCVR_RGN.RGN_ID%TYPE := 0;
    var_curr_region_count INTEGER := 0;
    
BEGIN

  SELECT MAX(RGN_ID) INTO var_curr_region_id FROM RCVR_RGN;
  dbms_output.put_line('Max Region ID : ' || var_curr_region_id);
  
  FOR COUNTRY IN (  SELECT DISTINCT a.CNTRY FROM RCVR_RGN a ) LOOP
    var_curr_region_id := var_curr_region_id + 1;
    SELECT COUNT(*) INTO var_curr_region_count FROM RCVR_RGN c WHERE c.CNTRY = COUNTRY.CNTRY AND c.CNTY IS NULL AND c.CNSTTNCY IS NULL AND c.WRD IS NULL;
    dbms_output.put_line('var_curr_region_count COUNTRIES: ' || var_curr_region_count);
    IF var_curr_region_count = 0 THEN
    	INSERT INTO RCVR_RGN ( RGN_ID, CNTRY, STATE, HS_STATE, CNTY, CNSTTNCY, CTY, WRD, VLLG, LNGTTD, LTTD, CRRNT_LVL, BRDR_LGN_LTS, CRET_DATE, LST_UPDT_DATE, CRTD_BY, LST_UPDTD_BY )
    	VALUES( var_curr_region_id, COUNTRY.CNTRY, NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, SYSDATE, SYSDATE, user_name, user_name );
    	COMMIT;
    END IF;    
  END LOOP;
  
  FOR COUNTY IN ( SELECT DISTINCT b.CNTRY, b.CNTY FROM RCVR_RGN b ) LOOP
    var_curr_region_id := var_curr_region_id + 1;
    SELECT COUNT(*) INTO var_curr_region_count FROM RCVR_RGN c WHERE c.CNTRY = COUNTY.CNTRY AND c.CNTY =  COUNTY.CNTY AND c.CNSTTNCY IS NULL AND c.WRD IS NULL;
    dbms_output.put_line('var_curr_region_count COUNTIES : ' || var_curr_region_count);
    IF var_curr_region_count = 0 THEN
    	INSERT INTO RCVR_RGN ( RGN_ID, CNTRY, STATE, HS_STATE, CNTY, CNSTTNCY, CTY, WRD, VLLG, LNGTTD, LTTD, CRRNT_LVL, BRDR_LGN_LTS, CRET_DATE, LST_UPDT_DATE, CRTD_BY, LST_UPDTD_BY )
    	VALUES( var_curr_region_id, COUNTY.CNTRY, NULL, 'N', COUNTY.CNTY, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, SYSDATE, SYSDATE, user_name, user_name );
    	COMMIT;
    END IF;    
  END LOOP;
  
  FOR cc IN ( SELECT DISTINCT c.CNTRY AS a, c.CNTY as b, c.CNSTTNCY as c FROM RCVR_RGN c ) LOOP
    var_curr_region_id := var_curr_region_id + 1;
    SELECT COUNT(*) INTO var_curr_region_count FROM RCVR_RGN c WHERE c.CNTRY = cc.a AND c.CNTY =  cc.b AND c.CNSTTNCY = cc.c AND c.WRD IS NULL;
    dbms_output.put_line('var_curr_region_count CONSTITUENCIES: ' || var_curr_region_count);
    IF var_curr_region_count = 0 THEN
    	INSERT INTO RCVR_RGN ( RGN_ID, CNTRY, STATE, HS_STATE, CNTY, CNSTTNCY, CTY, WRD, VLLG, LNGTTD, LTTD, CRRNT_LVL, BRDR_LGN_LTS, CRET_DATE, LST_UPDT_DATE, CRTD_BY, LST_UPDTD_BY )
    	VALUES( var_curr_region_id, cc.a, NULL, 'N', cc.b, cc.c, NULL, NULL, NULL, NULL, NULL, NULL, NULL, SYSDATE, SYSDATE, user_name, user_name );
    	COMMIT;
    END IF;    
  END LOOP;

END;
END create_parent_regions;