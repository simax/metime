# Metime


## Development

To start the api. At the terminal (or command line on Windows), type `lein ring server-headless 3030`.

At the terminal, type: lein figwheel
in the browser navigate to 'http://localhost:3449'. This will connect to the REPL.

Note: Global app state is held in the re-frame.db/app-db atom. @re-frame.db/app-db  

## REPL
Start a REPL 
In IntelliJ. 
Run the Clojurescript REPL
Once in the REPL window, type:
(use 'figwheel-sidecar.repl-api)
(cljs-repl)
You should now see Figwheel Controls, start-autobuild etc
You can now interact with the app and the browser.
Try typing (js/alert "Hello World!!!") and you should see the alert popup in the browser.


## Database migration

Using clj-sql-up to handle DB migrations
See: https://github.com/ckuttruff/clj-sql-up for details


## Deploying to Heroku

This assumes you have a
[Heroku account](https://signup.heroku.com/dc), have installed the
[Heroku toolbelt](https://toolbelt.heroku.com/), and have done a
`heroku login` before.

``` sh
git init
git add -A
git commit
heroku create
git push heroku master:master
heroku open
```

## Running with Foreman

Heroku uses [Foreman](http://ddollar.github.io/foreman/) to run your
app, which uses the `Procfile` in your repository to figure out which
server command to run. Heroku also compiles and runs your code with a
Leiningen "production" profile, instead of "dev". To locally simulate
what Heroku does you can do:

``` sh
lein with-profile -dev,+production uberjar && foreman start
```

Now your app is running at
[http://localhost:5000](http://localhost:5000) in production mode.

## License

Copyright © 2014 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
