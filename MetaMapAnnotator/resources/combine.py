import os
import csv

files = [f for f in os.listdir('.') if os.path.isfile(f) and ("Drugs" in f or "PatientsInfo" in f or "Webmd" in f)]

prevFilesTotalPosts = 0
allRows = []
headers = []
for file in files:
    print(file)
    with open(file) as f:
        reader = csv.reader(f)
        headers = next(reader, None)
        data = [r for r in reader]
        lastPostNumber = 0
        for index, row in enumerate(data):
            for columnId, columnVal in enumerate(row):
                row[columnId] = columnVal.replace(",", "|")
            postNumber = int(row[0])
            row[0] = prevFilesTotalPosts + postNumber

            allRows.append(row)
            lastPostNumber = postNumber
        prevFilesTotalPosts += lastPostNumber

with open('combinedData.csv', 'w') as writeFile:
    writer = csv.writer(writeFile)
    writer.writerow(headers)
    writer.writerows(allRows)
    writeFile.close()

print("Combined CSV file successfully created")