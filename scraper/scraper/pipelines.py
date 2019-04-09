import json
import csv
from datetime import datetime

# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


class ScraperPipeline(object):

    def __init__(self):
        self.linkDisease = {}
        csvOutputPath = "data/" + datetime.today().strftime("%Y-%m-%d_%H-%M-%S") + ".csv"
        self.outputFile = open(csvOutputPath, "a")
        row = ['disease', 'postLink', 'postHeading', 'postContent']
        print(row)
        writer = csv.writer(self.outputFile)
        writer.writerow(row)

    def __del__(self):
        self.outputFile.close()

    def process_item(self, item, spider):
        print('--------------------------------------')
        print('--------------------------------------')
        print('--------Inside Pipeline---------------')
        print(item)
        print('--------------------------------------')
        if item['contentType'] == 'disease':
            self.linkDisease[item['postLink']] = item['disease']
            print(json.dumps(self.linkDisease))
        else:
            disease = self.linkDisease[item['postLink']]
            row = [disease, item['postLink'], item['postHeading'], item['postContent']]
            print(row)
            writer = csv.writer(self.outputFile)
            writer.writerow(row)
        print('--------------------------------------')
        print('--------------------------------------')
        #print("##########################################")
        #print(item)
        #print("##########################################")
        return item

class MysqlPipeline(object):
    #collection_name = 'scrapy_items'

    '''
    def __init__(self, mongo_uri, mongo_db):
        self.mongo_uri = mongo_uri
        self.mongo_db = mongo_db

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            mongo_uri=crawler.settings.get('MONGO_URI'),
            mongo_db=crawler.settings.get('MONGO_DATABASE', 'items')
        )


    def open_spider(self, spider):
        self.client = pymongo.MongoClient(self.mongo_uri)
        self.db = self.client[self.mongo_db]

    def close_spider(self, spider):
        self.client.close()

    '''

    def process_item(self, item, spider):
        print("##########################################")
        print(item)
        print("##########################################")
        return item