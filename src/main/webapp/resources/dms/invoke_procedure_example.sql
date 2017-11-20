SET serveroutput on;
DECLARE
    InParam1 number;
    OutParam1 varchar2(100);
BEGIN
    /* Assign values to IN parameters */
    InParam1 := 191;

    /* Call procedure within package, identifying schema if necessary */
	GET_MSG_IDS_USNG_ATTCHMNT_ID(InParam1,OutParam1);

    /* Display OUT parameters */
    dbms_output.put_line('OutParam1: ' || OutParam1);
END;
/