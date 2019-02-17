package FindNumber;

import java.util.*;
import java.lang.Float;
import java.io.*;

public class User implements Serializable, Comparable<User> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nameUser;
    private float timeAttempts;
    private int numberOfAttempts;

    public User(String nameUser, int numberOfAttempts, float timeAttempts) {
        this.nameUser = nameUser;
        this.numberOfAttempts = numberOfAttempts;
        this.timeAttempts = timeAttempts;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
    public void setTimeAttempts(float timeAttempts) {
        this.timeAttempts = timeAttempts;
    }
    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }
    public String getNameUser() {
        return nameUser;
    }
    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }
    public float getTimeAttempts() {
        return timeAttempts;
    }


   @Override
    public String toString() {
        String strProba = "";
        if (getNumberOfAttempts() == 0 || getNumberOfAttempts() >= 5 && getNumberOfAttempts() <= 21) {
            strProba = " prób";
        }
        else if (getNumberOfAttempts() == 1) {
            strProba = " próba";
        }
        else if (getNumberOfAttempts() > 1 && getNumberOfAttempts() < 5) {
            strProba = " próby";
        }
        String strTemp = getNameUser()+ " - " + Float.toString(getTimeAttempts()) + " s - " + Integer.toString(getNumberOfAttempts()) + strProba;
        return strTemp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(nameUser, user.nameUser) &&
                Objects.equals(numberOfAttempts, user.numberOfAttempts) &&
                Objects.equals(timeAttempts, user.timeAttempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameUser, numberOfAttempts, timeAttempts);
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
