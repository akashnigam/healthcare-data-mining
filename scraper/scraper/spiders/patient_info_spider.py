import scrapy
import pandas as pd

class PatientInfoSpider(scrapy.Spider):

    name = "patient_info"

    mylist = []

    def start_requests(self):
        urlPrefix = 'https://patient.info/forums/index-'
        alphabet = 'A'
        i = 0
        #print("##########################################")
        while i<1:
            url = urlPrefix + chr(ord(alphabet) + i)
            yield scrapy.Request(url=url, callback=self.parse)
            i += 1
        #print(self.mylist)
        #print("##########################################")

    def parse(self, response):
        #print("##########################################")
        aToZgroups = response.xpath("//div[@class='disc-forums disc-a-z']")
        symtomRowsLinks = aToZgroups.xpath(".//tr[@class='row-0']//a/@href")
        symtomRowsLinks = symtomRowsLinks[:20]
        symtomCompleteLink = {}
        for symptomLink in symtomRowsLinks:
            print(symptomLink)
            #symtomCompleteLink['hjh'] = response.urljoin(symptomLink.get())
            symtomCompleteLink = response.urljoin(symptomLink.get())
            #print(symtomCompleteLink)
            #break
            print(response.url)
            print(symtomCompleteLink)
            #yield symtomCompleteLink
            yield scrapy.Request(url=symtomCompleteLink, callback=self.parsePostsList)
            #exit()

        #print("##########################################")

    def parsePostsList(self, response):
        #print("##########################################")
        disease = response.xpath("//h1[@class='articleHeader__title masthead__title']/text()").get().strip()
        posts = response.xpath("//li[@class='disc-smr cardb']")
        posts = posts[0:20]
        for postHeading in posts:
            postLinkAnchorTag = postHeading.xpath(".//h3[@class='post__title']//a/@href")
            postLink = response.urljoin(postLinkAnchorTag.get())
            print(postLink)
            mydict = {
                'contentType': 'disease',
                'disease': disease,
                'postLink': postLink
            }
            yield mydict
            yield scrapy.Request(url=postLink, callback=self.parsePost)
            #exit()

    def parsePost(self, response):
        #print("##########################################")
        postLink = response.url
        postHeading = response.xpath("//h1[@class='u-h1 post__title']/text()").get()
        postContentParas = response.xpath("//div[@class='post__content']/p/text()")
        postContent = ''
        for para in postContentParas:
            postContent += para.get() + ' '
        self.mylist.append([postHeading, postContent])
        #item = {'postcontent':[postHeading, postContent]}
        item = {
            'contentType': 'userPost',
            'postLink': postLink,
            'postHeading': postHeading,
            'postContent': postContent
        }
        yield item
        #yield item
        #print("##########################################")

    def closed(self, reason):
        #print("##########################################")
        #print("##########################################")
        df = pd.DataFrame(self.mylist, columns=['heading','passage'], index=None)
        print(df.shape)
        print(df.head())
        df.to_csv('patientInfo.csv')
        #print("##########################################")
        #print("##########################################")