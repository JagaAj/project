package com.trio.Elitecar.service;



import com.trio.Elitecar.model.User;
import com.trio.Elitecar.model.UserPrinciple;
import com.trio.Elitecar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//implementation of UserDetailsService
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByEmail(username); // ✅ CORRECT


        //if the username is not present in db
        if(user == null){
            System.out.println("User 404");
            throw new UsernameNotFoundException("User 404");
        }

        //we have to return userdetails not a username so we have to implement the UserDetails
        //userPrinciple implements userDetails.
        return new UserPrinciple(user);
    }
}
