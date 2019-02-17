package FindNumber;

import java.util.Comparator;

class TimeAttemptsComparator implements Comparator<User>{
    
	@Override
    public int compare(User o1, User o2) {
        return (new Float(o1.getTimeAttempts()).compareTo(new Float(o2.getTimeAttempts())));

    }
}