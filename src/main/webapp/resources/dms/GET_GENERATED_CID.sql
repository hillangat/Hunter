create or replace Procedure get_Generated_CId
(
bean_id IN MSG_ATTCHMNT_BEAN.BEAN_ID%TYPE,
p_bean_cid out varchar
) 
IS

BEGIN

SELECT 
  (SELECT REGEXP_REPLACE( bn.BEAN_NAME, '[[:space:]]', '_' )
    || '_'
    || bn.BEAN_ID
    || '_'
    ||( SUBSTR(
    ( SELECT dbms_random.value(1,100000)||'''' FROM dual
    ), 0, 5 ) ) AS cid
  FROM dual
  )
  into p_bean_cid
FROM MSG_ATTCHMNT_BEAN bn where bn.BEAN_ID = bean_id and ROWNUM = 1;

DBMS_OUTPUT.PUT(p_bean_cid);
		
END;