import scrapy
import pandas as pd

class HeadphonesSpider(scrapy.Spider):

    name = "patient_info"

    mylist = []

    def start_requests(self):
        #urls = [
        #'https://patient.info/forums/index-A',
        #]

        #for url in urls:
        urlPrefix = 'https://patient.info/forums/index-'
        alphabet = 'A'
        i = 0
        print("##########################################")
        #print(self.df.shape)
        #exit()
        while i<2:
            url = urlPrefix + chr(ord(alphabet) + i)
            #print(url)
            yield scrapy.Request(url=url, callback=self.parse)
            i += 1
        print(self.mylist)
        print("------------------------------------------")

    def parse(self, response):
        '''
        page = response.url.split("/")[-2]
        filename = 'quotes-%s.html' % page
        with open(filename, 'wb') as f:
            f.write(response.body)
        '''

        print("##########################################")
        aToZgroups = response.xpath("//div[@class='disc-forums disc-a-z']")
        symtomRowsLinks = aToZgroups.xpath(".//tr[@class='row-0']//a/@href")
        #print(aToZgroups)
        #print(symtomRows)

        for symptomLink in symtomRowsLinks:
            print(symptomLink)
            symtomCompleteLink = response.urljoin(symptomLink.get())
            print(response.url)
            print(symtomCompleteLink)
            #exit()
            yield scrapy.Request(url=symtomCompleteLink, callback=self.parsePostsList)
            exit()
            #symptomName = symptomRow.xpath(".//a/text()").get()
            #print(symptomName)
            #f.write(symptomName + "\n")

        #print(symtomRows)
        #print(len(symtomRows))
        #f.write(symptoms_table.extract() + "\n")


        print("##########################################")

    def parsePostsList(self, response):
        print("##########################################")
        posts = response.xpath("//li[@class='disc-smr cardb']")
        for postHeading in posts:
            postLinkAnchorTag = postHeading.xpath(".//h3[@class='post__title']//a/@href")
            postLink = response.urljoin(postLinkAnchorTag.get())
            print(postLink)
            yield scrapy.Request(url=postLink, callback=self.parsePost)
            exit()

    def parsePost(self, response):
        print("##########################################")
        postHeading = response.xpath("//h1[@class='u-h1 post__title']/text()").get()
        postContentParas = response.xpath("//div[@class='post__content']/p/text()")
        postContent = ''
        for para in postContentParas:
            postContent += para.get() + ' '
        print(postHeading)
        print(postContent)
        self.mylist.append([postHeading, postContent])
        print("##########################################")

    def closed(self, reason):
        print("##########################################")
        print("##########################################")
        print(self.mylist)
        df = pd.DataFrame(self.mylist, columns=['heading','passage'], index=None)
        print(df.head())
        df.to_csv('patientInfo.csv')
        print("##########################################")
        print("##########################################")