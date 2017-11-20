package com.techmaster.hunter.chats;

import java.util.ArrayList;
import java.util.List;


public class ChatHelper {
	
	private static ChatHelper instance;
	
	static{
		if( null == instance  ){
			synchronized (ChatHelper.class) { 
				instance = new ChatHelper();
			}
		}
	}
	
	
	public static ChatHelper getInstance(){
		return instance;
	}
	
	public List<ChatUser> getMyChatUsers(String myUserName){
		List<ChatUser> chUsers = new ArrayList<ChatUser>();
		
		return chUsers;
	}
	
	public List<Chat> getMyChatsWithThisChatUser(String myUserName, String myChatUser){
		List<Chat> chats = new ArrayList<Chat>();
		
		return chats;
	}
	
	
	
	

}
