package eu.timepit.datapackage

import eu.timepit.datapackage.testUtil._
import eu.timepit.datapackage.util.nameOf
import org.scalacheck.Properties

class ResourceLocationSpec extends Properties(nameOf[ResourceLocation]) {
  property("JSON round-trip") = jsonRoundTrip[ResourceLocation]
  property("JSON decoding error") = jsonDecodeNull[ResourceLocation]
}
