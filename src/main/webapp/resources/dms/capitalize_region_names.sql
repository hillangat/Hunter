
/* replace ward with whateber field you need */
SET SERVEROUTPUT ON;
DECLARE 
  
  curr_field rcvr_rgn.CNSTTNCY%TYPE := NULL;
  curr_field_util rcvr_rgn.CNSTTNCY%TYPE := NULL;
  
  space_pos number := 0;
  is_space_present BOOLEAN := false;
  curr_space_index number := 0;

BEGIN

FOR rec IN ( select RGN_ID,WRD from rcvr_rgn ) LOOP
 curr_field := TRIM( REC.WRD );
 is_space_present := curr_field like '% %';
 if( is_space_present ) then 
   while is_space_present LOOP
         curr_space_index := INSTR( curr_field, ' ' );         
         DBMS_OUTPUT.PUT_LINE( curr_field );
         curr_field_util := curr_field;         
         curr_field := substr( curr_field_util, 0, curr_space_index-1 ) || UPPER(substr( curr_field_util, curr_space_index, 2 )) || substr( curr_field_util, curr_space_index+2, LENGTH(curr_field_util) - curr_space_index );         
         DBMS_OUTPUT.PUT_LINE( curr_field );
         DBMS_OUTPUT.PUT_LINE( curr_space_index );
         curr_field_util := curr_field;
         curr_field := substr( curr_field_util, 0, curr_space_index-1 ) || ':' || substr( curr_field_util, curr_space_index+1, LENGTH(curr_field_util) - curr_space_index );
         DBMS_OUTPUT.PUT_LINE( curr_field );
         is_space_present := curr_field like '% %';    
   END LOOP;
    curr_field := REPLACE(curr_field,':',' ');
    UPDATE rcvr_rgn SET WRD = curr_field WHERE RGN_ID = rec.RGN_ID;
    DBMS_OUTPUT.PUT_LINE( '.............curr_field.................' || curr_field );
 end if;
END LOOP;

END;

