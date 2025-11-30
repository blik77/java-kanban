import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private Integer count;

    public CounterOfTrainings(Coach coach, Integer count) {
        this.coach = coach;
        this.count = count;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        int cmp = o.count.compareTo(this.count);
        if (cmp != 0) return cmp;
        cmp = this.coach.getSurname().compareTo(o.coach.getSurname());
        if (cmp != 0) return cmp;
        cmp = this.coach.getName().compareTo(o.coach.getName());
        if (cmp != 0) return cmp;
        return this.coach.getMiddleName().compareTo(o.coach.getMiddleName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(coach);
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return coach + ": " + count + " тренировок";
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }
}
