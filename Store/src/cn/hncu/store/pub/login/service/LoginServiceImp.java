package cn.hncu.store.pub.login.service;

import cn.hncu.container.annotation.Inpouring;
import cn.hncu.container.annotation.Service;
import cn.hncu.store.domain.User;
import cn.hncu.store.pub.login.dao.LoginDAO;
import cn.hncu.store.util.MySendMailThread;

@Service(value="LoginService")
public class LoginServiceImp implements LoginService{

	@Inpouring(name="LoginDAO")
	private LoginDAO iDao;
	
	@Override
	public boolean save(User user){
		boolean f=iDao.save(user);
		if(f){
			new Thread(new MySendMailThread(user)).start();
		}
		return f;
	}

	@Override
	public User login(User user) {
		return iDao.login(user);
	}

	@Override
	public boolean del(User user) {
		return iDao.del(user);
	}
	@Override
	public boolean update(User user) {
		return iDao.update(user);
	}
	
}
