package eu.timepit.datapackage

import cats.data.Xor
import eu.timepit.datapackage.util.JsonKey
import eu.timepit.datapackage.util.JsonKey.keyOf
import io.circe._

sealed trait ResourceLocation extends Product with Serializable

object ResourceLocation {
  final case class Url(value: String) extends ResourceLocation
  final case class Path(value: String) extends ResourceLocation
  final case class Data() extends ResourceLocation

  implicit val decoderResourceLocation: Decoder[ResourceLocation] =
    Decoder.instance { c =>
      def url = c.downField(keyOf[Url]).as[String].map(Url.apply)
      def path = c.downField(keyOf[Path]).as[String].map(Path.apply)
      def data = c.downField(keyOf[Data]).as[Unit].map(_ => Data())
      def fail = Xor.Left(DecodingFailure("ResourceLocation", c.history))
      url.orElse(path).orElse(data).orElse(fail)
    }

  implicit val encoderResourceLocation: Encoder[ResourceLocation] =
    Encoder.instance {
      case Url(value) => Json.obj(keyOf[Url] -> Json.fromString(value))
      case Path(value) => Json.obj(keyOf[Path] -> Json.fromString(value))
      case Data() => Json.obj(keyOf[Data] -> Json.Null)
    }

  implicit val jsonKeyUrl: JsonKey[Url] = JsonKey.instance("url")
  implicit val jsonKeyPath: JsonKey[Path] = JsonKey.instance("path")
  implicit val jsonKeyData: JsonKey[Data] = JsonKey.instance("data")
}