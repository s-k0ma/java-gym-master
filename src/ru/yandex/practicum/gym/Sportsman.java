package ru.yandex.practicum.gym;

import java.util.Objects;

/**
 * Класс для работы с гостями клуба
 */
public class Sportsman {
    private String name;
    private String surname;
    private String middleName;
    private Age age;

    public Sportsman(String name, String surname, String middleName, Age age){
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.age = age;
    }

    public String getName() { return name; }

    public String getSurname() { return surname; }

    public String getMiddleName() { return middleName; }

    public Age getAge() { return age; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Sportsman sportsman = (Sportsman) o;
        return Objects.equals(name, sportsman.name)
                && Objects.equals(surname, sportsman.surname)
                && Objects.equals(middleName, sportsman.middleName)
                && Objects.equals(age, sportsman.age);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, surname, middleName, age);
    }
}
