package ru.yandex.practicum.gym;

import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private final Map<Coach, Integer> trainingCounterPerCoach = new HashMap<>();

    /**
     *
     * @param trainingSession
     * Метод добавляет тренировку в расписание
     */
    public void addNewTrainingSession(TrainingSession trainingSession) {
        if(trainingSession == null)
            throw new IllegalArgumentException("Тренировка не может быть пустой");

        trainingCounterPerCoach.merge(trainingSession.getCoach(), 1, Integer::sum);

        timetable
                .computeIfAbsent(trainingSession.getDayOfWeek(), day -> new TreeMap<>())
                .computeIfAbsent(trainingSession.getTimeOfDay(), time -> new ArrayList<>())
                .add(trainingSession);
    }

    /**
     *
     * @param dayOfWeek - день недели для которого ищем тренировки
     * @return - список тренировок
     */
    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if(dayOfWeek == null)
            throw new IllegalArgumentException("День недели не может быть пустым!");

        Map<TimeOfDay, List<TrainingSession>> dailyTrainings = timetable.get(dayOfWeek);
        if(dailyTrainings == null)
            return Collections.emptyList();

        return dailyTrainings.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param dayOfWeek - день недели для которого ищем тренировки
     * @param timeOfDay - временной слот в который ищем тренировки
     * @return - список  тренировок
     */
    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        if(dayOfWeek == null || timeOfDay == null)
            throw new IllegalArgumentException("День недели и время не могут быть пустыми!");

        Map<TimeOfDay, List<TrainingSession>> dailyTrainings = timetable.get(dayOfWeek);
        if(dailyTrainings == null)
            return Collections.emptyList();

        List<TrainingSession> timedTrainings = dailyTrainings.get(timeOfDay);
        return timedTrainings == null ? Collections.emptyList() : Collections.unmodifiableList(timedTrainings);
    }

    /**
     * Метод возвращает тренеров и количество их тренировок в неделю в порядке убывания
     * @return
     */
    public LinkedHashMap<Coach, Integer> getCountByCoaches(){
        return trainingCounterPerCoach.entrySet().stream()
                .sorted((ent1, ent2) -> ent2.getValue() - ent1.getValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }
}
