package com.quovantis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class will store the detail of the contact mode for the customer
 * A user can opt for both email/sms notification
 * @author Vivek Gupta
 *
 */
@Entity
@Table(name="CONTACT_PREF")
public class ContactPreference {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long preference;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the preference
	 */
	public Long getPreference() {
		return preference;
	}

	/**
	 * @param preference the preference to set
	 */
	public void setPreference(Long preference) {
		this.preference = preference;
	}
	
}
