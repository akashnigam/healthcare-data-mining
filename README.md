# Mining Healthcare Forums 👨‍⚕️
The aim of this project is to build an information extraction system that can turn unstructured medical healthcare data from user posts of multiple sources (in [WebMD.com](https://www.webmd.com/), [Drugs.com](https://www.drugs.com/), [Patient.info](https://patient.info/)) into structured information and build a parametric search interface for a category (a disease/symptom/drug). Using the search interface, users will be able to get the different attributes of input disease, possible diseases for given symptoms, or the usage of the input drug, etc. Also, the search output data attributes will be ranked based on their occurrence frequency on data sources, to help users in differentiating the more common ones to the less frequent ones. The attributes of a disease include symptoms, the category of disease, treatment.


> :warning: For the install, excute instructions, screenshots and module specific functions look at the **README.md** files under each module.
> This is a general **README** file with higher level details of the project.

### The Problem

● Unstructured Health-related experience data

● Experiences spread across Multiple Sources

● No Disease - Symptom search tools for data mined from user posts


### Assumptions

The majority of the user posts

```
➔ Won’t contain negative contextual information
```
```
➔ Contain a single disease and it’s related symptoms per post
```

### Solution

#### Unstructured Data ➔ Structured Data

```
“I saw an orthopedist because
of pain and swelling in both
knees 2 years ago. Arthritis
Org said I need total knee
replacements. I am 75 and do
not want to undergo surgery so
I curtailed hiking which had
been causing pain, I lost some
weight. They don't hurt any
worse since starting this
exercise.”
```
```
Disease :
Arthritis
```
```
Symptoms :
Pain NOS Adverse Event,
Swelling, Weight decreased,
```
```
Treatment :
Knee Replacement Arthroplasty,
Exercise
```
### Solution
Following insights can be derived from the structured data collected from user posts

###### Top symptoms of a Disease

```
Disease: Malaria
980 people reported Malaria.
80% reported Fever
70% reported Headache
```
###### Top diseases for a symptom

```
Symptom: Sore Throat
580 people reported Sore Throat.
70% had Epiglottitis
64% had Influenza
```


###### Top treatment for a disease

```
Disease : Influenza
1193 people reported influenza
93 % took Rapivab
80 % took Relenza
```

### Web Scraping

**Purpose -** to extract _relevant_ data from the
websites.

**How to extract data?**

1. Using xpath
2. Using CSS selector

**Healthcare websites from where data is extracted**
* WebMD.com
* Patient.info
* Dugs.com

**Python’s scrapy framework will be used for web scraping**


### UMLS : Unified Medical Language System

##### 3 Knowledge Sources
```
Metathesaurus

1 million+
biomedical
**concepts** from over
100 sources
```

```
Semantic Network

135 broad categories
and 54 relationships
between categories
```

```
SPECIALIST Lexicon & Tools

lexical information and
programs for language
processing
```


### MetaMap

### Annotating and extracting medical concepts from Text

###### ➔ Developed at National Library of Medicine (NLM)

###### ➔ Highly configurable

###### ➔ Uses NLP and computational-linguistic techniques

###### ➔ Java Web APIs and Online Interactive Interface available


### MetaMap - How it works?


### MetaMap - Input / Output

**_Text to be processed_**

```
“I saw an orthopedist
because of pain and swelling
in both knees 2 years ago.
Arthritis Org said I need
total knee replacements. I
am 75 and do not want to
undergo surgery so I
curtailed hiking which had
been causing pain, I lost
some weight. They don't
hurt any worse since starting
this exercise.”
```

**Annotated Text with UMLS concepts and Semantics**

```
Score/ Term/ (UMLS Concept Preferred Name)/ [Semantic Type]


581 Pain NOS (Pain NOS Adverse Event) [Finding]
748 SWELLING (Swelling) [Finding]
861 knees (Knee) [Body Part, Organ, or Organ Component]
884 total knee replacements (Knee Replacement Arthroplasty
(procedure)) [Therapeutic or Preventive Procedure]
799 lost weight (Weight decreased) [Finding]
770 ARTHRITIS (Arthritis) [Disease or Syndrome]
861 Exercise (Exercise Pain Management) [Therapeutic or Preventive
Procedure]
```

### Ontology Creation

```
● Will create Disease, Symptom and Treatment ontology
```
```
● The output of metamap will be inserted into the ontology database
```
```
● Objects and relationships will be stored using tables in relational DB
```
```
● Relationships will consist of disease related with their symptom, treatment, anatomy
```
```
● Weight of each symptom, treatment, for a particular disease will be kept to get frequent itemsets
```

### SympGraph

● A graph with symptoms as nodes and co-occurrence
relations between symptoms as edges.

● Generated using symptoms from different
posts/comments from different patients

● Can be used for symptom expansion


### References

```
● Parikshit Sondhi, Jimeng Sun, Hanghang Tong, ChengXiang Zhai: SympGraph: a framework for
mining clinical notes through symptom relation graphs. KDD 2012: 1167-1175
● Xu H, Stenner SP, Doan S, Johnson KB, Waitman LR, et al. (2010) MedEx: a medication information
extraction system for clinical narratives. J Am Med Inform Assoc 17: 19–24
● Unified Medical Language System UMLS - https://www.nlm.nih.gov/research/umls/
● MetaMap Annotator - https://metamap.nlm.nih.gov/
● Scrapy - https://scrapy.org/ - A framework for extracting Data
● https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2243666/
● https://www.ncbi.nlm.nih.gov/pmc/articles/PMC2995713/
● https://www.sciencedirect.com/science/article/pii/S1532046417302563/
```

### Q & A

**Q.1: What exactly is symptom expansion which you are trying to achieve using SympGraph?
How is it beneficial?
Ans.** The symptom expansion is an application of SympGarph. As its name suggests, it is an
expansion of a given set of symptoms to an additional set of symptoms for disease by using
Sympgraph structure.
For example, disease like Congestive Heart failure (CHF) has fewer known symptoms according
to Framingham criteria. We can add more symptoms for CHF using Symptom expansion which
can help in early detection of CHF.

**Q.2: What are the uses and benefits of ontology that you are creating?
Ans.** Ontology describes the various parts of our information extraction system and the
relationship between them. We are creating a disease-symptom-treatment ontology which has
defined relationship among the various diseases, symptoms and the treatment. This enables the
user to give any of the classes (disease, symptom or Treatment) as input and get other two
related classes for that.
For ex: user can get the most frequent symptoms for inputted disease, most frequent diseases
for a symptom or most frequent treatments for a disease.

**Q.3: In slide 10, there is a post with heading “Mystery, just no idea” for such posts, the user
might not be sure of the disease which can be wrong. Will this not affect the accuracy of the
results?
Ans.** The web scrapper gives the details of post category, post heading, post body and post tags
to the Metamap annotator. When a disease is not found in body, heading then the annotator
looks for post category which definitely is a disease category (but might represent a broad area).
The symptoms will then be related to this disease. If the user is wrong, then the confidence level
of such symptoms will be less and hence won’t show up in the end search results since the
number of such posts will be less when compared to the total comments we will be scrapping.
So, there won’t be much effect on accuracy of end search results.

**Q.4: How is the disease list useful?
Ans.** Disease list in the search interface denotes the most probable disease returned for a
symptom or a treatment. For ranking the diseases, we are taking into account the confidence,
which is calculated based on the frequency of its occurrence for that symptom or treatment.
Hence, searching a disease based on symptom will return the probable disease of that symptom
based on users post.

**Q.5 Why do you use Metamap for extracting the UMLS concepts? How is it better than the
other tools?
Ans.** Since Metamap specifically uses UMLS to extract concepts, it gives better results than any
other Natural Language toolkits which are for a general purpose. It is specifically created to
extract biomedical terminologies. There are similar tools like CTakes which also uses UMLS to
extract concepts. Metamap and CTakes gives comparable results as shown in previous research.
Therefore, we selected Metamap for extracting concepts.


