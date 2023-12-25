package com.example.park.myrestfulservice.dao;

import com.example.park.myrestfulservice.exception.UserNotFoundException;
import com.example.park.myrestfulservice.model.User;
import org.springframework.stereotype.Component;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "tkdfla", new Date(), "test1", "111111-1111111"));
        users.add(new User(2, "test1", new Date(), "test2", "222222-2222222"));
        users.add(new User(3, "abcd", new Date(), "test3", "333333-3333333"));
    }

    public List<User> findAll(){
        return users;
    }


    public User save(User user){
        if(user.getId() == null){
            user.setId(++usersCount);
        }

        if(user.getJoinData() == null){
            user.setJoinData(new Date());
        }
        users.add(user);
        return user;
    }

    public User findOne(int id){
        return users.stream().filter( users -> users.getId() == id).findFirst().orElse(null);
    }

    public User delete(int id){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        throw  new UserNotFoundException(id);
    }


}