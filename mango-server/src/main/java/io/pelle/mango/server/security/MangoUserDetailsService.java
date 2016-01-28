package io.pelle.mango.server.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.pelle.mango.client.base.vo.query.SelectQuery;
import io.pelle.mango.db.dao.BaseEntityDAO;

public class MangoUserDetailsService implements UserDetailsService {

	@Autowired
	private BaseEntityDAO baseEntityDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		SelectQuery<MangoUser> query = SelectQuery.selectFrom(MangoUser.class).where(MangoUser.USERNAME.eq(username));

		List<MangoUser> users = baseEntityDAO.filter(query);

		if (users.size() == 1) {

		}
		throw new UsernameNotFoundException(String.format("user '%s' not found", username));
	}

}
