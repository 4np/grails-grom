import org.codehaus.groovy.grails.plugins.GrailsPluginUtils

/**
 * Grom Grails Plugin
 *
 * @Author Jeroen Wesbeek
 * @Since 20101026
 * @Description
 *
 * A plugin to send notifications to the window manager of the
 * host OS. It currently supports the following:
 * 	- Max OS X via Growl
 *  - Windows via Growl for Windows
 *  - Linux via libnotify
 *
 * Revision information:
 * $Rev$
 * $Author$
 * $Date$
 */
class GromGrailsPlugin {
	def version = "0.2.0"
	def grailsVersion = "1.3.4 > *"
	def dependsOn = [:]
	def pluginExcludes = ["grails-app/views/error.gsp"]
	def author = "Jeroen Wesbeek"
	def authorEmail = "work@osx.eu"
	def title = "Grom"
	def description = '''Grom (Dutch for Growl) sends Grails notifications to Mac OS X and Windows using Growl, or to Linux using libnotify.'''
	def documentation = "http://grails.org/plugin/grom"

	def doWithWebDescriptor = { xml ->
		// TODO Implement additions to web.xml (optional), this event occurs before
	}

	def doWithSpring = {
		// TODO Implement runtime spring config (optional)
	}

	def getPluginDirectory = { application ->
		// at this stage we do not have the pluginPath
		// available so we need to instantiate / define
		// them ourselves

		if (org.codehaus.groovy.grails.plugins.GrailsPluginUtils.pluginDirectories) {
			// application is being run locally (grails run-app)
			return org.codehaus.groovy.grails.plugins.GrailsPluginUtils.pluginDirectories.find { it.toString() =~ 'grom' }.file.toString()
		} else {
			// we are packaged in a war on an application server
			// this works on tomcat, however, this will probably
			// not work on other application servers...
			return System.properties['catalina.base'] + "/webapps" + application.properties.classLoader.toString().split("\n").find { it =~ "context" }.split(":")[1].trim() + "/plugins/grom-${version}/lib"
		}
	}

	def doWithDynamicMethods = { applicationContext ->
		// make sure the application will not -in any case- break because
		// of this plugin
		try {
			def pluginBasePath = getPluginDirectory(application)
			def applicationName = application.properties.metadata['app.name'].toString()

			// check what OS we are running
			if (System.properties["os.name"] == "Mac OS X") {
				// It's a Mac, we can use Growl (if installed of course)
				String.metaClass.grom = {->
					def message = delegate.toString()

					try {
						// use a custom Apple script to send Growl notifications
						def cmd = [
							"osascript",
							"${pluginBasePath}/web-app/lib/grom.scpt",
							applicationName,
							message
						]

						Runtime.getRuntime().exec((String[]) cmd.toArray())
					} catch (Exception e) {
						// no gromming for now?
						log.error "Grom: plugin encountered a problem sending growl notifications"
						log.error "      --> ${e.getMessage()}"
					}
				}
			} else if (System.properties["os.name"] == "Linux") {
				// it's time to notify tux
				//
				// Unfortunately the timeout parameter (-t) is broken in
				// Ubuntu so the 2 second timeout is being ignored. Also,
				// the installation in Ubuntu does not allow customization
				// of notifications. However, install the leolik patch in
				// Ubuntu to resolve both issues:
				// http://www.webupd8.org/2010/05/finally-easy-way-to-customize-notify.html
				//
				// Also, appending messages does not seem to work when using
				// notify-bin. Put it in here if this feature will be implemented
				// in the future. Also see:
				// https://bugs.launchpad.net/ubuntu/+source/notify-osd/+bug/434913
				String.metaClass.grom = {->
					def message = delegate.toString()

					try {
						def cmd = [
							"notify-send",
							"-t",
							"2000",
							"-u",
							"critical",
							"-h",
							"string:x-canonical-append:allowed",
							"--icon",
							"${pluginBasePath}/web-app/lib/grailslogo.png",
							"Grails :: ${applicationName}",
							"${message}"
						]

						Runtime.getRuntime().exec((String[]) cmd.toArray())
					} catch (Exception e) {
						// check if libnotify is installed
						if (e.getMessage() =~ "No such file") {
							log.error "Grom: please install libnotify in order to receive notifications from the Grom plugin"
							log.error "      see ${documentation} for more information"
						} else {
							log.error "Grom: plugin encounted a problem sending notifications"
							log.error "      --> ${e.getMessage()}"
						}
					}
				}
			} else if (System.properties["os.name"] =~ "Windows") {
				// use growl for windows to send notifications
				// Note: growlnotify requires the Microsoft .NET Framework (v2.0 or higher)
				String.metaClass.grom = {->
					def message = delegate.toString()
					try {
						def cmd = [
							"\"${pluginBasePath}\\web-app\\lib\\growlnotify.com\"",
							"/a:\"${applicationName}\"",
							"/r:\"Grom\"",
							"/n:\"Grom\"",
							"/t:\"Grails :: ${applicationName}\"",
							"/i:\"${pluginBasePath}\\web-app\\lib\\grailslogo.png\"",
							"\"${message}\""
						]

						// execute command. Use String.execute as runtime.exec does
						// not work properly in combination with growlnotify.com
						def process = cmd.join(' ').execute()
						def feedback = ""

						// handle feedback
						process.in.eachLine { line -> feedback += line }
						if (feedback =~ "The destination server was not reachable") {
							log.error "Grom: please install growl for windows in order to receive notifications from the Grom plugin"
							log.error "      see ${documentation} for more information"
						} else if (!(feedback =~ "Notification sent successfully")) {
							throw new Exception(feedback)
						}
					} catch (Exception e) {
						// no gromming for now?
						log.error "Grom: plugin encountered a problem sending growl notifications"
						log.error "      --> ${e.getMessage()}"
					}
				}
			} else {
				// unsupported OS
				String.metaClass.grom = {
					log.error "Grom: ${System.properties["os.name"]} is currently not supported"
					log.error "      see ${documentation} for more information"
				}
			}
		} catch (Exception e) {
			// something went terribly wrong... :S
			// show feedback...
			log.error "Grom: plugin has encountered a problem setting up the global grom method"
			log.error "      --> ${e.getMessage()}"

			// ...and make sure the method is available
			String.metaClass.grom = {
				// just print it to the terminal
				log.debug delegate.toString()
			}
		}
	}

	def doWithApplicationContext = { applicationContext ->
		// TODO Implement post initialization spring config (optional)
	}

	def onChange = { event ->
		// TODO Implement code that is executed when any artefact that this plugin is
		// watching is modified and reloaded. The event contains: event.source,
		// event.application, event.manager, event.ctx, and event.plugin.
	}

	def onConfigChange = { event ->
		// TODO Implement code that is executed when the project configuration changes.
		// The event is the same as for 'onChange'.
	}
}