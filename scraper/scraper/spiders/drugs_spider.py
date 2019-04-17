import scrapy

class PatientInfoSpider(scrapy.Spider):

    name = "drugs"

    def start_requests(self):
        url = 'https://www.drugs.com/answers/conditions/'
        yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        conditionGroups = response.xpath("//ul[@class='column-span column-span-3']/li/a/@href")
        #conditionGroups = conditionGroups[:1]
        print("#########   1  #############")
        print("############################")
        print(conditionGroups)
        for condition in conditionGroups:
            conditionLink = response.urljoin(condition.get()) + "questions/"
            print(conditionLink)
            yield scrapy.Request(url=conditionLink, callback=self.parseSupportGroup)
        print("############################")
        print("############################")

    def parseSupportGroup(self, response):
        print("#########   2  #############")
        print("############################")
        disease = response.xpath("//div[@class='groupHead']/h1/text()").get()
        disease = disease.replace("Questions", "")
        disease = disease.strip()
        posts = response.xpath("//div[@class='questionList visited']/div[@class='listContent']/h2/a/@href")
        #posts = posts[:1]
        print("Count:", len(posts))
        print("disease:"+disease+":")
        for postHeading in posts:
            postLink = response.urljoin(postHeading.get())
            print(postLink)
            mydict = {
                'contentType': 'disease',
                'disease': disease,
                'postLink': postLink
            }
            yield mydict
            yield scrapy.Request(url=postLink, callback=self.parsePost)
        nextLink = response.xpath("//li[@class='ddc-paging-item-next ddc-paging-item-span2']/a/@href")
        print("nextLink:", nextLink)
        if len(nextLink) > 0:
            nextCompleteLink = response.urljoin(nextLink.get())
            yield scrapy.Request(url=nextCompleteLink, callback=self.parseSupportGroup)
            print("nextCompleteLink:", nextCompleteLink)

        print("############################")
        print("############################")

    def parsePost(self, response):
        print("#########   3  #############")
        print("############################")
        postLink = response.url
        contentDiv = response.xpath("//div[@class='postWrap clearAfter showInvisible']")
        postHeading = contentDiv.xpath(".//h1/text()").get()
        postContentParas = contentDiv.xpath(".//div[@class='postContent']/p/text()")
        postContent = ''
        for para in postContentParas:
            postContent += para.get() + ' '
        item = {
            'contentType': 'userPost',
            'postLink': postLink,
            'postHeading': postHeading,
            'postContent': postContent
        }
        yield item
        print(item)
        print("############################")
        print("############################")
