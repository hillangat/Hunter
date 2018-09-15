package com.techmaster.hunter.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class ContactsStoreImpl implements ContactsStore {
	
	private List<Contact> contacts = new ArrayList<>();
	private long contactId = 1;

	@Override
	public void command(String command, Contact data) {
		if ( !command.equals("DELETE") && !command.equals("UPDATE") && !command.equals("ADD")) {
			throw new IllegalArgumentException( "Illegal command ( " + command + " ). Acceptables commands : 'ADD', 'DELETE' and 'UPDATE'" );
		}
		if ( command.equals("DELETE") ) {
			if ( contacts.contains(data) ) {
				contacts.remove(data);
			}
		} else if ( command.equals("ADD") ) {
			contacts.add(data);
		} else if ( command.equals("UPDATE") ) {
			Contact removContact = null;
			for ( Contact contact : contacts ) {
				if ( contact.getNationalId() == data.getNationalId() ) {
					removContact = contact;
					break;
				}
			}
			contacts.remove(removContact);
			contacts.add(data);
		}
	}

	@Override
	public List<Contact> find(String partialName) {
		List<Contact> found = new ArrayList<>();
		for ( Contact contact : contacts ) {
			if ( contact.getName().contains(partialName)) {
				found.add(contact);
			}
		}
		return found;
	}

	@Override
	public List<Contact> all() {
		return contacts;
	}

	@Override
	public int compare(Contact o1, Contact o2) {
		return (int)( o1.getNationalId() - o2.getNationalId() );
	}

}
