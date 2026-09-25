package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GroupTest {

    @Test
    void addParticipant_shouldIgnoreNull() {
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> group.addParticipant(null));
    }

    @Test
    void addParticipant_shouldThrowOnAgeMismatch() {
        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Sportsman adult = new Sportsman("Иван", "Иванов", "Иванович", Age.ADULT);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> childGroup.addParticipant(adult));

        Assertions.assertTrue(childGroup.getAllparticipants().isEmpty(),
                "После исключения список участников должен остаться пустым");
    }

    @Test
    void addParticipant_shouldThrowOnAgeMismatchWithNonEmptyList() {
        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Sportsman adult = new Sportsman("Иван", "Иванов", "Иванович", Age.ADULT);
        Sportsman child = new Sportsman("Иван", "Иванов", "Иванович", Age.CHILD);

        childGroup.addParticipant(child);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> childGroup.addParticipant(adult));

        Assertions.assertEquals(1, childGroup.getAllparticipants().size());
    }
}
