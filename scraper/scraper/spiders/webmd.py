import scrapy


class WebMDSpider(scrapy.Spider):
    name = "webmd"

    def start_requests(self):
        start_urls = [
            "https://messageboards.webmd.com/",
        ]

        for url in start_urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        topics = response.xpath('//*[@id="fragment-2071092687"]/div[1]/div/div/div[3]/div[1]//div[@class="link"]/a/@href').getall()
        topic_link = response.urljoin(topics[0])

        yield scrapy.Request(url=topic_link, callback=self.parse_topics)

    def parse_topics(self, response):
        disease = response.xpath('//*[@id="fragment-36"]/div[1]/h1/a/text()').get()
        content = response.css("ul.content-list.content")
        content_items = content.css("li.content-item.forum-thread")

        for content_item in content_items:
            thread_detail = content_item.css("div.thread-detail")
            post_link = thread_detail.css("h3 a::attr(href)").get()
            post_link = response.urljoin(post_link)
            print(disease, post_link)
            mydict = {
                'contentType': 'disease',
                'disease': disease,
                'postLink': post_link
            }
            yield mydict
            yield scrapy.Request(url=post_link, callback=self.parse_topic_post)

        nex = response.css("a.next::attr(href)").get()
        if nex:
            next_page_link = response.urljoin(nex)
            print("next page ", next_page_link)
            print("************************************next page********************************")
            yield scrapy.Request(url=next_page_link, callback=self.parse_topics, dont_filter=True)

        else:
            print("********last link!!!**************")

    def parse_topic_post(self, response):
        post_link = response.url
        post_heading = response.css("a.internal-link.view-post.unread::text").get()
        # post_content = " ".join(response.css('div.thread-body::text').getall())
        post_details = response.css("ul.content-list.content.margin-bottom.thread-list.webmd-mb-thrd")
        post_content = " ".join(post_details.css("div.thread-body::text").getall())

        post_tags = " , ".join(response.css("a.tag::text").getall())
        # print("tags", post_tags)

        item = {
            'contentType': 'userPost',
            'postLink': post_link,
            'postHeading': post_heading,
            'postContent': post_content.strip(),
            'postTags': post_tags
        }
        yield item

