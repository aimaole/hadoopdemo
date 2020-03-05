package jav;

import java.util.Comparator;

public class ComparatorTest {
    //https://blog.csdn.net/sanchan/article/details/70792845
    public static void main(String[] args) {
        Comparator<Human> byName = new Comparator<Human>() {
            @Override
            public int compare(Human developer, Human compareDeveloper) {
                return developer.getName().compareTo(compareDeveloper.getName());
            }
        };
        Comparator<Human> byNameLambda =   (Human developer, Human compareDeveloper)->developer.getName().compareTo(compareDeveloper.getName());
        Comparator<Human> byNameLambdaSimple = Comparator.comparing(Human::getName);


    }

}
class Human {
    private String name;
    private int age;

    public Human() {
        super();
    }

    public Human(final String name, final int age) {
        super();

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
