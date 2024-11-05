package utilities;

import enums.StudentLevel;
import model.Users.Student;
import model.Users.Teachers;
import model.Users.Users;

import java.util.Comparator;

public class UserComparator implements Comparator<Users> {
    @Override
    public int compare(Users o1, Users o2) {

        if (o1 instanceof Teachers && !(o2 instanceof Teachers)) {
            return -1;
        }
        if (o2 instanceof Teachers && !(o1 instanceof Teachers)) {
            return 1;
        }

        if (o1 instanceof Student s1 && o2 instanceof Student s2) {

            // Senior students have higher priority than junior students

            return s2.getLevel().compareTo(s1.getLevel());
//            if (s1.getLevel() == StudentLevel.SENIOR && s2.getLevel() == StudentLevel.JUNIOR) {
//                return -1;
//            }
//            if (s1.getLevel() == StudentLevel.JUNIOR && s2.getLevel() == StudentLevel.SENIOR) {
//                return 1;
//            }
        }

        // Otherwise, treat them as equal
        return 0;
    }


}

