SET SERVEROUTPUT ON;
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MMMM-YYYY HH12:MI:SS';

DECLARE

CURSOR region_cursor IS SELECT distinct (select cc.cnty_id from cnty cc where cc.cnty_nam = rgn.cnty and rownum = 1) as CNTY_ID, rgn.CNTY ,rgn.CNSTTNCY  FROM RCVR_RGN rgn ORDER BY rgn.CNTY,rgn.CNSTTNCY ASC;


next_region_id CNSTTNCY.CNSTTNCY_ID%type;
curr_region_nam varchar2(200);
same_name_count integer(6);
county_id CNTY.CNTY_ID%TYPE;
record_counter number(6);

BEGIN

 select max(CNSTTNCY_ID) + 1 into next_region_id from CNSTTNCY;
 record_counter := 0;
 
 IF(next_region_id IS NULL OR next_region_id = 0) THEN
  next_region_id := 1;
 END IF;

 FOR rcrd IN region_cursor 
  LOOP
    record_counter := record_counter + 1;
    curr_region_nam := rcrd.CNSTTNCY;
    county_id := rcrd.CNTY_ID;
        
          	
    select count(*) into same_name_count from CNSTTNCY where CNSTTNCY_NAM = curr_region_nam;
    if(same_name_count >= 1) THEN
    	dbms_output.put_line('consituencies found with the same name = ( ' || same_name_count || ' )');
    ELSE
    	INSERT INTO CNSTTNCY(CNSTTNCY_ID,CNSTTNCY_NAM,CNSTTNCY_PPLTN, HNTR_PPLTN, CRET_DATE,LST_UPDT_DATE,CRTD_BY,LST_UPDTD_BY, CNTY_ID) 
    	VALUES (next_region_id,curr_region_nam, 60000, 3000, SYSDATE, SYSDATE,  'hlangat01', 'hlangat01', county_id);
    end if;
    next_region_id := next_region_id + 1;
  END LOOP;
  
END;





