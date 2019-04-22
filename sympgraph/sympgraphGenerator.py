import csv
import numpy as np
import json

def getPostWiseSymptoms():
    file = '../MetaMapAnnotator/resources/combinedData.csv'
    allpostSymptoms = {}
    allSymptoms = {}
    symtomIndex = 1
    postIndex = 1
    with open(file) as f:
        reader = csv.reader(f)
        headers = next(reader, None)
        for row in reader:
            postId = row[0]
            symptomId = row[3]
            symptomName = row[4]
            if symptomId:
                if postId not in allpostSymptoms:
                    allpostSymptoms[postId] = {"index": postIndex, "symptomList": []}
                    postIndex += 1
                allpostSymptoms[postId]["symptomList"].append(symptomId)
                if symptomId not in allSymptoms:
                    allSymptoms[symptomId] = {"index": symtomIndex, "name": symptomName}
                    symtomIndex += 1
    return allpostSymptoms, allSymptoms

def postWiseSymptomMatrix(allpostSymptoms, allSymptoms):
    postSymptomsMat = []
    for postId, postInfo in allpostSymptoms.items():
        symptoms = [0 for x in range(len(allSymptoms))]
        for symptomId in postInfo["symptomList"]:
            symptomMatIndex = allSymptoms[symptomId]["index"]
            symptoms[symptomMatIndex-1] += 1
        postSymptomsMat.append(symptoms)
        print(postId, postInfo)
    return postSymptomsMat

def createSympGraph(postSymptomsMat, allSymptoms):
    #print(len(allSymptoms))
    postSymptomsMat = np.array(postSymptomsMat)
    symptomGraphShape = (len(allSymptoms),len(allSymptoms))
    symptomGraph = np.zeros(symptomGraphShape)
    #print("symptomGraphShape:", symptomGraph.shape)

    for postSymptoms in postSymptomsMat:
        postSymptoms = np.matrix(postSymptoms)
        postSymptomGraph = np.matmul(postSymptoms.transpose(), postSymptoms)
        symptomGraph += postSymptomGraph
        #print("shape:", postSymptoms.transpose().shape)
        #print("shape:", postSymptoms.shape)
        #print("shape:", postSymptomGraph.shape)
        #print("symptomGraphShape:", symptomGraph.shape)
        #print(postSymptomGraph)
        #exit()

    #print(postSymptomsMat.shape)
    #print(symptomGraph.shape)
    print(symptomGraph.shape)
    #print(json.dumps(allSymptoms))
    #print(json.dumps(postSymptomsMat))
    return symptomGraph

if __name__ == '__main__':
    allpostSymptoms, allSymptoms = getPostWiseSymptoms()
    postSymptomsMat = postWiseSymptomMatrix(allpostSymptoms, allSymptoms)
    symptomGraph = createSympGraph(postSymptomsMat, allSymptoms)

