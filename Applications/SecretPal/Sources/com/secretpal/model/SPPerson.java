package com.secretpal.model;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.webobjects.foundation.NSArray;

import er.extensions.foundation.ERXStringUtilities;

public class SPPerson extends _SPPerson {
	private static Logger log = Logger.getLogger(SPPerson.class);

	public static String hashPassword(String password) {
		return ERXStringUtilities.md5Hex(password, "UTF-8");
	}
	
	public void setPlainTextPassword(String password) {
		setPassword(SPPerson.hashPassword(password));
	}
	
	public boolean anyMembershipsConfirmed() {
		return memberships(SPMembership.CONFIRMED.isTrue()).count() > 0;
	}
	
	public void resetPassword() {
		setPassword(UUID.randomUUID().toString());
	}
	
	public String resetPasswordCode() {
		return password();
	}
	
	public NSArray<SPSecretPal> secretPalsForEvent(SPEvent event) {
		return SPSecretPal.GIVER.is(this).filtered(event.secretPals());
	}

	public NSArray<SPWish> desires() {
		return wishes(SPWish.SUGGESTED_BY.is(SPWish.SUGGESTED_FOR));
	}

	public NSArray<SPWish> suggestions() {
		return wishes(SPWish.SUGGESTED_BY.isNot(SPWish.SUGGESTED_FOR));
	}
}