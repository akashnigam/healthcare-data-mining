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
        writer = csv.writer(self.outputFile)
        writer.writerow(row)

    def __del__(self):
        self.outputFile.close()

    def process_item(self, item, spider):
        if item['contentType'] == 'disease':
            self.linkDisease[item['postLink']] = item['disease']
        else:
            disease = self.linkDisease[item['postLink']]
            row = [disease, item['postLink'], item['postHeading'], item['postContent']]
            writer = csv.writer(self.outputFile)
            writer.writerow(row)
        return item
