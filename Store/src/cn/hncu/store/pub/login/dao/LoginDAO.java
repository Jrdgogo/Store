package cn.hncu.store.pub.login.dao;

import cn.hncu.store.domain.User;

public interface LoginDAO {

	boolean save(User user);

	User login(User user);

	boolean del(User user);

	boolean update(User user);

}
