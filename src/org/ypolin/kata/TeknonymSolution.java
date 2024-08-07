package org.ypolin.kata;

import java.time.LocalDateTime;

/**
 * https://www.codewars.com/kata/65781071e16df9dcbded1520
 *
 * Teknonymy
 *
 * Description:
 * In some societies, you receive a name around your birth and people use it to refer to you while you don't have any children.
 *
 * Once you have at least one child, people give you a teknonym. A teknonym is a way to refer to someone according to some of its descendants, like the 'mother of Joe', the 'great-grandfather of Jane', etc.
 *
 * In this kata, you will receive some kind of family tree and your task will be to give the correct teknonym to the members of the tree, according to the rules specified in the next section.
 *
 * Rules
 * If X doesn't have any direct descendant : they don't have a teknonym.
 * A direct descendant of X is any member, but X, of the subtree rooted at X.
 * If X has some direct descendants : the teknonym is built according to the sex of X and the name of the elder among its direct descendants of the most distant generation from X.
 * The 'generation distance' between X and one of its descendant, say Y, is the number of levels between X and Y.
 * So you have to :
 * Find the most distant generation
 * Find the elder of that generation
 * Build the teknonym of X according to the two previous informations.
 * There won't be any ambiguity to determine who is the elder between two direct descendants of someone.
 * The possible teknonyms are (all lower case):
 * 'father of Y', 'grandfather of Y', 'great-grandfather of Y', 'great-great-grandfather of Y', and so on ;
 * 'mother of Y', 'grandmother of Y', 'great-grandmother of Y', 'great-great-grandmother of Y', and so on ;
 *
 * Input
 * A structure representing a kind of family tree. Each node of the tree is a structure representing a person and containing:
 *
 * their name
 * their date of birth
 * their sex, either 'm' for male and 'f' for female
 * their teknonym, a string
 * a sequence of children. Each child is a person, the sequence can be empty and not necessarily sorted.
 * In Java, the structure is a class Person. Each node is a Person with the following fields:
 *
 * public class Person{
 *     public Person parent;
 *     public final String name;
 *     public final Character sex;
 *     public final Person[] children;
 *     public final LocalDateTime dateOfBirth;
 *     public String teknonym = ""; // that's the field you should mutate
 * }
 * Output
 * Nothing. You should mutate the input in this kata. But you have to mutate only the teknonym field, and only if it's relevant.
 */
public class TeknonymSolution {
    private static class Person {
        public Person parent;
        public final String name;
        public final Character sex;
        public final Person[] children;
        public final LocalDateTime dateOfBirth;
        public String teknonym = ""; // that's the field you should mutate

        public Person(String name, Character sex, LocalDateTime dateOfBirth, Person[] children) {
            this.name = name;
            this.sex = sex;
            this.children = children;
            this.dateOfBirth = dateOfBirth;
        }

        public Person(Person parent, String name, Character sex, LocalDateTime dateOfBirth, Person[] children) {
            this.parent = parent;
            this.name = name;
            this.sex = sex;
            this.children = children;
            this.dateOfBirth = dateOfBirth;
        }
    }

    private static class PersonExt {
        private int generation;
        private Person person;

        public PersonExt(int generation, Person person) {
            this.generation = generation;
            this.person = person;
        }
    }

    static void teknonymize(Person person) {
        if (person.children.length != 0) {
            findElder(new PersonExt(1, person));
        }
    }

    private static PersonExt findElder(PersonExt p) {
        PersonExt elder = new PersonExt(p.generation + 1, p.person.children[0]);
        for (Person ch : p.person.children) {
            PersonExt descendant = new PersonExt(p.generation + 1, ch);
            if (ch.children.length != 0) {
                descendant = findElder(descendant);
            }
            if (descendant.generation > elder.generation ||
                    descendant.generation == elder.generation && descendant.person.dateOfBirth.isBefore(elder.person.dateOfBirth)) {
                elder = descendant;
            }
        }
        String teknonym = String.format("%s of %s", p.person.sex == 'f' ? "mother" : "father", elder.person.name);
        int genDif = elder.generation - p.generation;
        if (genDif > 1) {
            teknonym = "grand" + teknonym;
            while (genDif > 2) {
                teknonym = "great-" + teknonym;
                genDif--;
            }
        }
        p.person.teknonym = teknonym;
        return elder;
    }

}
