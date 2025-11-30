import java.util.*;

public class Timetable {
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        timetable = new HashMap<>();
        timetable.put(DayOfWeek.MONDAY, new TreeMap<>());
        timetable.put(DayOfWeek.TUESDAY, new TreeMap<>());
        timetable.put(DayOfWeek.WEDNESDAY, new TreeMap<>());
        timetable.put(DayOfWeek.THURSDAY, new TreeMap<>());
        timetable.put(DayOfWeek.FRIDAY, new TreeMap<>());
        timetable.put(DayOfWeek.SATURDAY, new TreeMap<>());
        timetable.put(DayOfWeek.SUNDAY, new TreeMap<>());
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        Map<TimeOfDay, List<TrainingSession>> dayTrainingSessions = timetable.get(trainingSession.getDayOfWeek());

        if (dayTrainingSessions.containsKey(trainingSession.getTimeOfDay())) {
            dayTrainingSessions.get(trainingSession.getTimeOfDay()).add(trainingSession);
        } else {
            dayTrainingSessions.put(trainingSession.getTimeOfDay(), new ArrayList<>(List.of(trainingSession)));
        }
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).getOrDefault(timeOfDay, new ArrayList<>());
    }

    public TreeSet<CounterOfTrainings> getCountByCoaches() {
        Coach coachShmotkov = new Coach("Шмотков", "В.", "В.");
        Coach coachSemenov = new Coach("Семёнов", "В.", "К.");

        CounterOfTrainings x1 = new CounterOfTrainings(coachShmotkov, 1);
        CounterOfTrainings x2 = new CounterOfTrainings(coachSemenov, 1);

        System.out.println(Objects.equals(x1, x2));
        System.out.println(x1.equals(x2));


        // считаем сколько тренировок у тренера
        Map<Coach, Integer> countByCoach = new HashMap<>();
        timetable.forEach((day, dayTrainings) -> {
            dayTrainings.forEach((time, timeTrainings) -> {
                timeTrainings.forEach(training -> {
                    countByCoach.compute(training.getCoach(), (key, value) -> value == null ? 1 : value + 1);
                });
            });
        });
        // сортируем по количеству тренировок
        TreeSet<CounterOfTrainings> counterOfTrainings = new TreeSet<>();
        countByCoach.forEach((coach, count) -> {
            CounterOfTrainings newRec = new CounterOfTrainings(coach, count);
            System.out.println("Добавляем " + newRec + ": " + counterOfTrainings.add(newRec));
        });

        return counterOfTrainings;
    }
}
