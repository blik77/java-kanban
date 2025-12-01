import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        Assertions.assertEquals("13:00", timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).firstKey().toString());
        Assertions.assertEquals("20:00", timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).lastKey().toString());
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group1 = new Group("Группа 1", Age.ADULT, 90);
        Group group2 = new Group("Группа 2", Age.ADULT, 80);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachShmotkov = new Coach("Шмотков", "В.", "В.");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession ts1 = new TrainingSession(group1, coachShmotkov,
                DayOfWeek.MONDAY, new TimeOfDay(16, 0));
        TrainingSession ts2 = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(16, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(ts1);
        timetable.addNewTrainingSession(ts2);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertTrue(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)).isEmpty());
        //Проверить, что за понедельник в 16:00 начинается 2 тренировки
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(16, 0)).size());
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Группа 1", Age.ADULT, 90);
        Group group2 = new Group("Группа 2", Age.ADULT, 80);
        Group group18 = new Group("Группа 18", Age.ADULT, 70);
        Group group3 = new Group("Группа 3", Age.CHILD, 55);

        Coach coachShmotkov = new Coach("Шмотков", "В.", "В.");
        Coach coachSemenov = new Coach("Семёнов", "В.", "К.");
        Coach coachTihomirov = new Coach("Тихомиров", "А.", "Е.");
        Coach coachVolkova = new Coach("Волкова", "Г.", "Ю.");

        TrainingSession trainingSession1 = new TrainingSession(group1, coachShmotkov,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(group2, coachShmotkov,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        TrainingSession trainingSession3 = new TrainingSession(group18, coachSemenov,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        TrainingSession trainingSession4 = new TrainingSession(group3, coachSemenov,
                DayOfWeek.MONDAY, new TimeOfDay(18, 0));
        TrainingSession trainingSession5 = new TrainingSession(group3, coachShmotkov,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 30));
        TrainingSession trainingSession6 = new TrainingSession(group3, coachTihomirov,
                DayOfWeek.SATURDAY, new TimeOfDay(13, 33));
        TrainingSession trainingSession7 = new TrainingSession(group3, coachShmotkov,
                DayOfWeek.SUNDAY, new TimeOfDay(13, 7));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);
        timetable.addNewTrainingSession(trainingSession7);

        //Проверить, что у Шмотков 4 тренировки в неделю
        Assertions.assertEquals(4, timetable.getCountByCoaches().ceiling(new CounterOfTrainings(coachShmotkov, 4)).getCount());
        //Проверить, что у Волкова не тренировала в эту неделю
        Assertions.assertFalse(timetable.getCountByCoaches().contains(new CounterOfTrainings(coachVolkova, 0)));
        //Проверить, что количество активных тренеров
        Assertions.assertEquals(3, timetable.getCountByCoaches().size());
    }
}
