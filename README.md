# net.loadbang.lib

This is a hotch-potch of rather antiquated utilities for [MaxMSP][max]
used by the rest of the `net.loadbang` suite. The prebuilt JAR files
are in the sub-directory `distribution`, or the library can be built
from the enclosed sources using Maven. To install without building,
copy all the JAR files from `distribution` *except* the
`max-[...].jar` file into the `java/lib` directory of your Max
installation.

The Maven build is pretty straightforward - import into Eclipse or run
Maven from the command line. One wrinkle: the `max.jar` file isn't
available in any Maven repository, so install the one we've included
in this distribution into your local repository as follows:

        mvn install:install-file \
            -Dfile=com.cycling74.max-5.1.9.jar \
            -DgroupId=com.cycling74 \
            -DartifactId=com.cycling74.max \
            -Dversion=5.1.9 \
            -Dpackaging=jar \
            -DgeneratePom=true

(We pulled the `max.jar` file from MaxMSP 5.1.9, but there are no
significant advances that we know of in the version shipping with
Max6. All of our `net.loadbang` projects have explicit dependencies on
the `5.1.9` version, although they run happily under Max6.)

## License

Distributed under the [GNU General Public License][gpl].

Copyright (C) 2011 Nick Rothwell.

[max]: http://cycling74.com/products/max/
[jython]: https://github.com/cassiel/net.loadbang.jython
[clojure]: https://github.com/cassiel/net.loadbang.clojure
[gpl]: http://www.gnu.org/copyleft/gpl.html
