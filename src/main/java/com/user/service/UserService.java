package com.user.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
   /* @Autowired
    private com.test.entity.User user;
    @Autowired
    private UserDAO userDAO;
*/
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new User("ansari", "A1234", new ArrayList<>());
    }

   /* public com.test.entity.User checkUser(String userName, String password) {
        return userDAO.findByUserNameAndPassword(userName, password);
    }*/
}
