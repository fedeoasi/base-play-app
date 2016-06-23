package modules

import com.typesafe.config.ConfigFactory
import play.api.ApplicationLoader.Context
import play.api.inject.guice.{GuiceApplicationBuilder, GuiceApplicationLoader}
import play.api.{Configuration, Logger}

class CustomApplicationLoader extends GuiceApplicationLoader {
  private val logger = Logger.logger

  override protected def builder(context: Context): GuiceApplicationBuilder = {
    val mode = context.environment.mode
    val configOverride = s"application.${mode.toString.toLowerCase}.conf"
    logger.info(s"looking for config override at: $configOverride")
    val modeSpecificConfig = Configuration(ConfigFactory.load(configOverride))

    initialBuilder
      .in(context.environment)
      .loadConfig(modeSpecificConfig ++ context.initialConfiguration)
      .overrides(overrides(context): _*)
  }
}

