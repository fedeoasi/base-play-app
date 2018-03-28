base-play-app 
=============

A simple play application be used as a template, providing some 
standards for persistence, authentication, logging, configuration, 
testing, and styling. 

Usage
=====

The play application is structured as an [SBT](https://www.scala-sbt.org/)
project. When SBT is installed, you can run `sbt` to open a
shell and then use the following commands:
- `run`: Runs the application and listens on `localhost:9000`
- `test`: Runs the tests
- `dist`: Creates a runnable archive (which can be used in prod)

Configuration
=============
Configuration in play is based on the 
[typesafe config](https://github.com/typesafehub/config) library. 
On startup, the application scans the classpath looking for the 
`application.conf` file, and there is a fallback mechanism that is 
explained the in config library documentation.

This template adds a custom application loader which allows to look for
an override file depending on the applcation mode (think of it as an 
environment). 
Play supports three modes: DEV, TEST, and PROD.
The override configuration files are respectively `application.dev.conf`,
`application.test.conf`, and `application.prod.conf`.

Persistence
===========
[Slick](http://slick.lightbend.com/) is used in the persistence layer. 
While there is a play plugin to
quickly configure the database, a bit more work was done to setup slick
to get more control. I can test my persistence code without 
requiring any play object, which means I can use the persistence code 
in projects that do not use play.

Slick provides a DSL that allows to interact with the database as if
it were a Scala collection issuing both typesafe queries (preferred)
and plain SQL.

The persistence layer is structured as follows:

1. A database component (e.g., `AuthDbComponenet`) contains the table model
2. A data access layer (e.g., `AuthDal`) takes a JdbcDriver as input and
  gives access to the table model
3. A persistence service (e.g., `AuthPersistenceServiceImpl`) takes a
database as input and exposes persistence level methods to the application 
and uses a specific `Dal`
4. A database is a slick wrapper around a specific JDBC database object.

The template contains two databases, SQLite for production and H2 for 
testing.

## Database migrations

The library used for database migrations is flyway. 
On the testing side with H2, the tables are generated directly from 
the model every time the database is created.

On the production side with SQLite,  the `conf/db/migration` contains
the database migration (evolution) scripts. There is a `SqliteDdlPrinter`
class that prints the ddl that generates the model tables. It can be 
invoked using `./activator "runMain persistence.SqliteDdlPrinter"`.

Dependency Injection
====================
The guice module `Module` contains the application bindings. 
This module is loaded on application startup.

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
The logging configuration can be found in `conf/logback.xml`.
 
There is a filter called `LoggingFilter` that logs information about
the current HTTP request. Requests containing `/assets` and `/status` in
the URL are not logged.

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
