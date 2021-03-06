package si.iitech.lib.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import si.iitech.lib.controller.UserController;
import si.iitech.lib.entity.impl.EtExampleUser;


@Controller
@RequestMapping("/users")
public class UserControllerImpl extends UserController<EtExampleUser> {

	public UserControllerImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
		super(bCryptPasswordEncoder);
	}

}
