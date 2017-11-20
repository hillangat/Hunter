SET SERVEROUTPUT ON;
ALTER SESSION SET NLS_DATE_FORMAT = 'DD-MMMM-YYYY HH12:MI:SS';

DECLARE

CURSOR region_cursor IS
	select distinct 
	(SELECT CC.CNTY_ID FROM CNTY CC WHERE CC.CNTY_NAM = rgn.CNTY and rownum = 1) "CNTY_ID", 
	rgn.cnty, 
	(SELECT cns.CNSTTNCY_ID FROM CNSTTNCY cns WHERE cns.CNSTTNCY_NAM = rgn.CNSTTNCY  and rownum = 1 )"CNSTTNCY_ID", 
	rgn.CNSTTNCY,
	rgn.WRD
	from RCVR_RGN rgn
	where rgn.WRD is not null 
	and rgn.wrd != 'Null'
	order by rgn.cnty, rgn.CNSTTNCY, rgn.WRD asc;


que_ry varchar2(4000);
next_region_id CNTY.CNTY_ID%type;
curr_region_nam varchar2(200);
same_name_count integer(6);
cons_id CNSTTNCY.CNSTTNCY_ID%TYPE;
record_counter number(19);

BEGIN

 select max(WRD_ID) + 1 into next_region_id from CNSTTNCY_WRD;
 record_counter := 0;
 
 IF(next_region_id IS NULL OR next_region_id = 0) THEN
  next_region_id := 1;
 END IF;

 FOR rcrd IN region_cursor 
  LOOP
    record_counter := record_counter + 1;
    curr_region_nam := rcrd.WRD;
    cons_id := rcrd.CNSTTNCY_ID;
    if(cons_id is null)
    then dbms_output.put_line('NULL COUNTY >>>>>>>>>>>>>> ' || rcrd.WRD);
    end if;
   dbms_output.put_line(next_region_id);
   INSERT INTO CNSTTNCY_WRD(WRD_ID,WRD_NAM,WRD_PPLTN, HNTR_PPLTN, CRET_DATE,LST_UPDT_DATE,CRTD_BY,LST_UPDTD_BY, CNSTTNCY_ID) 
   VALUES (next_region_id, curr_region_nam, 200, 200, SYSDATE, SYSDATE, 'hlangat01', 'hlangat01', cons_id);
    next_region_id := next_region_id + 1;
  END LOOP;
  dbms_output.put_line('Records found (' || record_counter ||')');
  
END;




