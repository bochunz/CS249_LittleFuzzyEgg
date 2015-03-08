#!/usr/bin/python

import xml.sax
import sys

class MovieHandler( xml.sax.ContentHandler ):
   def __init__(self):
      self.CurrentData = ""
      self.level = 0

      self.sku = ""
      self.name = ""
      self.shortDescription = ""
      self.startDate = ""
      self.features = []

   # Call when an element starts
   def startElement(self, tag, attributes):
      self.CurrentData = tag
      self.level += 1

   # Call when an elements ends
   def endElement(self, tag):
      if self.CurrentData == "name" and self.level == 3:
         sys.stdout.write('"'+self.name+'",')
      elif self.CurrentData == "sku" and self.level == 3:
         sys.stdout.write('"'+self.sku+'",')
      elif self.CurrentData == "startDate" and self.level == 3:
         sys.stdout.write('"'+self.startDate+'",\n')

      # elif self.CurrentData == "shortDescription" and self.level == 3:
      #    sys.stdout.write('"'+self.shortDescription+'",')

      if tag == "features":
         # print '"',"||".join(self.features), '"'
         self.features = []

      self.CurrentData = ""
      self.level -= 1

   # Call when a character is read
   def characters(self, content):
      if self.CurrentData == "name":
         self.name = content
      elif self.CurrentData == "sku":
         self.sku = content
      elif self.CurrentData == "shortDescription":
         self.shortDescription = content
      elif self.CurrentData == "startDate":
         self.startDate = content
      elif self.CurrentData == "feature":
         pass
         # self.features.append(content.encode("utf-8").replace('\n', ' ').replace('"', ''))

  
if ( __name__ == "__main__"):
   
   # create an XMLReader
   parser = xml.sax.make_parser()
   # turn off namepsaces
   parser.setFeature(xml.sax.handler.feature_namespaces, 0)

   # override the default ContextHandler
   Handler = MovieHandler()
   parser.setContentHandler( Handler )
   
   parser.parse("small_product_data.xml")