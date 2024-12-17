import sqlite3
import random
from datetime import datetime, timedelta
import string

# Predefined lists of realistic book titles and authors
titles = [
    "The Great Gatsby", "To Kill a Mockingbird", "1984", "Pride and Prejudice",
    "The Catcher in the Rye", "The Hobbit", "Fahrenheit 451", "Brave New World",
    "Moby Dick", "War and Peace", "The Odyssey", "Crime and Punishment",
    "The Picture of Dorian Gray", "Jane Eyre", "The Lord of the Rings",
    "The Alchemist", "The Kite Runner", "Life of Pi", "The Da Vinci Code",
    "The Book Thief", "The Road", "The Fault in Our Stars", "The Handmaid's Tale",
    "The Hunger Games", "The Chronicles of Narnia", "Animal Farm", "The Shining",
    "The Glass Castle", "Beloved", "The Secret Garden", "The Bell Jar"
]

authors = [
    "F. Scott Fitzgerald", "Harper Lee", "George Orwell", "Jane Austen",
    "J.D. Salinger", "J.R.R. Tolkien", "Ray Bradbury", "Aldous Huxley",
    "Herman Melville", "Leo Tolstoy", "Homer", "Fyodor Dostoevsky",
    "Oscar Wilde", "Charlotte Brontë", "J.K. Rowling", "Paulo Coelho",
    "Khaled Hosseini", "Yann Martel", "Dan Brown", "Markus Zusak",
    "Margaret Atwood", "Suzanne Collins", "Stephen King", "Virginia Woolf",
    "Agatha Christie", "John Green", "Gabriel García Márquez", "Toni Morrison",
    "C.S. Lewis", "Mark Twain"
]

# Function to generate a random ISBN
def generate_isbn():
    return ''.join(random.choices(string.digits, k=13))

# Connect to the SQLite database (or create a new one if it doesn't exist)
conn = sqlite3.connect('/Users/amir/Desktop/javalin_project/src/main/resources/db')
cursor = conn.cursor()

# Create the table if it doesn't exist
cursor.execute('''
CREATE TABLE IF NOT EXISTS Books (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    publication_date TEXT,
    isbn TEXT UNIQUE,
    genre TEXT,
    copies_count INTEGER DEFAULT 1,
    status TEXT DEFAULT 'available'
);
''')

# Function to generate random publication date
def random_date(start, end):
    return start + timedelta(days=random.randint(0, (end - start).days))

# Function to generate fake book data
def generate_fake_book_data(num_books):
    genres = ['Fiction', 'Non-Fiction', 'Science Fiction', 'Mystery', 'Romance',
              'Fantasy', 'Biography', 'History', 'Thriller']
    statuses = ['available', 'unavailable', 'checked out', 'reserved', 'under maintenance']  # List of statuses

    start_date = datetime(1900, 1, 1)
    end_date = datetime.now()

    for _ in range(num_books):
        title = random.choice(titles)  # Randomly select a title from the list
        author = random.choice(authors)  # Randomly select an author from the list
        publication_date = random_date(start_date, end_date).strftime('%Y-%m-%d')  # Generate a random publication date
        isbn = generate_isbn()  # Generate a unique ISBN
        genre = random.choice(genres)  # Random genre from the list
        copies_count = random.randint(1, 10)  # Random number of copies from 1 to 10
        status = random.choice(statuses)  # Random status from the list

        # Insert data into the table
        try:
            cursor.execute('''
            INSERT INTO Books (title, author, publication_date, isbn, genre, copies_count, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            ''', (title, author, publication_date, isbn, genre, copies_count, status))
        except sqlite3.IntegrityError:
            # Handle duplicate ISBN if it occurs
            print(f"Duplicate ISBN generated: {isbn}. Skipping this record.")

# Generate 2500 fake books
generate_fake_book_data(100)

# Commit changes and close the connection
conn.commit()
conn.close()

print("Fake data has been successfully generated and saved in the Books table.")