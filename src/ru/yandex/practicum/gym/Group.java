package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    //название группы
    private String title;
    //тип (взрослая или детская)
    private Age age;
    //длительность (в минутах)
    private int duration;

    private final List<Sportsman> participants = new ArrayList<>();

    public Group(String title, Age age, int duration) {
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    public void addParticipant(Sportsman sportsman) {
        if (sportsman == null)
            throw new IllegalArgumentException("Участник не может быть null!");
        if (age != sportsman.getAge())
            throw new IllegalArgumentException("Возраст участника не соответствует возрастной группе!");

        participants.add(sportsman);
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

    public List<Sportsman> getAllparticipants() {
        return Collections.unmodifiableList(participants);
    }
}
