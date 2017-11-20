
CREATE OR REPLACE FUNCTION get_xml_from_blob ( blob_in IN BLOB ) return XMLTYPE IS
v_clob    CLOB;
v_varchar VARCHAR2(32767);
v_start   PLS_INTEGER := 1;
v_buffer  PLS_INTEGER := 32767;
x         xmltype;
BEGIN
DBMS_LOB.CREATETEMPORARY(v_clob, TRUE);
 FOR i IN 1 .. CEIL(DBMS_LOB.GETLENGTH(blob_in) / v_buffer) LOOP
   v_varchar := UTL_RAW.CAST_TO_VARCHAR2(DBMS_LOB.SUBSTR(blob_in,v_buffer,v_start));
   DBMS_LOB.WRITEAPPEND(v_clob, LENGTH(v_varchar), v_varchar);
   v_start := v_start + v_buffer;
 END LOOP;
 x := xmltype.createxml(v_clob);
 dbms_output.put_line(x.getclobval());
RETURN X;
EXCEPTION WHEN OTHERS THEN RETURN NULL;
end;



SELECT 
    xml.*
FROM 
  USR_LGN_BN b,
  XMLtable('//login' passing get_xml_from_blob(b.LGN_DATA) 
    COLUMNS 
    LOGIN_NUMBER NUMBER PATH '@loginNum',
    LOGIN_DATE VARCHAR2(100) PATH 'date',
    LOGIN_STATUS VARCHAR2(100) PATH 'status',
    LOGIN_IP VARCHAR2(100) PATH 'ip',
    LOGIN_USER_NAME VARCHAR2(100) PATH 'userName',
    LOGIN_PASSWORD VARCHAR2(100) PATH 'password',
    LOGIN_CURR_FAILED_COUNT NUMBER PATH 'currFailedCount'
  )xml
WHERE b.USR_ID = 1;