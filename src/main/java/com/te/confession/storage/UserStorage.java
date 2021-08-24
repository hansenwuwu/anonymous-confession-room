package com.te.confession.storage;

import java.util.Map;
import java.util.HashMap;

import com.te.confession.model.UserModel;

public class UserStorage {

    private static UserStorage instance;
    private Map<String, UserModel> users;

    private UserStorage() {
        users = new HashMap<>();
    }

    public static synchronized UserStorage getInstance() {
        if (instance == null) {
            instance = new UserStorage();
        }
        return instance;
    }

    public Map<String, UserModel> getUsers() {
        return users;
    }

    public void setUser(String sessionId) throws Exception {
        if (users.containsKey(sessionId)) {
            throw new Exception("User aready exists with sessionId: " + sessionId);
        }
        users.put(sessionId, new UserModel());
    }

    public void removeUser(String sessionId) {
        if (users.containsKey(sessionId)) {
            users.remove(sessionId);
        }
    }

}
