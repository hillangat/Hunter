DECLARE 

cnty_id cnty.cnty_id%type := 0 ;
cons_id CNSTTNCY.CNSTTNCY_ID%type := 0 ;

BEGIN

for rec in ( select r.CNTY,r.CNSTTNCY,r.WRD from RCVR_RGN r group by r.CNTY,r.CNSTTNCY,r.WRD order by  r.CNTY,r.CNSTTNCY,r.WRD asc ) loop
  select cnty_id into cnty_id from cnty where cnty_nam = rec.cnty;
  select CNSTTNCY_ID into cons_id from CNSTTNCY where CNSTTNCY_NAM = rec.CNSTTNCY;
  INSERT INTO CNSTTNCY_WRD(WRD_ID,WRD_NAM,WRD_PPLTN,HNTR_PPLTN,MP_DTS,CNSTTNCY_ID,CNSTTNCY_WRD_CODE,CRET_DATE,LST_UPDT_DATE,CRTD_BY,LST_UPDTD_BY)
  VALUES( ( SELECT NVL(MAX(WRD_ID)+1, 1) FROM  CNSTTNCY_WRD), rec.WRD,0,0,null,cons_id, 254,sysdate,sysdate,'admin','admin');
end loop;

END;