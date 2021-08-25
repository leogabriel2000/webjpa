package com.rijai.webjpa.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.rijai.webjpa.model.UserRecord;
import com.rijai.webjpa.service.UserService;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public List<UserRecord> getAllUser()
    {
        return userService.getAllUsers();
    }

    @RequestMapping(value="/users/{id}")
    public UserRecord getUser(@PathVariable int id)
    {
         return userService.getUser(id);
    }

    @RequestMapping(value="/add-user", method=RequestMethod.POST)
    public void addUser(@RequestBody UserRecord userRecord)
    {
        userService.addUser(userRecord);
    }

    @RequestMapping(value="/update-user", method=RequestMethod.PUT)
    public void updateUser(@RequestBody UserRecord userRecord)
    {
        userService.updateUser(userRecord);
    }
    @RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id)
    {
        userService.deleteUser(id);
    }
}
