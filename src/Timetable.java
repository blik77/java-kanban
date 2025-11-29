import java.util.*;

public class Timetable {
    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if(timetable.containsKey(trainingSession.getDayOfWeek())) { // проверка на ключ "день недели"
            if(timetable.get(trainingSession.getDayOfWeek()).
                    containsKey(trainingSession.getTimeOfDay())) { // проверка на ключ "время дня"
                timetable.get(trainingSession.getDayOfWeek()).
                        get(trainingSession.getTimeOfDay()).
                        add(trainingSession);
            } else {
                ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
                trainingSessions.add(trainingSession);
                timetable.get(trainingSession.getDayOfWeek()).
                        put(trainingSession.getTimeOfDay(), trainingSessions);
            }
        } else {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayTrainingSessions = new TreeMap<>();
            ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
            trainingSessions.add(trainingSession);
            dayTrainingSessions.put(trainingSession.getTimeOfDay(), trainingSessions);
            timetable.put(trainingSession.getDayOfWeek(), dayTrainingSessions);
        }
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> countByCoach = new HashMap<>();
        LinkedHashMap<Coach, Integer> countByCoachSort = new LinkedHashMap<>();
        Comparator<Integer> countComparator = new Comparator<>() {
            @Override
            public int compare(Integer count1, Integer count2) {
                return count2 - count1;
            }
        };
        TreeMap<Integer, ArrayList<Coach>> counterOfTrainings = new TreeMap<>(countComparator);
        // считаем сколько тренировок у тренера
        timetable.forEach((day, dayTrainings) -> {
            dayTrainings.forEach((time, timeTrainings) -> {
                timeTrainings.forEach(training -> {
                    if(countByCoach.containsKey(training.getCoach())) {
                        countByCoach.put(training.getCoach(), countByCoach.get(training.getCoach()) + 1);
                    } else {
                        countByCoach.put(training.getCoach(), 1);
                    }
                });
            });
        });
        // сортируем по количеству тренировок
        countByCoach.forEach((coach, count) -> {
            if(counterOfTrainings.containsKey(count)) {
                counterOfTrainings.get(count).add(coach);
            } else {
                counterOfTrainings.put(count, new ArrayList<>(List.of(coach)));
            }
        });
        // формируем ответ
        counterOfTrainings.forEach((count, coaches) -> {
            coaches.forEach(coach -> {
                countByCoachSort.put(coach, count);
            });
        });

        return countByCoachSort;
    }
}
