package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size());
        Assertions.assertEquals(singleTrainingSession, monday.get(0));

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesday.isEmpty());
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

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, monday.size());
        Assertions.assertEquals(mondayChildTrainingSession, monday.get(0));

        List<TrainingSession> thursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursday.size());
        Assertions.assertEquals(thursdayChildTrainingSession, thursday.get(0));   // 13:00
        Assertions.assertEquals(thursdayAdultTrainingSession, thursday.get(1));  // 20:00

        List<TrainingSession> tuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesday.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> at13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, at13.size());
        Assertions.assertEquals(singleTrainingSession, at13.get(0));

        List<TrainingSession> at14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(at14.isEmpty());
    }

    @Test
    void addNewTrainingSession_shouldThrowOnNull() {
        Timetable timetable = new Timetable();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> timetable.addNewTrainingSession(null));
    }

    @Test
    void getTrainingSessionsForDay_shouldThrowOnNull() {
        Timetable timetable = new Timetable();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> timetable.getTrainingSessionsForDay(null));
    }

    @Test
    void getTrainingSessionsForDayAndTime_shouldThrowOnNull() {
        Timetable timetable = new Timetable();
        TimeOfDay time = new TimeOfDay(13, 0);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> timetable.getTrainingSessionsForDayAndTime(null, time));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, null));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> timetable.getTrainingSessionsForDayAndTime(null, null));
    }

    @Test
    void multipleSessionsInSameSlot_shouldAllBeReturned() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Иван", "Иванович");

        TrainingSession s1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession s2 = new TrainingSession(group, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(s1);
        timetable.addNewTrainingSession(s2);

        List<TrainingSession> at13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(2, at13.size());
        Assertions.assertTrue(at13.contains(s1));
        Assertions.assertTrue(at13.contains(s2));

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(2, monday.size());
    }

    @Test
    void getTrainingSessionsForDay_shouldBeSortedByTimeAscending() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession evening = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession morning = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(9, 0));
        TrainingSession noon = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        // добавляем в произвольном порядке
        timetable.addNewTrainingSession(evening);
        timetable.addNewTrainingSession(morning);
        timetable.addNewTrainingSession(noon);

        List<TrainingSession> monday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(3, monday.size());
        Assertions.assertEquals(morning, monday.get(0));   // 09:00
        Assertions.assertEquals(noon, monday.get(1));      // 13:00
        Assertions.assertEquals(evening, monday.get(2));   // 20:00
    }

    @Test
    void sessionsInSameSlot_shouldPreserveInsertionOrder() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TimeOfDay time = new TimeOfDay(13, 0);

        TrainingSession first = new TrainingSession(group, coach, DayOfWeek.MONDAY, time);
        TrainingSession second = new TrainingSession(group, coach, DayOfWeek.MONDAY, time);

        timetable.addNewTrainingSession(first);
        timetable.addNewTrainingSession(second);

        List<TrainingSession> list = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, time);

        Assertions.assertEquals(first, list.get(0));
        Assertions.assertEquals(second, list.get(1));
    }

    @Test
    void getCountByCoaches_shouldReturnEmptyMapWhenNoSessions() {
        Timetable timetable = new Timetable();

        LinkedHashMap<Coach, Integer> counts = timetable.getCountByCoaches();
        Assertions.assertTrue(counts.isEmpty());
    }

    @Test
    void getCountByCoaches_shouldCountSessionsPerCoach() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0)));

        LinkedHashMap<Coach, Integer> counts = timetable.getCountByCoaches();

        Assertions.assertEquals(1, counts.size());
        Assertions.assertEquals(3, counts.get(coach));
    }

    @Test
    void getCountByCoaches_shouldBeSortedByCountDescending() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика", Age.ADULT, 60);
        Coach ivanov = new Coach("Иванов", "Иван", "Иванович");
        Coach petrov = new Coach("Петров", "Пётр", "Петрович");
        Coach sidorov = new Coach("Сидоров", "Сидор", "Сидорович");

        // Иванов — 2, Петров — 1, Сидоров — 3
        addSession(timetable, group, ivanov, DayOfWeek.MONDAY, 9, 0);
        addSession(timetable, group, ivanov, DayOfWeek.MONDAY, 10, 0);

        addSession(timetable, group, petrov, DayOfWeek.TUESDAY, 9, 0);

        addSession(timetable, group, sidorov, DayOfWeek.WEDNESDAY, 9, 0);
        addSession(timetable, group, sidorov, DayOfWeek.WEDNESDAY, 10, 0);
        addSession(timetable, group, sidorov, DayOfWeek.WEDNESDAY, 11, 0);

        LinkedHashMap<Coach, Integer> counts = timetable.getCountByCoaches();

        List<Coach> ordered = new ArrayList<>(counts.keySet());
        Assertions.assertEquals(sidorov, ordered.get(0));
        Assertions.assertEquals(3, counts.get(sidorov));
        Assertions.assertEquals(ivanov, ordered.get(1));
        Assertions.assertEquals(2, counts.get(ivanov));
        Assertions.assertEquals(petrov, ordered.get(2));
        Assertions.assertEquals(1, counts.get(petrov));
    }

    private static void addSession(Timetable timetable, Group group, Coach coach,
                                   DayOfWeek day, int hours, int minutes) {
        timetable.addNewTrainingSession(
                new TrainingSession(group, coach, day, new TimeOfDay(hours, minutes)));
    }
}
