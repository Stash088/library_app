package org.example.book;

import java.util.Arrays;
import java.util.List;

public class BookRepository {
    public static List<Book> getBooks() {
        return Arrays.asList(
                new Book(1, "The Avengers Saga", "Steve Rogers"),
                new Book(2, "Iron Man: A Genius's Journey", "Tony Stark"),
                new Book(3, "Higher, Further, Faster", "Carol Danvers"),
                new Book(4, "The Art of Espionage", "Natasha Romanoff"),
                new Book(5, "Anger Management for Big Green Guys", "Bruce Banner"),
                new Book(6, "Asgardian Tales", "Thor Odinson"),
                new Book(7, "Archery: Hitting the Mark", "Clint Barton"),
                new Book(8, "Web-Slinging 101", "Peter Parker"),
                new Book(9, "Wakanda Forever", "T'Challa"),
                new Book(10, "Mastering the Mystic Arts", "Stephen Strange"),
                new Book(11, "Guardians of the Galaxy: A Space Odyssey", "Peter Quill"),
                new Book(12, "Ant-Man: Small Hero, Big Adventures", "Scott Lang"),
                new Book(13, "The Wasp: Stinging Success", "Hope van Dyne"),
                new Book(14, "Vision: Understanding Humanity", "Vision"),
                new Book(15, "Scarlet Witch: Chaos Magic Unveiled", "Wanda Maximoff"),
                new Book(16, "Falcon: Soaring to New Heights", "Sam Wilson"),
                new Book(17, "Winter Soldier: Redemption Story", "Bucky Barnes"),
                new Book(18, "Black Panther: The Heart-Shaped Herb", "Shuri"),
                new Book(19, "Spider-Man: Friendly Neighborhood Hero", "Miles Morales"),
                new Book(20, "Daredevil: Justice is Blind", "Matt Murdock"),
                new Book(21, "Jessica Jones: Private Eye Diaries", "Jessica Jones"),
                new Book(22, "Luke Cage: Unbreakable", "Luke Cage"),
                new Book(23, "Iron Fist: The Living Weapon", "Danny Rand"),
                new Book(24, "Deadpool's Guide to Annoying Everyone", "Wade Wilson"),
                new Book(25, "Fantastic Four: Family Matters", "Reed Richards"),
                new Book(26, "Invisible Woman: Hidden Strengths", "Sue Storm"),
                new Book(27, "The Human Torch: Playing with Fire", "Johnny Storm"),
                new Book(28, "The Thing: Rocky Road to Heroism", "Ben Grimm"),
                new Book(29, "X-Men: Evolution of Mutantkind", "Charles Xavier"),
                new Book(30, "Magneto: Master of Magnetism", "Erik Lehnsherr"),
                new Book(31, "Wolverine: The Art of Healing", "Logan"),
                new Book(32, "Storm: Weathering Any Challenge", "Ororo Munroe"),
                new Book(33, "Cyclops: Leading with Vision", "Scott Summers"),
                new Book(34, "Jean Grey: Phoenix Rising", "Jean Grey"),
                new Book(35, "Gambit: Charging into Action", "Remy LeBeau"),
                new Book(36, "Rogue: The Power of Touch", "Anna Marie"),
                new Book(37, "Beast: Beauty of the Mind", "Hank McCoy"),
                new Book(38, "Iceman: Chilling Adventures", "Bobby Drake"),
                new Book(39, "Nightcrawler: Teleporting Through Life", "Kurt Wagner"),
                new Book(40, "Colossus: Heart of Steel", "Piotr Rasputin")
        );

    }
}
