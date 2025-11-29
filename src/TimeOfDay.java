import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {
    private final int hours; //часы (от 0 до 23)
    private final int minutes; //минуты (от 0 до 59)

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public int compareTo(TimeOfDay o) {
        return (hours*60+minutes) - (o.hours*60+o.minutes);
    }

    @Override
    public String toString() {
        String minutesStr = "";
        if(minutes < 10) {
            minutesStr = "0" + minutes;
        } else {
            minutesStr = minutes + "";
        }
        return hours + ":" + minutesStr;
    }
}