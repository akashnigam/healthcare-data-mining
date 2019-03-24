import scrapy

class HeadphonesSpider(scrapy.Spider):

    name = "patients_like_me"

    def start_requests(self):
        urls = [
        'https://www.patientslikeme.com/conditions/breast-ca',
        ]

        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        page = response.url.split("/")[-2]
        filename = 'quotes-%s.html' % page
        with open(filename, 'wb') as f:
            f.write(response.body)
        symptomsTable = response.xpath("//div[@data-widget='condition_symptoms_table']")
        print("##########################################")
        #print(symptomsTable)

        with open('symptoms.txt', 'w') as f:
            #for
            symtomRows = symptomsTable.xpath(".//span[@itemprop='name']/text()")
            for symptom in symtomRows:
                symptomName = symptom.get()
                print(symptomName)
                f.write(symptomName + "\n")
            #print(symtomRows)
            #print(len(symtomRows))
            #f.write(symptoms_table.extract() + "\n")

        print("##########################################")

