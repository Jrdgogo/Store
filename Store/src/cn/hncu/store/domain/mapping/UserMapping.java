package cn.hncu.store.domain.mapping;

import java.util.List;

import cn.hncu.store.domain.User;

public interface UserMapping {
	
	List<User> getAllUser();

	User getSingleUser(String sql, Object ...args);

	boolean register(String sql, Object ...args);

}
