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
 *  - Max OS X via Growl
 *  - Windows via Growl for Windows
 *  - Linux via libnotify
 *
 * Revision information:
 * $Rev$
 * $Author$
 * $Date$
 */

class GromGrailsPlugin {
    def version		= "0.2.5"
    def grailsVersion	= "1.3.4 > *"
    def dependsOn	= [:]
    def pluginExcludes  = [
                "grails-app/views/error.gsp",
				"grails-app/conf/DataSource.groovy",
                "web-app/css",
                "web-app/images",
                "web-app/js"
    ]
    def author		= "Jeroen Wesbeek"
    def authorEmail	= "work@osx.eu"
    def title		= "Grom"
    def description	= "Grom (Dutch for Growl) sends Grails notifications to Mac OS X and Windows using Growl, or to Linux using libnotify."
    def documentation   = "https://github.com/4np/grails-grom/blob/master/README.md"
    def license         = "APACHE"
    def issueManagement = [ system: "github", url: "https://github.com/4np/grails-grom/issues" ]
    def scm             = [ url: "https://github.com/4np/grails-grom" ]

    // Extra (optional) plugin metadata

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
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

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
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
		def pluginBasePath = getPluginDirectory(application)
		def applicationName = application.properties.metadata['app.name'].toString()

		// set up Grom
		try {
			setUpGrom(applicationName, pluginBasePath, log)

		} catch (Exception e) {
			log.warn "Grom: encountered a problem setting up the global grom method"
			log.warn "  --> ${e.getMessage()}"
			log.warn "  this probably means this is a server instance which has no"
			log.warn "  notification daemon running, so you can ignore this message"
		}
	}

	/**
	 * add the grom method to the String class
	 * @param applicationName
	 * @param pluginBasePath
	 * @param log
	 * @return
	 */
	static setUpGrom(String applicationName, String pluginBasePath, log) {
		try {
			// try to send a message
			grom('Starting application', applicationName, pluginBasePath)

			// all went well, set up grom method
			String.metaClass.grom = { ->
				def message = delegate.toString()
				try {
					grom(message, applicationName, pluginBasePath)
				} catch (Exception e) {
					log.error "Grom: encountered an error gromming a message (${message})"
					log.error "  --> ${e.getMessage()}"
				}
			}
		} catch (Exception e) {
			// make sure the grom method is available...
			String.metaClass.grom = { }

			// ...and pass on the exception
			throw new Exception(e.getMessage())
		}
	}

	/**
	 * Send a message to the Window Manager's unified messaging system
	 * @param message
	 * @param applicationName
	 * @param pluginBasePath
	 * @return
	 */
	static grom(String message, String applicationName, String pluginBasePath) {
		if (System.properties["os.name"] == "Mac OS X") {
			gromMacOS(message, applicationName, pluginBasePath)
		} else if (System.properties["os.name"] == "Linux") {
			gromLinux(message, applicationName, pluginBasePath)
		} else if (System.properties["os.name"] =~ "Windows") {
			gromWindows(message, applicationName, pluginBasePath)
		} else {
			throw new Exception("unsupported OS ${System.properties["os.name"]}")
		}
	}

	/**
	 * Send a message to Mac OS X using Growl
	 * @param message
	 * @param applicationName
	 * @param pluginBasePath
	 * @return
	 */
	static gromMacOS(String message, String applicationName, String pluginBasePath) {
		// use a custom Apple script to send Growl notifications
		def cmd = [
			"osascript",
			"${pluginBasePath}/web-app/lib/grom.scpt",
			applicationName,
			message
		]

		Runtime.getRuntime().exec((String[]) cmd.toArray())
	}

	/**
	 * send a message to Linux using libnotify
	 * @param message
	 * @param applicationName
	 * @param pluginBasePath
	 * @return
	 */
	static gromLinux(String message, String applicationName, String pluginBasePath) {
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
		//
		// ~ ~ ~ Thanks to: ~ ~ ~
		// - Federico Pedemonte for reporting an issue with notifications
		//   filling up the 'taskbar' on Gnome3, and for providing the fix :)
		try {
			def cmd = [
				"notify-send",
				"-t",
				"2000",
				"-u",
				"normal",
				"-h",
				"string:x-canonical-append:allowed",
				"-h",
				"int:transient:1",
				"--icon",
				"${pluginBasePath}/web-app/lib/grailslogo.png",
				"Grails :: ${applicationName}",
				"${message}"
			]

			Runtime.getRuntime().exec((String[]) cmd.toArray())
		} catch (Exception e) {
			// check if libnotify is installed
			if (e.getMessage() =~ "No such file") {
				throw new Exception("libnotify is not installed")
			} else {
				throw new Exception(e.getMessage())
			}
		}
	}

	/**
	 * Send a message to Windows using Growl for Windows
	 * @param message
	 * @param applicationName
	 * @param pluginBasePath
	 * @return
	 */
	static gromWindows(String message, String applicationName, String pluginBasePath) {
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
			throw new Exception("growl for windows is not installed or not accepting messages")
		} else if (!(feedback =~ "Notification sent successfully")) {
			throw new Exception(feedback)
		}
	}
}
