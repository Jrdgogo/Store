package cn.hncu.store.pub.login.dao;

import cn.hncu.container.annotation.DAO;
import cn.hncu.container.jdbc.IConnection;
import cn.hncu.store.domain.User;
import cn.hncu.store.domain.mapping.UserMapping;
import cn.hncu.store.util.Encrypt;

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
		return getMapping(UserMapping.class).getSingleUser(sql,user.getName(),Encrypt.encrypt(user.getPassword()));
	}
	@Override
	public boolean del(User user) {
		String sql="WHERE id=?";
		return getMapping(UserMapping.class).del(sql,user.getId());
	}
	@Override
	public boolean update(User user) {
		String sql="SET password=? WHERE id=?";
		return getMapping(UserMapping.class).del(sql,Encrypt.encrypt(user.getPassword()),user.getId());
	}

}
