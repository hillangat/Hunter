package com.techmaster.hunter.hackerrank;

import java.util.Comparator;
import java.util.List;

public interface ContactsStore extends Comparator<Contact>{
	void command( String command, Contact data );
	List<Contact> find( String partialName );
	List<Contact> all();
}
