package cn.hncu.store.pub.login.service;

import cn.hncu.container.annotation.Inpouring;
import cn.hncu.container.annotation.Service;
import cn.hncu.store.domain.User;
import cn.hncu.store.pub.login.dao.LoginDAO;

@Service(value="LoginService")
public class LoginServiceImp implements LoginService{

	@Inpouring(name="LoginDAO")
	private LoginDAO iDao;
	
	@Override
	public boolean save(User user){
		return iDao.save(user);
	}

	@Override
	public User login(User user) {
		return iDao.login(user);
	}
	
}
