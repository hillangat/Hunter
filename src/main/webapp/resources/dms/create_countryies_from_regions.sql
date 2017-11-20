SET SERVEROUTPUT ON;

DECLARE

CURSOR region_cursor IS SELECT distinct cntry FROM RCVR_RGN ORDER BY cntry ASC;
que_ry varchar2(4000);
next_region_id CNTRY.CNTRY_ID%type;
curr_region_nam varchar2(200);
same_name_count integer(6);

BEGIN

 select max(CNTRY_ID) + 1 into next_region_id from cntry;

 FOR rcrd IN region_cursor 
  LOOP
    curr_region_nam := rcrd.cntry;
    dbms_output.put_line(curr_region_nam);
    
    que_ry := 'INSERT INTO cntry(CNTRY_ID,CNTRY_NAM,CRET_DATE,LST_UPDT_DATE,CRTD_BY,LST_UPDTD_BY) VALUES (' 
    	|| next_region_id || ',''' 
    	|| curr_region_nam || ''',''' 
    	|| sysdate || ''',''' 
    	|| sysdate || ''',' 
    	|| '''hlangat01''' 
    	|| ',' || '''hlangat01''' 
    	|| ')';
    	
    select count(*) into same_name_count from cntry where CNTRY_NAM = curr_region_nam;
    if(same_name_count >= 1) THEN
    	dbms_output.put_line('Countries found with the same name = ( ' || same_name_count || ' )');
    ELSE
    	EXECUTE IMMEDIATE que_ry;
    end if;
    next_region_id := next_region_id + 1;
  END LOOP;

END;