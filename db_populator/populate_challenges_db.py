import sqlite3
import csv

def create_database_from_csv(db_name, csv_file):
    # Connect to (or create) the database
    conn = sqlite3.connect(db_name)
    cursor = conn.cursor()

    # Drop the existing tables if they exist
    cursor.execute('DROP TABLE IF EXISTS settings;')
    cursor.execute('DROP TABLE IF EXISTS game_cards;')

    # Create the settings table
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS settings(
            musicEnabled INTEGER NOT NULL
        );
    ''')

    # Create the game_cards table with an additional difficulty column
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS game_cards(
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            challenge_type TEXT NOT NULL,
            difficulty INTEGER NOT NULL,
            content TEXT NOT NULL
        );
    ''')

    # Insert the default value for musicEnabled (always true)
    cursor.execute('INSERT INTO settings (musicEnabled) VALUES (1);')

    # Read and insert game elements from the CSV file
    with open(csv_file, 'r') as file:
        reader = csv.DictReader(file)
        for row in reader:
            cursor.execute('''
                INSERT INTO game_cards (challenge_type, difficulty, content)
                VALUES (?, ?, ?)
            ''', (row['challenge_type'], row['difficulty'], row['content']))

    # Commit changes and close the connection
    conn.commit()
    conn.close()

if __name__ == "__main__":
    # Example usage:
    create_database_from_csv('game_challenges.db', 'challenges.csv')
