create or replace function GET_USER_LOGIN_DATA(user_name IN HNTR_USR.usr_nam%TYPE) RETURN VARCHAR AS

USR_DATA_XML XMLTYPE := XMLTYPE( '<loginData passCode="null" user_name="'|| user_name ||'" active="N" ><roles></roles></loginData>' );
pass_code HNTR_USR.PSSWRD%TYPE := null;
active    HNTR_USR.ACTIV%TYPE := null;
USR_ID   HNTR_USR.usr_nam%TYPE := 0;

blocked varchar2(10) := 'false';
LST_GN_DATE varchar2(20) := null;
failed_lgn_count number := 0;
login_exist varchar2(5) := 'false';

BEGIN 

SELECT u.PSSWRD, u.ACTIV,u.USR_ID INTO pass_code,active,USR_ID FROM HNTR_USR u WHERE u.USR_NAM = user_name AND u.ACTIV = 'Y';

SELECT UPDATEXML(USR_DATA_XML, '/loginData/@active',active  ) INTO USR_DATA_XML FROM DUAL;
SELECT UPDATEXML(USR_DATA_XML, '/loginData/@passCode',pass_code  ) INTO USR_DATA_XML FROM DUAL;

 FOR rec IN ( 
  SELECT
    u.USR_ID,
    u.USR_NAM,
    r.RL_NAM
  FROM
    HNTR_USR_RLS us,
    HNTR_USR u,
    USER_ROLE r
  WHERE us.USR_ID   = u.USR_ID
  AND u.USR_NAM = user_name
  AND r.ROLE_ID = us.ROLE_ID
) LOOP
  SELECT APPENDCHILDXML( USR_DATA_XML, '/loginData/roles', XMLTYPE('<role>'|| rec.RL_NAM ||'</role>') ) INTO USR_DATA_XML FROM DUAL;
END LOOP;

SELECT decode((SELECT count(c.USR_ID) FROM USR_LGN_BN c WHERE c.USR_ID = ( SELECT us.USR_ID FROM HNTR_USR us WHERE us.USR_NAM = user_name ) ), 0,'false','true') INTO login_exist  FROM dual;

IF( login_exist = 'true' )  THEN

      SELECT 
        DECODE( b.BLCKD, 'N','false','true' ),
        TO_CHAR(b.LST_LGN_TM, 'yyyy-MM-dd HH:mm:ss'),
        b.FLD_LGN_CNT,
        decode((SELECT count(c.USR_ID) FROM USR_LGN_BN c WHERE c.USR_ID =b.USR_ID), 0,'false','true') as EXIST
      INTO 
        blocked,
        LST_GN_DATE,
        failed_lgn_count,
        login_exist
      FROM 
        USR_LGN_BN b
      WHERE b.USR_ID = ( SELECT us.USR_ID FROM HNTR_USR us WHERE us.USR_NAM = user_name );
      
      SELECT APPENDCHILDXML( USR_DATA_XML, '/loginData', XMLTYPE('<login userId="0" lastLoginTime="2017-05-28 04:05:31" blocked="false" failedLoginCount="1" loginExist = "false" ></login>') ) INTO USR_DATA_XML FROM DUAL;
      SELECT UPDATEXML(USR_DATA_XML, '/loginData/login/@loginExist', login_exist  ) INTO USR_DATA_XML FROM DUAL;
      SELECT UPDATEXML(USR_DATA_XML, '/loginData/login/@userId', USR_ID  ) INTO USR_DATA_XML FROM DUAL;
      SELECT UPDATEXML(USR_DATA_XML, '/loginData/login/@lastLoginTime', LST_GN_DATE  ) INTO USR_DATA_XML FROM DUAL;
      SELECT UPDATEXML(USR_DATA_XML, '/loginData/login/@blocked', blocked  ) INTO USR_DATA_XML FROM DUAL;
      SELECT UPDATEXML(USR_DATA_XML, '/loginData/login/@failedLoginCount', failed_lgn_count  ) INTO USR_DATA_XML FROM DUAL;

END IF;

return USR_DATA_XML.getClobVal();

EXCEPTION WHEN NO_DATA_FOUND THEN RETURN NULL;

END;