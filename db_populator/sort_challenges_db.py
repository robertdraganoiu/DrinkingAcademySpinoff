import csv

# Load the CSV file
input_file = 'challenges.csv'  # Replace with your file path
output_file = 'challenges.csv'  # Replace with your desired output path

# Read the CSV data
with open(input_file, mode='r', newline='', encoding='utf-8') as file:
    reader = csv.reader(file)
    header = next(reader)  # Read the header
    data = list(reader)  # Read the rest of the data

# Sort the data by difficulty (numeric) and then by challenge_type (alphabetical)
sorted_data = sorted(data, key=lambda x: (int(x[1]), x[0]))

# Write the sorted data to a new CSV file
with open(output_file, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(header)  # Write the header first
    writer.writerows(sorted_data)  # Write the sorted data

print(f'Sorted file has been saved to {output_file}')
