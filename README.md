base-play-app 
=============

A simple play application be used as a template, providing some 
standards for persistence, injecting dependencies, authentication, 
logging, configuration, testing, and styling. 

Usage
=====

To run the app use `./activator run`. To test use 
`./activator test`. To create a runnable archive use 
`./activator dist`.

Configuration
=============
Configuration in play is based on the typesafe config library. On startup,
the application scan the classpath looking for the `application.conf` 
file, and there is a fallback mechanism that is explained the in config 
library documentation.

This template adds a custom application loader which allows to look for
an override file depending on the applcation mode (think of it as an 
environment). 
Play supports three modes: DEV, TEST, and PROD.
The override configuration files are respectively `application.dev.conf`,
`application.test.conf`, and `application.prod.conf`.

Persistence
===========
Slick is used on the persistence layer. While there is a play plugin to
quickly configure the database, I did a little more work to setup slick
and I get more control in return. I can test my persistence code without 
requiring any play object, which means I can use the persistence code 
in projects that do not use play.

Slick provides a DSL that allows to interact with the database as if
it were a Scala collection. The queries are typesafe, 

Dependency Injection
====================
Coming soon...

Authentication
==============
We are using the [securesocial](http://securesocial.ws/) play plugin 
as authentication library. This was wired up to the database via slick 
(see `UserServiceImpl` and `AuthPersistenceService` for more detail).

Securesocial exposes many configuration keys through the standard 
typesafe config mechanism. The `securesocial.conf` contains the 
configuration specific to securesocial.

Logging
=======
Coming soon...

Testing
=======
Scalatest is used as testing framework. All tests included in this
template use the FunSpec style. You can arbitrarily nest `describe` and
`it` blocks to get a nice console output from scalatest.

If you need to run a single test, you can use:
```
./activator "testOnly *ApiSpec"
```

An example spec that starts a play application and makes HTTP calls to 
it is `ApiSpec`. It uses `OneServerPerSuite` trait that is provided
by the play/scalatest integration library. 

Styling
=======
We are using [twitter bootstrap](http://getbootstrap.com/) and a theme
downloaded from [bootswatch](http://bootswatch.com/). Bootstrap is a 
dependency in `build.sbt` and is provided by webjars.

The bootswatch file was downloaded directly from the bootswatch website
and placed in the `app/assets/stylesheets` directory, where it gets 
compiled by the play `sbt-less` plugin.
