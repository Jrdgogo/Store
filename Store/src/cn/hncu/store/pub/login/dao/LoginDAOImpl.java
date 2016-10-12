package cn.hncu.store.pub.login.dao;

import cn.hncu.container.annotation.DAO;
import cn.hncu.container.jdbc.IConnection;
import cn.hncu.store.domain.User;
import cn.hncu.store.domain.mapping.UserMapping;

@DAO(value="LoginDAO")
public class LoginDAOImpl extends IConnection implements LoginDAO{
	@Override
	public boolean save(User user){
		String sql="VALUES(?,?,?,?,?)";
		return getMapping(UserMapping.class).register(sql,user.getId(),user.getName(),user.getPassword(),user.getPhone(),user.getEmail());
	}
	@Override
	public User login(User user){
		String sql="WHERE name=? AND password=?";
		return getMapping(UserMapping.class).getSingleUser(sql,user.getName(),user.getPassword());
	}

}
