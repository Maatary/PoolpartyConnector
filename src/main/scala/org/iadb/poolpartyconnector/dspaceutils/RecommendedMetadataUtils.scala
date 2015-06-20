package org.iadb.poolpartyconnector.dspaceutils

import org.dspace.content.Item
import org.dspace.content.authority.Choices
import org.iadb.poolpartyconnector.utils.JsonUtils.{FreeTerm, Concept, ConceptResults}

/**
 * Created by Daniel Maatari Okouya on 6/6/15.
 */
object RecommendedMetadataUtils {


  /**
   *
   * Take an Item and a List of recommended metadata and returned the item updated with this metadata
   *
   * @param itemWrapper
   * @param recommendedmetadata
   * @return
   */
  def deliverItemWithRecommendedMetadata(itemWrapper : DspaceItemWrapper, recommendedmetadata: ConceptResults): Item = {

    recommendedmetadata.document foreach { doc =>
      doc.concepts foreach { conceptList =>
        conceptList foreach { concept =>
          val dsmetadata = getConceptInDspaceScheme(concept, Choices.CF_UNCERTAIN)
          //(schema: String, element: String, qualifier: String, lang: String, value: String, authority: String, confidence: Int)
          itemWrapper.addMetadata(dsmetadata._1, dsmetadata._2, dsmetadata._3, dsmetadata._4, dsmetadata._5, dsmetadata._6, dsmetadata._7)
        }
      }
      doc.freeTerms foreach { freeTermList =>
        freeTermList foreach { freeTerm =>
          val dsmetadata = getFreeTermInDspaceScheme(freeTerm, -2)
          itemWrapper.addMetadata(dsmetadata._1, dsmetadata._2, dsmetadata._3, dsmetadata._4, dsmetadata._5, dsmetadata._6, dsmetadata._7)
        }
      }
    }

    itemWrapper.item
  }


  /**
   *
   * Convert a PoolParty concept in a form aligned with Dspace representation
   *
   * @param concept A PoolParty Concept
   * @return
   */
  private def getConceptInDspaceScheme(concept: Concept, confidence: Int) : Tuple7[String, String, String, String, String, String, Int] = {


    val eltandqual = concept.conceptSchemes.head.uri match {

      case "http://thesaurus.iadb.org/publicthesauri/IdBTopics" => ("dc", "subject", null)
      case "http://thesaurus.iadb.org/publicthesauri/IdBDepartments" => ("iadb", "department", null)
      case "http://thesaurus.iadb.org/publicthesauri/IdBInstitutions" => ("dc", "contributor", "institution")
      case "http://thesaurus.iadb.org/publicthesauri/IdBCountries" => ("dc", "coverage", "placename")
      case "http://thesaurus.iadb.org/publicthesauri/IdBAuthors" => ("dc", "contributor", "author")

    }

    //(schema: String, element: String, qualifier: String, lang: String, value: String, authority: String, confidence: Int)
    (eltandqual._1, eltandqual._2, eltandqual._3, concept.language, concept.prefLabel, concept.uri, confidence)

  }


  /**
   *
   * Convert a PoolParty freeTerm in a form aligned with Dspace representation
   *
   * @param freeTerm A PoolParty Concept
   * @return
   */
  private def getFreeTermInDspaceScheme(freeTerm: FreeTerm, confidence: Int) : Tuple7[String, String, String, String, String, String, Int] = {

    //(schema: String, element: String, qualifier: String, lang: String, value: String, authority: String, confidence: Int)
    ("dc", "subject", null, "en", freeTerm.textValue, "suggest", -2)

  }

}
