package si.iitech.lib.entity.impl;

import javax.persistence.Entity;

import si.iitech.lib.entity.EtUser;

@Entity(name = "EXAMPLE_USER")
public class EtExampleUser extends EtUser {

	public EtExampleUser() {
	}

	public EtExampleUser(String email, String password) {
		super(email, password);
	}
}
