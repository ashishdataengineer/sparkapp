<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
   <xsl:output method="xml" version="1.0" encoding="iso-8859-1" indent="yes" />
   <xsl:template match="/">
      <countries>
         <xsl:for-each select="//country">
            <xsl:sort select="@population" order="descending" data-type="number" />
            <country>
               <name>
                  <xsl:value-of select="@name" />
               </name>
               <population>
                  <xsl:value-of select="@population" />
               </population>
               <area>
                  <xsl:value-of select="@area" />
               </area>
               <cities>
                  <xsl:for-each select="city">
                     <xsl:sort select="population" order="descending" />
                     <city>
                        <name>
                           <xsl:value-of select="name" />
                        </name>
                        <population>
                           <xsl:value-of select="population" />
                        </population>
                     </city>
                  </xsl:for-each>
               </cities>
            </country>
         </xsl:for-each>
      </countries>
   </xsl:template>
</xsl:stylesheet>
