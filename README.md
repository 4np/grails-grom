# Grom
Grom [is Dutch for Growl](http://www.mijnwoordenboek.nl/werkwoord/grommen), which already gives away a hint on what this plugin does. This plugin sends grails notifications to a unified notifications daemon. It is currently compatible with Growl for Mac OS X, Growl for Windows and Notify OSD for Linux.

Development of this plugin was inspired by [N.A.D.D. Neutralizer](http://www.grails.org/plugin/nadd-neutralizer), a plugin to decrease development latency by using the speech synthesizer to notify you when a project was up and running. While this was very useful at the time and certainly has it's uses (me and my colleagues even gave [feedback](http://phatness.com/2010/04/nadd-neutralizer/) to improve support), it was a bit too obtrusive for our  liking as it started iTunes if not running and at some point you just wished it stopped talking.

So what would be better than to use Growl instead, as it is unobtrusive and the whole reason for running a notification daemon is to unify messaging into a single location. So I developed Grom initially for [Growl](http://growl.info/) on Mac OS X, and I added multi-platform support later by including support for [Growl for Windows](http://www.growlforwindows.com) and [Notify OSD](https://wiki.ubuntu.com/NotifyOSD) for Linux which is by default shipped with Ubuntu.

![image](https://dl.dropbox.com/s/uivr3y8ra8v11v3/grom-mac-multiple.jpg?dl=1)

## Installation
To install the plugin, add a compile time dependency to your application's ```BuildConfig```:

```groovy
grails.project.dependency.resolution = {
	...
	plugins {
		compile ':grom:0.2.3'
		...
	}
}
```

## Examples
Grom will send notifications for default Grails events, but it also possible to send custom notifications by *Grom-ing* a String:

```groovy
"bootstrapping application".grom()
```

If you, at some point in the future, decide to remove the from plugin, either before packaging to a production environment or for some other reason, keep in mind that any custom *Grom-ing* will break your application as the grom() method will not be available. This can be resolved by using something like:

```groovy
class MyController {
	def myAction {
		if (String.metaClass.getMetaMethod("grom")) "my action is being called".grom()
	}
}
```

You can also *grom* bootstrap messages:

```groovy
class BootStrap {
	def init = { servletContext ->
		if (String.metaClass.getMetaMethod("grom")) "bootstrapping application".grom()
		...
	}

	def destroy = {
		if (String.metaClass.getMetaMethod("grom")) "stopping application...".grom()
		...
	}
} 
```

*The Grom plugin can be in a production package, as it will just not do anything if no notification agent is present. It may spit out a warning to the logfile, but that can be ignored.*

## Mac OS X support
Most people I know already run [Growl](http://growl.info/) as a notification daemon, so I'm pretty sure I'm not telling you anything new here. In order to have any benefit from this plugin you should install Growl to centralize messages. In case you're not familiar with Growl, [many Mac applications](http://growl.info/applications.php) already support Growl integration so it will be an overall improvement to your Mac experience to have Growl installed. With or without using this plugin.

![image](https://dl.dropbox.com/s/mdingf3s7mpblqx/grom-mac.jpg?dl=1)

## Linux / Ubuntu support
The unified messaging implementation in Ubuntu provided through [Notify OSD](https://wiki.ubuntu.com/NotifyOSD). I am not entirely sure if it is also available for other distributions, but as Ubuntu is by far the most common distribution used in corporate organizations and home desktop installations, I chose to support Notify OSD. Notify OSD is by default available in (the latest releases of) Ubuntu, however the client binary is not provided so you need to install that to get Grom working on Ubuntu. To do that, just install libnotify-bin and you're good to go:

```
apt-get install libnotify-bin
```

While Grom will now work, there are a number of limitations with the default implementation of Notify OSD on Ubuntu:
* the timeout parameter is broken (notifications stay on the screen too long)
* the notifications cannot be customized
* grouping of notifications does not work

Luckily the first two limitations can be solved by using the [Leolik patch](http://www.webupd8.org/2010/05/finally-easy-way-to-customize-notify.html) for Notify OSD. Unfortunately the latter does not seem to be getting picked up [anytime soon](https://bugs.launchpad.net/ubuntu/+source/notify-osd/+bug/434913). Perhaps if more people voted the issue up, as support for grouping is already integrated into this plugin.

![image](https://dl.dropbox.com/s/4tlgyf7a06dyljq/grom-linux.jpg?dl=1)

*The default Grails events like 'Server Running' automatically show notifications*

![image](https://dl.dropbox.com/s/uivr3y8ra8v11v3/grom-mac-multiple.jpg?dl=1)

*It's also possible to show custom notifications*

## Windows support
[Growl for Windows](http://www.growlforwindows.com) basically is a port of Growl for Mac OS X and brings unified messaging to Windows. To have any benefit from this plugin, you should install Growl for Windows and the [Microsoft .NET Framework](http://msdn.microsoft.com/en-us/netframework) (v2.0 or higher)

*If you do not have any of the following notification daemons installed or if you're using a platform which is not -yet?- supported, the Grom plugin will just not send any notifications. Which means you can keep it in a project repository where multiple people work on the same project, without breaking the project on machines that do not support unified notifications.*
