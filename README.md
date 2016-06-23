base-play-app 
=============

A simple play application be used as a template, providing some 
standards for persistence, dependency injection, authentication, 
logging, configuration, testing, and styling. 

Usage
=====

To run the app use ```./activator run```. 

To run tests use ```./activator test```

To create a runnable archive use `./activator dist`.

Configuration
=============

Persistence
===========
Coming soon... 

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

Styling
=======
We are using [twitter bootstrap](http://getbootstrap.com/) and a theme
downloaded from [bootswatch](http://bootswatch.com/). Bootstrap is a 
dependency in ```build.sbt``` and is provided by webjars.

The bootswatch file was downloaded directly from the bootswatch website
and placed in the `app/assets/stylesheets` directory, where it gets 
compiled by the play `less` plugin.
