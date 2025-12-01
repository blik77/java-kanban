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
        TreeSet<CounterOfTrainings> counterOfTrainings = new TreeSet<>();
        timetable.forEach((day, dayTrainings) -> {
            dayTrainings.forEach((time, timeTrainings) -> {
                timeTrainings.forEach(training -> {
                    CounterOfTrainings newRec = new CounterOfTrainings(training.getCoach(), 0);
                    counterOfTrainings.forEach(record -> {
                        if (record.getCoach().equals(training.getCoach())) {
                            newRec.setCount(record.getCount());
                        }
                    });
                    if (newRec.getCount() > 0) {
                        counterOfTrainings.remove(newRec);
                    }
                    newRec.setCount(newRec.getCount() + 1);
                    counterOfTrainings.add(newRec);
                });
            });
        });

        return counterOfTrainings;
    }
}
