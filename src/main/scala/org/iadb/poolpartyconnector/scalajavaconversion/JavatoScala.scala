package org.iadb.poolpartyconnector.scalajavaconversion

import scala.collection.JavaConverters._

/**
 * Created by Daniel Maatari Okouya on 8/4/15.
 *
 * Utility to be used from java to convert between the two.
 *
 */
object JavatoScala {


  def asJava[A](list: java.util.List[A]): List[A] = {
    list.asScala.toList
  }

  def asJava[A, B](map: java.util.Map[A, B]) = {
    map.asScala
  }


}
