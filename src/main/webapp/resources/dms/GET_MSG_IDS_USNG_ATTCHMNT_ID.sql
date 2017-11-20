create or replace PROCEDURE GET_MSG_IDS_USNG_ATTCHMNT_ID
(
    P_ATTACHMENT_ID IN NUMBER ,
    MSG_IDS OUT VARCHAR2 
)
AS
  l_input VARCHAR(4000)    := NULL;
  fnl_ids_str l_input%type := NULL;
  part1 l_input%type       := '';
  part2 l_input%type       := '';
  curr_attchmnt_id l_input%type := '';
  indx NUMBER(6)              := 0 ;
BEGIN

  FOR rec IN
  (SELECT m.msg_id,
    m.MSG_ATTCHMENTS
  FROM EMAIL_MSG m
  WHERE m.MSG_ATTCHMENTS IS NOT NULL
  )
  LOOP
    l_input                       := rec.MSG_ATTCHMENTS;
    WHILE( instr(l_input, '_ID_') !=0 )
    LOOP
      indx            := instr(l_input, ',');
      part1           := SUBSTR(l_input, 0, indx - 1 );
      part2           := SUBSTR(l_input, indx    +1, LENGTH(l_input));
      indx            := instr(l_input, '_ID_');
	     
	  curr_attchmnt_id := SUBSTR(part1, indx+4, LENGTH(part1) );
    
    IF( part1 = '' ) THEN
      part1  := part2;
      part2  := part1;
    END IF;
	  
	  IF( LOWER(curr_attchmnt_id) = LOWER( P_ATTACHMENT_ID || '' ) ) THEN
		IF( fnl_ids_str IS NULL ) THEN
			fnl_ids_str   := rec.msg_id;
		  ELSE
			fnl_ids_str := fnl_ids_str || ',' || rec.msg_id;
		  END IF;
	  END IF;
      
	  l_input         := part2;
	  
      IF( REGEXP_COUNT(l_input, '_ID_') = 1 ) THEN
        curr_attchmnt_id := SUBSTR(part2, indx+4, LENGTH(part2) );
        IF( curr_attchmnt_id = P_ATTACHMENT_ID || '' ) THEN
          fnl_ids_str                    := fnl_ids_str || ',' || rec.msg_id;
        END IF;
        l_input                        := '';
      END IF;
	  
    END LOOP;
  END LOOP;
  
  IF( fnl_ids_str IS NOT NULL AND lower( SUBSTR( fnl_ids_str, 0, 1 ) ) = lower(',') ) THEN
    fnl_ids_str := SUBSTR( fnl_ids_str, 2, LENGTH( fnl_ids_str ) );
  END IF;
  
  MSG_IDS := fnl_ids_str;
END GET_MSG_IDS_USNG_ATTCHMNT_ID;