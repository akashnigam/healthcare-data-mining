import csv
import numpy as np
import matplotlib.pyplot as plt
import networkx as nx

def getPostWiseSymptoms(numPosts):
    file = '../MetaMapAnnotator/resources/combinedData.csv'
    allpostSymptoms = {}
    allSymptoms = {}
    symtomsReverseMap = {}
    symtomIndex = 0
    postIndex = 1
    with open(file) as f:
        reader = csv.reader(f)
        headers = next(reader, None)
        data = [r for r in reader]
        #data = data[0:numPosts]
        for row in data:
            postId = row[0]
            symptomId = row[3]
            symptomName = row[4]
            if symptomId:
                if postId not in allpostSymptoms:
                    allpostSymptoms[postId] = {"index": postIndex, "symptomList": [], "symtomNames": []}
                    postIndex += 1
                allpostSymptoms[postId]["symptomList"].append(symptomId)
                allpostSymptoms[postId]["symtomNames"].append(symptomName)
                if symptomId not in allSymptoms:
                    allSymptoms[symptomId] = {"index": symtomIndex, "name": symptomName}
                    symtomsReverseMap[symtomIndex] = symptomId
                    symtomIndex += 1
                if numPosts != None and postIndex> numPosts:
                    break
    return allpostSymptoms, allSymptoms, symtomsReverseMap

def postWiseSymptomMatrix(allpostSymptoms, allSymptoms):
    postSymptomsMat = []
    for postId, postInfo in allpostSymptoms.items():
        symptoms = [0 for x in range(len(allSymptoms))]
        for symptomId in postInfo["symptomList"]:
            symptomMatIndex = allSymptoms[symptomId]["index"]
            symptoms[symptomMatIndex] += 1
        postSymptomsMat.append(symptoms)
        print(postId, postInfo)
    return postSymptomsMat

def createSympGraph(postSymptomsMat, allSymptoms):
    postSymptomsMat = np.array(postSymptomsMat)
    symptomGraphShape = (len(allSymptoms),len(allSymptoms))
    symptomGraph = np.zeros(symptomGraphShape)

    for postSymptoms in postSymptomsMat:
        postSymptoms = np.matrix(postSymptoms)
        postSymptomGraph = np.matmul(postSymptoms.transpose(), postSymptoms)
        symptomGraph += postSymptomGraph

    print(symptomGraph.shape)
    return symptomGraph

def getSymgraphEdges(symptomGraph, symtomsReverseMap):
    symptomGraphList = symptomGraph.tolist()
    edgeList = []
    for row, symtoms in enumerate(symptomGraphList):
        for col in range(row + 1 ,len(symptomGraphList)):
            source = symtomsReverseMap[row]
            dest = symtomsReverseMap[col]
            weight = symptomGraphList[row][col]
            if weight >= 1:
                edgeList.append([source, dest, weight])
    return edgeList

def plotGraph(edgeList, allSymptoms):
    G = nx.Graph()
    for edge in edgeList:
        G.add_edge(allSymptoms[edge[0]]["name"],allSymptoms[edge[1]]["name"])
    print("number of nodes:", G.number_of_nodes())
    print("number of edges:", G.number_of_edges())
    options = {
        'node_color': 'black',
        'node_size': 5,
        'width': 1,
        'with_labels': True
    }
    nx.draw_circular(G, **options)
    plt.show()

def writeSympGraph(edgeList):
    with open('sympgraph.csv', 'w') as writeFile:
        writer = csv.writer(writeFile)
        writer.writerow(["Source", "Destination", "Weight"])
        writer.writerows(edgeList)
        writeFile.close()

if __name__ == '__main__':
    allpostSymptoms, allSymptoms, symtomsReverseMap = getPostWiseSymptoms(None)
    postSymptomsMat = postWiseSymptomMatrix(allpostSymptoms, allSymptoms)
    symptomGraph = createSympGraph(postSymptomsMat, allSymptoms)
    edgeList = getSymgraphEdges(symptomGraph, symtomsReverseMap)
    writeSympGraph(edgeList)
    print("Successully generated Sympgraph and saved in CSV")

    allpostSymptoms, allSymptoms, symtomsReverseMap = getPostWiseSymptoms(5)
    postSymptomsMat = postWiseSymptomMatrix(allpostSymptoms, allSymptoms)
    symptomGraph = createSympGraph(postSymptomsMat, allSymptoms)
    edgeList = getSymgraphEdges(symptomGraph, symtomsReverseMap)
    plotGraph(edgeList, allSymptoms)
    print("Successully generated Sympgraph with 5 posts and plotted sympgraph")

