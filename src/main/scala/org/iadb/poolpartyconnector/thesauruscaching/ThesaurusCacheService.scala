package org.iadb.poolpartyconnector.thesauruscaching

/**
 *
 * Created by Daniel Maatari Okouya on 6/19/15.
 *
 * A Service That Cache an External Thesarus
 *
 */
trait ThesaurusCacheService {

  def isSchemeInCache(scheme: Option[String]): Boolean
}

/**
 * An Implementation of the CacheService for the PoolParty Thesaurus Server
 *
 */
case class ThesaurusCacheServicePoolPartyImpl() extends  ThesaurusCacheService {


  //TODO support JelCode and Series
  var schemeList: List[String] =  List("http://thesaurus.iadb.org/publicthesauri/IdBTopics",
                                       "http://thesaurus.iadb.org/publicthesauri/IdBDepartments",
                                       "http://thesaurus.iadb.org/publicthesauri/IdBInstitutions",
                                       "http://thesaurus.iadb.org/publicthesauri/IdBCountries",
                                       "http://thesaurus.iadb.org/publicthesauri/IdBTopics")


  /**
   *
   * Check if a scheme is being Cached
   *
   * @param scheme The scheme we want to know if it is in the cache
   * @return
   */
  override def isSchemeInCache(scheme: Option[String]): Boolean = {

    scheme match {

      case None => false
      case Some(e) => schemeList.contains(e)
      case _ => false


    }

  }
}
