create or replace PROCEDURE GET_RGN_LVL_NMS_FOR_CNTIES 
(
  COUNTRY_NAM IN VARCHAR2 DEFAULT null , 
  CMM_SPRTD_CNTIS IN VARCHAR2 DEFAULT null,
  OUTPUT_REGION_NAME OUT VARCHAR2
) AS 

  l_count binary_integer;
  l_array dbms_utility.lname_array;
  
  curr_county cnty.CNTY_NAM%TYPE := null;
  curr_constituency CNSTTNCY.CNSTTNCY_NAM%TYPE := null;
  curr_constituency_wrd CNSTTNCY_WRD.WRD_NAM%TYPE := null;
  
  total_name varchar2(4000) := '';
  total_cons varchar2(2000) := '';
  total_cons_wrd varchar2(2000) := '';

  CURSOR cons_nams_cursor IS 
    SELECT 
      cns.CNSTTNCY_NAM, 
      cns.CNSTTNCY_ID, 
      cns.CNTY_ID, cn.CNTY_NAM
    FROM CNSTTNCY cns, CNTRY cntry, cnty cn
    WHERE cns.CNTY_ID = cn.CNTY_ID
    AND cn.CNTRY_ID = cntry.CNTRY_ID
    and cntry.CNTRY_NAM = COUNTRY_NAM order by cn.CNTY_NAM, cns.CNSTTNCY_NAM asc;

  CURSOR cons_wrds_nams_cursor IS 
    SELECT cns.CNTY_ID, cn.CNTY_NAM,wrd.WRD_ID, cns.CNSTTNCY_NAM, cns.CNSTTNCY_ID, wrd.WRD_NAM
    FROM CNSTTNCY cns, CNTRY cntry, cnty cn, CNSTTNCY_WRD wrd
    WHERE wrd.CNSTTNCY_ID = cns.CNSTTNCY_ID
    AND cns.CNTY_ID = cn.CNTY_ID
    AND cn.CNTRY_ID = cntry.CNTRY_ID
    and cntry.CNTRY_NAM = COUNTRY_NAM order by cn.CNTY_NAM, cns.CNSTTNCY_NAM, wrd.WRD_NAM asc;
  
BEGIN
  
  dbms_utility.comma_to_table( list   => regexp_replace(CMM_SPRTD_CNTIS,'(^|,)','\1x'), tablen => l_count, tab    => l_array);
  dbms_output.put_line('Number of counties passed in >> '  || l_count);
  
  for i in 1 .. l_count loop
    curr_county := substr(l_array(i),2);
     dbms_output.put_line('>>>>>>>>> ' || curr_county);
    FOR consRcrd IN cons_nams_cursor LOOP
      IF(consRcrd.CNTY_NAM = curr_county) THEN
        if(curr_constituency is null)then 
          curr_constituency := consRcrd.CNSTTNCY_NAM;
        end if;
          IF(curr_constituency != consRcrd.CNSTTNCY_NAM) THEN 
          dbms_output.put_line(consRcrd.CNSTTNCY_NAM);
          total_cons := total_cons || ',' || consRcrd.CNSTTNCY_NAM;
          curr_constituency := consRcrd.CNSTTNCY_NAM;
            FOR consWrdRcrd IN cons_wrds_nams_cursor LOOP
              IF(consWrdRcrd.CNSTTNCY_ID = consRcrd.CNSTTNCY_ID ) THEN
                  IF(curr_constituency_wrd is null) THEN 
                    curr_constituency_wrd := consWrdRcrd.WRD_NAM;
                  END IF;
                  IF(curr_constituency_wrd != consWrdRcrd.WRD_NAM)THEN 
                    curr_constituency_wrd := consWrdRcrd.WRD_NAM;
                    dbms_output.put_line('==' || curr_constituency_wrd);
                    total_cons_wrd := total_cons_wrd || ',' || curr_constituency_wrd;
                  ELSE 
                    --dbms_output.put_line(consWrdRcrd.WRD_NAM || '*');
                    NULL;
                  END IF;
              END IF;
            END LOOP;
        ELSE
          null;
        END IF;
               
      END IF;
      
    END LOOP;
  end loop;
  
  dbms_output.put_line('All counties >> ' || CMM_SPRTD_CNTIS);
  dbms_output.put_line('All cons >> ' || total_cons);
  dbms_output.put_line('All cons wards >> ' || total_cons_wrd);
  OUTPUT_REGION_NAME := CMM_SPRTD_CNTIS || total_cons || total_cons_wrd;
  dbms_output.put_line('Final OUTPUT_REGION_NAME >>>>>> ' || OUTPUT_REGION_NAME);
  
END GET_RGN_LVL_NMS_FOR_CNTIES;