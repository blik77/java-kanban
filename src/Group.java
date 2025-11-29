public class Group {
    private final String title; //название группы
    private final Age age; //тип (взрослая или детская)
    private final int duration; //длительность (в минутах)

    public Group(String title, Age age, int duration) {
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Группа \"" + title + "\", " + age + " [" + duration + " мин.]";
    }
}