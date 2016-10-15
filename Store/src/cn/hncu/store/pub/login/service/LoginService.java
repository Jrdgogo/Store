package cn.hncu.store.pub.login.service;

import cn.hncu.container.annotation.TransAction;
import cn.hncu.store.domain.User;

public interface LoginService {
	
	@TransAction
	boolean save(User user);
	
	User login(User user);

	boolean del(User user);

	boolean update(User user);

}
