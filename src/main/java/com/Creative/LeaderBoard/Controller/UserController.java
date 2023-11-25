package com.Creative.LeaderBoard.Controller;

import com.Creative.LeaderBoard.Entity.Users;
import com.Creative.LeaderBoard.Service.UserService;
import com.Creative.LeaderBoard.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/top10")
    public ResponseEntity<List<UserResponse>> gettop10(){
        List<UserResponse> result = userService.getTopUser(10);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserResponse>> getUserById(@PathVariable Long userId) {
        List<UserResponse> userResponses = userService.getTopUserandUserRank(userId);
        if(userResponses != null) return  new ResponseEntity<>(userResponses,HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/saveuser")
    public ResponseEntity<List<UserResponse>> saveUser(@RequestParam long id, @RequestParam String username, @RequestParam int score){
        Users user = userService.SaveUser(id,username,score);
        List<UserResponse> userResponses = userService.getTopUserandUserRank(id);
        return new ResponseEntity<>(userResponses,HttpStatus.OK);

    }




}
