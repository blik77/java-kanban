import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Group> groups = new ArrayList<>();
    private static final List<Coach> coaches = new ArrayList<>();
    private static final Timetable timetable = new Timetable();

    public static void main(String[] args) {
        createTimetable(); // наполняем данными для работы
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    showTrainingsForDay();
                    break;
                case "2":
                    showTrainingsForDayAndTime();
                    break;
                case "3":
                    addTraining();
                    break;
                case "4":
                    showCountByCoaches();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Показать тренировки за день");
        System.out.println("2 — Показать тренировки за день и время");
        System.out.println("3 — Добавить тренировку");
        System.out.println("4 — Загруженность тренеров");
        System.out.println("0 — Завершить");
    }

    private static void showTrainingsForDay() {
        DayOfWeek day = inputDay();

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsDay = timetable.getTrainingSessionsForDay(day);
        if (trainingsDay.isEmpty()) {
            System.out.println("Тренировки в " + day + " отсутствуют");
        } else {
            System.out.println("Список тренировок на " + day + ":");
            trainingsDay.forEach((time, trainings) -> {
                System.out.println(time);
                trainings.forEach(training -> System.out.println(training));
            });
        }
    }

    private static void showTrainingsForDayAndTime() {
        DayOfWeek day = inputDay();
        TimeOfDay time = inputTime();

        List<TrainingSession> trainingsDayAndTime = timetable.getTrainingSessionsForDayAndTime(day, time);
        if (trainingsDayAndTime.isEmpty()) {
            System.out.println("Тренировки в " + day + " начинающиеся в " + time + " отсутствуют");
        } else {
            System.out.println("Тренировки на " + day + " начинающиеся в " + time + ":");
            trainingsDayAndTime.forEach(training -> System.out.println(training));
        }
    }

    private static void addTraining() {
        DayOfWeek day = inputDay();
        TimeOfDay time = inputTime();
        Group group = inputGroup();
        Coach coach = inputCoach();

        TrainingSession training = new TrainingSession(group, coach, day, time);

        timetable.addNewTrainingSession(training);

        System.out.println("Добавлена тренировка:\n" + day + " в " + time + " - " + training);
    }

    private static void showCountByCoaches() {
        Set<CounterOfTrainings> counterOfTrainings = timetable.getCountByCoaches();

        if (counterOfTrainings.isEmpty()) {
            System.out.println("Данные отсутствуют");
        } else {
            counterOfTrainings.forEach((counter) -> System.out.println(counter));
        }
    }

    private static DayOfWeek inputDay() {
        while (true) {
            System.out.println("Выберите день недели:");
            Integer dayNumber = 1;
            for (DayOfWeek day : DayOfWeek.values()) {
                System.out.println(dayNumber + " - " + day);
                dayNumber++;
            }
            try {
                int choice = scanner.nextInt() - 1;
                if (choice >= 0 && choice < DayOfWeek.values().length) {
                    return DayOfWeek.values()[choice];
                } else {
                    System.out.println("Ожидается число от 1 до " + DayOfWeek.values().length);
                }
            } catch (InputMismatchException e) {
                System.out.println("Ожидается число от 1 до " + DayOfWeek.values().length);
                scanner.next(); // для исключения бесконечного считывания
            }
        }
    }

    private static TimeOfDay inputTime() {
        int choiceHour = 0;
        int choiceMinute = 0;

        System.out.println("Введите время начала тренировок:");
        boolean running = true;
        while (running) {
            System.out.println("час (от 0 до 23):");
            try {
                choiceHour = scanner.nextInt();
                if (choiceHour < 0 || choiceHour > 23) {
                    System.out.println("Ожидается число от 0 до 23");
                } else {
                    running = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Ожидается число от 0 до 23");
                scanner.next(); // для исключения бесконечного считывания
            }
        }

        running = true;
        while (running) {
            System.out.println("минуты (от 0 до 59):");
            try {
                choiceMinute = scanner.nextInt();
                if (choiceMinute < 0 || choiceMinute > 59) {
                    System.out.println("Ожидается число от 0 до 59");
                } else {
                    running = false;

                }
            } catch (InputMismatchException e) {
                System.out.println("Ожидается число от 0 до 59");
                scanner.next(); // для исключения бесконечного считывания
            }
        }

        return new TimeOfDay(choiceHour, choiceMinute);
    }

    private static Group inputGroup() {
        while (true) {
            int countGroup = 1;
            System.out.println("Выберите группу:");
            for (Group group : groups) {
                System.out.println(countGroup + " - " + group);
                countGroup++;
            }
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= groups.size()) {
                    return groups.get(choice - 1);
                } else {
                    System.out.println("Ожидается число от 0 до " + groups.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Ожидается число от 0 до " + groups.size());
                scanner.next(); // для исключения бесконечного считывания
            }
        }
    }

    private static Coach inputCoach() {
        while (true) {
            int countCoach = 1;
            System.out.println("Выберите тренера:");
            for (Coach coach : coaches) {
                System.out.println(countCoach + " - " + coach);
                countCoach++;
            }
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= coaches.size()) {
                    return coaches.get(choice - 1);
                } else {
                    System.out.println("Ожидается число от 0 до " + coaches.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Ожидается число от 0 до " + coaches.size());
                scanner.next(); // для исключения бесконечного считывания
            }
        }
    }

    private static void createTimetable() {
        Coach coachShmotkov = new Coach("Шмотков", "В.", "В.");
        Coach coachSemenov = new Coach("Семёнов", "В.", "К.");
        Coach coachTihomirov = new Coach("Тихомиров", "А.", "Е.");
        Coach coachBelyaev = new Coach("Беляев", "Е.", "В.");
        Coach coachMorev = new Coach("Морев", "Е.", "В.");
        Coach coachUksusov = new Coach("Уксусов", "Н.", "А.");
        Coach coachSmirnov = new Coach("Смирнов", "Н.", "А.");
        Coach coachMironov = new Coach("Миронов", "Ю.", "Б.");
        Coach coachToprova = new Coach("Топрова", "А.", "С.");
        Coach coachStrashnaya = new Coach("Страшная", "А.", "С.");
        Coach coachVeselov = new Coach("Веселов", "С.", "А.");
        Coach coachVolkova = new Coach("Волкова", "Г.", "Ю.");

        coaches.addAll(List.of(coachShmotkov, coachSemenov, coachTihomirov, coachBelyaev, coachMorev,
                coachUksusov, coachSmirnov, coachMironov, coachToprova, coachStrashnaya,
                coachVeselov, coachVolkova));

        Group group1 = new Group("Группа 1", Age.ADULT, 90);
        Group group2 = new Group("Группа 2", Age.ADULT, 80);
        Group group18 = new Group("Группа 18", Age.ADULT, 70);
        Group group3 = new Group("Группа 3", Age.CHILD, 55);
        Group group4 = new Group("Группа 4", Age.ADULT, 55);
        Group group15 = new Group("Группа 15", Age.CHILD, 30);
        Group group11 = new Group("Группа 11", Age.ADULT, 90);
        Group group88 = new Group("Группа 88", Age.ADULT, 60);
        Group group8 = new Group("Группа 8", Age.ADULT, 80);
        Group group13 = new Group("Группа 13", Age.ADULT, 90);

        groups.addAll(List.of(group1, group2, group18, group3, group4, group15, group11, group88,
                group8, group13));

        TrainingSession monday1 = new TrainingSession(group1, coachShmotkov, DayOfWeek.MONDAY, new TimeOfDay(10, 9));
        TrainingSession monday2 = new TrainingSession(group2, coachShmotkov, DayOfWeek.MONDAY, new TimeOfDay(13, 29));
        TrainingSession monday3 = new TrainingSession(group18, coachSemenov, DayOfWeek.MONDAY, new TimeOfDay(14, 50));
        TrainingSession monday4 = new TrainingSession(group3, coachMironov, DayOfWeek.MONDAY, new TimeOfDay(14, 50));
        TrainingSession tuesday1 = new TrainingSession(group3, coachSemenov, DayOfWeek.TUESDAY, new TimeOfDay(12, 0));
        TrainingSession tuesday2 = new TrainingSession(group2, coachShmotkov, DayOfWeek.TUESDAY, new TimeOfDay(15, 0));
        TrainingSession wednesday1 = new TrainingSession(group1, coachTihomirov, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0));
        TrainingSession wednesday2 = new TrainingSession(group2, coachBelyaev, DayOfWeek.WEDNESDAY, new TimeOfDay(12, 0));
        TrainingSession wednesday3 = new TrainingSession(group4, coachMorev, DayOfWeek.WEDNESDAY, new TimeOfDay(14, 0));
        TrainingSession thursday1 = new TrainingSession(group15, coachUksusov, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession thursday2 = new TrainingSession(group15, coachSmirnov, DayOfWeek.THURSDAY, new TimeOfDay(15, 0));
        TrainingSession friday1 = new TrainingSession(group11, coachMironov, DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        TrainingSession friday2 = new TrainingSession(group88, coachToprova, DayOfWeek.FRIDAY, new TimeOfDay(12, 0));
        TrainingSession friday3 = new TrainingSession(group8, coachStrashnaya, DayOfWeek.FRIDAY, new TimeOfDay(14, 0));
        TrainingSession saturday1 = new TrainingSession(group13, coachVeselov, DayOfWeek.SATURDAY, new TimeOfDay(13, 0));
        TrainingSession saturday2 = new TrainingSession(group4, coachVolkova, DayOfWeek.SATURDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(monday1);
        timetable.addNewTrainingSession(monday2);
        timetable.addNewTrainingSession(monday3);
        timetable.addNewTrainingSession(monday4);
        timetable.addNewTrainingSession(tuesday1);
        timetable.addNewTrainingSession(tuesday2);
        timetable.addNewTrainingSession(wednesday1);
        timetable.addNewTrainingSession(wednesday2);
        timetable.addNewTrainingSession(wednesday3);
        timetable.addNewTrainingSession(thursday1);
        timetable.addNewTrainingSession(thursday2);
        timetable.addNewTrainingSession(friday1);
        timetable.addNewTrainingSession(friday2);
        timetable.addNewTrainingSession(friday3);
        timetable.addNewTrainingSession(saturday1);
        timetable.addNewTrainingSession(saturday2);
    }
}
