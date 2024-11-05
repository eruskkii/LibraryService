package collections;

import model.Users.Users;

import java.util.HashMap;
import java.util.Map;

public class UserList {

        private Map<Integer, Users> userMap = new HashMap<>();

        // Add user to the collection
        public void addUser(Users user) {
            if (user != null) {
                userMap.put(user.getUserId(), user);
            }
        }

        // Get the next available User ID
        public int getNextUserId() {
            int highestId = 0;
            for (int id : userMap.keySet()) {
                if (id > highestId) {
                    highestId = id;  // Update highestId if the current key is greater
                }
            }
            return highestId + 1;  // Return the next available ID
        }

        // Other utility methods if needed (find user, remove user, etc.)
}


