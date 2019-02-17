package FindNumber;

import java.util.Comparator;

class NumberOfAttemptsComparator implements Comparator<User>{
    @Override
    public int compare(User o1, User o2) {
        return (new Integer(o1.getNumberOfAttempts()).compareTo(new Integer(o2.getNumberOfAttempts())));
    }
}