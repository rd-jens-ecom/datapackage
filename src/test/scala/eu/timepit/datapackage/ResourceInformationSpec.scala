package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import eu.timepit.datapackage.util.nameOf
import org.scalacheck.Properties

class ResourceInformationSpec extends Properties(nameOf[ResourceInformation]) {
  property("JSON round-trip") = jsonRoundTrip[ResourceInformation]
  property("JSON decoding error") = jsonDecodeNull[ResourceInformation]
}
