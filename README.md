# Grom
Grom [is Dutch for Growl](http://www.mijnwoordenboek.nl/werkwoord/grommen), which already gives away a hint on what this plugin does. This plugin sends grails notifications to a unified notifications daemon. It is currently compatible with Growl for Mac OS X, Growl for Windows and Notify OSD for Linux.

Development of this plugin was inspired by [N.A.D.D. Neutralizer](http://www.grails.org/plugin/nadd-neutralizer), a plugin to decrease development latency by using the speech synthesizer to notify you when a project was up and running. While this was very useful at the time and certainly has it's uses (me and my colleagues even gave [feedback](http://phatness.com/2010/04/nadd-neutralizer/) to improve support), it was a bit too obtrusive for our  liking as it started iTunes if not running and at some point you just wished it stopped talking.

So what would be better than to use Growl instead, as it is unobtrusive and the whole reason for running a notification daemon is to unify messaging into a single location. So I developed Grom initially for [Growl|http://growl.info/] on Mac OS X, and I added multi-platform support later by including support for [Growl for Windows](http://www.growlforwindows.com) and [Notify OSD](https://wiki.ubuntu.com/NotifyOSD) for Linux which is by default shipped with Ubuntu.

![image](http://grails.org/wikiImage/screenshots-754/Grom_with_Growl_on_Mac_OS_X_-_2.png)

More screenshots are available in the Screenshots tab.

## Mac OS X
Most people I know already run [Growl](http://growl.info/) as a notification daemon, so I'm pretty sure I'm not telling you anything new here. In order to have any benefit from this plugin you should install Growl to centralize messages. In case you're not familiar with Growl, [many Mac applications](http://growl.info/applications.php) already support Growl integration so it will be an overall improvement to your Mac experience to have Growl installed. With or without using this plugin.

## Linux / Ubuntu
The unified messaging implementation in Ubuntu provided through [Notify OSD](https://wiki.ubuntu.com/NotifyOSD). I am not entirely sure if it is also available for other distributions, but as Ubuntu is by far the most common distribution used in corporate organizations and home desktop installations, I chose to support Notify OSD. Notify OSD is by default available in (the latest releases of) Ubuntu, however the client binary is not provided so you need to install that to get Grom working on Ubuntu. To do that, just install libnotify-bin and you're good to go:

```
apt-get install libnotify-bin
```

While Grom will now work, there are a number of limitations with the default implementation of Notify OSD on Ubuntu:
* the timeout parameter is broken (notifications stay on the screen too long)
* the notifications cannot be customized
* grouping of notifications does not work

Luckily the first two limitations can be solved by using the [Leolik patch](http://www.webupd8.org/2010/05/finally-easy-way-to-customize-notify.html) for Notify OSD. Unfortunately the latter does not seem to be getting picked up [anytime soon](https://bugs.launchpad.net/ubuntu/+source/notify-osd/+bug/434913). Perhaps is more people voted the issue up, as support for grouping is already integrated into this plugin.

## Windows
[Growl for Windows](http://www.growlforwindows.com) basically is a port of Growl for Mac OS X and brings unified messaging to Windows. To have any benefit from this plugin, you should install Growl for Windows and the [Microsoft .NET Framework](http://msdn.microsoft.com/en-us/netframework) (v2.0 or higher)

*If you do not have any of the following notification daemons installed or if you're using a platform which is not -yet?- supported, the Grom plugin will just not send any notifications. Which means you can keep it in a project repository where multiple people work on the same project, without breaking the project on machines that do not support unified notifications.*
