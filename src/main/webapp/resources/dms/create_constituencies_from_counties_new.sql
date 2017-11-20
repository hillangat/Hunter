DECLARE 

cnty_id cnty.cnty_id%type := 0 ;

BEGIN

for rec in ( select r.CNTY,r.CNSTTNCY from RCVR_RGN r group by r.CNTY,r.CNSTTNCY order by  r.CNTY,r.CNSTTNCY asc) loop
  select cnty_id into cnty_id from cnty where cnty_nam = rec.cnty;
  INSERT INTO CNSTTNCY(CNSTTNCY_ID,CNSTTNCY_NAM,CNSTTNCY_PPLTN,HNTR_PPLTN,CNSTTNCY_CTY,CNTY_ID,CNSTTNCY_CDE,CRET_DATE,LST_UPDT_DATE,CRTD_BY,LST_UPDTD_BY )
  VALUES( ( SELECT NVL(MAX(CNSTTNCY_ID)+1, 1) FROM  CNSTTNCY), rec.CNSTTNCY,4000,0,null, cnty_id,254,sysdate,sysdate,'admin','admin'  );
end loop;

END;

select * from CNSTTNCY where cnty_id = (select cnty_id from cnty where cnty_nam = 'Nairobi');