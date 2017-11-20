package com.techmaster.hunter.email;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class GmailInbox {

 public static void main(String[] args) {
  GmailInbox gmail = new GmailInbox();
  gmail.read();
 }

 public void read() {
  Properties props = new Properties();
  try {
   InputStream in = this.getClass().getClassLoader().getResourceAsStream("smtp.properties");
   props.load(in);
   Session session = Session.getDefaultInstance(props, null);

   Store store = session.getStore("imaps");
   store.connect("smtp.gmail.com", "techmaster.hunter.mail@gmail.com","hunter.admin.1900");

   Folder inbox = store.getFolder("inbox");
   inbox.open(Folder.READ_ONLY);
   int messageCount = inbox.getMessageCount();

   System.out.println("Total Messages:- " + messageCount);

   Message[] messages = inbox.getMessages();
   System.out.println("------------------------------");
   for (int i = 0; i < 10; i++) {
      System.out.println("Mail Subject:- " + messages[i].getSubject());      
   }
   for (int i = 0; i < 10; i++) {
	      System.out.println("Mail content:- " + messages[i].getContent());      
	   }
   inbox.close(true);
   store.close();

  } catch (Exception e) {
   e.printStackTrace();
  }
 }

}
