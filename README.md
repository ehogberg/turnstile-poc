# turnstile-poc

A (very) simple (sorta) editor for (a very small part of) LDE partner profiles.  Uses the magic of Reagent and Clojurescript to build a single page webapp allowing new partners to be created and existing ones to be updated, but doesn't actually save anything anywhere.

Build-out goals were/are:

- Kick the tires on figwheel-main and see what was new/improved over the old lein-figwheel.
- Demonstrate a few basic use cases we'd need to support if we take a Clojurescript-driven approach to a for-reals rewrite of Turnstile.
- It's coding!   Fun fun fun!


## Overview

You'll need to be loaded for CLJS bear before starting.   `brew install clojure` will accomplish that for Homebrewers.   Other folks can consult <http://clojure.org/guides/getting_started.>

Once you're prepped, execute:

	lein fig:build
	
which will start a CLJS repl, launch the app and display in a browser window.

The UI is simple to the point of laughability, and hopefully self explanatory.   Dual pane SPA, left side lists known partners (launches with one partner defined),  Blue add icon lets you define a new one which is edited in the right side pane.   Click on the blue edit icons on existing partner entries to change them.   Save/cancel do what you'd expect on the editor pane.  The red trash can on partner list entries also does what you'd expect a red trash can icon to do.

.cljs code is liberally commented and hopefully also close to self-explanatory.  Three namespaces of interest, `.core` (mostly boilerplate launch code, of little interest beyond understanding how a CLJS app can bootstrap itself), `.partner` (renders the UI),  `.state` (manages the in-memory list of partners).   As a reminder, nothing is actually stored by this app; create, save and delete to your heart's content.

I welcome questions; contact me and I'll not only answer but will add my answer to this README so other folks can benefit as well.


## Development

To get an interactive development environment run:

    lein fig:build

This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

	lein clean

To create a production build run:

	lein clean
	lein fig:min


## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
