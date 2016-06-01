package model

import securesocial.core.BasicProfile

case class User(main: BasicProfile, identities: List[BasicProfile], id: Int)