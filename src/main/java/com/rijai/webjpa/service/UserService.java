package com.rijai.webjpa.service;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rijai.webjpa.model.UserRecord;
import com.rijai.webjpa.repository.UserRepository;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<UserRecord> getAllUsers()
    {
        List<UserRecord>userRecords = new ArrayList<>();
        userRepository.findAll().forEach(userRecords::add);
        return userRecords;
    }
    public void addUser(UserRecord userRecord)
    {
        userRepository.save(userRecord);
    }
    public void updateUser(UserRecord userRecord)
    {
        userRepository.save(userRecord);
    }
    public UserRecord getUser(int id)
    {
        Optional<UserRecord> userRecord = userRepository.findById(id);
        if(userRecord.isPresent()) {
            return userRecord.get();
        }
        else
            return null;
    }
    public void deleteUser(int id)
    {
        Optional<UserRecord> userRecord = userRepository.findById(id);
        if(userRecord.isPresent()) {
            userRepository.delete(userRecord.get());
        }
    }
}
