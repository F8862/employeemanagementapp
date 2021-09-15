package com.user.controller;

import com.user.model.User;
import com.user.model.UserRequest;
import com.user.model.UserResponse;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import com.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value ="/token",method = RequestMethod.POST)
    public ResponseEntity<?> createJwtToken(@RequestBody UserRequest userRequest)throws Exception{
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect userName or Password",e);
        }
        final UserDetails userDetails=userService.loadUserByUsername(userRequest.getUsername());
        final String token=jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new UserResponse(token));
        /*var user=userService.checkUser(userRequest.getUsername(),userRequest.getPassword());
         String token=null;
        if (user!=null){
             token=jwtUtil.generateToken(user);
        }else {
            throw new UsernameNotFoundException("Bad credentials!!!");
        }
        return ResponseEntity.ok(new UserResponse(token));*/
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user ) {
        User user1=userRepository.save(user);
        return user1;

    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
       return userRepository.findAll();
    }
    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable Long id){
       return userRepository.findById(id);

    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }
    @DeleteMapping("/users")
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateStudent(@PathVariable ("id") Long id , @RequestBody User user){
        Optional<User> userData=userRepository.findById(id);
        if (userData.isPresent()){
            User user1=userData.get();
            user1.setUserName(user.getUserName());
            user1.setPassword(user.getPassword());
            return new ResponseEntity<>(userRepository.save(user1), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
