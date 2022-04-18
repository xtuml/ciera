## To build the compiler from source:

### Dependencies

old name:       ebee0940-ead1-490b-bffa-71d1742b4409
old package:    0255fe6d-5592-479c-b00b-3326adaceedd

new name:       806662a9-c6c3-44fd-ba0f-49e7e8fa5f4a
new package:    02273349-14ab-4026-8fbd-3e8730c6a059

#### pypy

The Ciera build can use the `pypy` Python interpreter for its prebuilds. `pypy`
is significantly faster than standard CPython and saves a lot of time in
builds. `pypy` installation is optional.

Consider using [pyenv](https://github.com/pyenv/pyenv) to install an manage
`pypy`.

Alternatively, you can install `pypy` using the following:

To install on Linux:
```
sudo apt-get install pypy
curl https://bootstrap.pypa.io/get-pip.py | pypy -  # install pip for pypy
```

To install on Mac:
```
brew install pypy
```

If you choose to install `pypy` this way, you must add the line:
```
<pythonExecutable>pypy</pythonExecutable>
```

to `src/tool-core/pom.xml`, `src/tool-sql/pom.xml`, and
`src/tool-template/pom.xml` in the configuration secion of the
`pyxtuml-pre-build` goal.

#### pyxtuml

pyxtuml (>= 2.2.1) is required to build the source projects. It can be installed with:

```
pypy -m pip install pyxtuml
```

or if you already have an older version of pyxtuml installed:

```
pypy -m pip install --upgrade
```

If you are using CPython instead of `pypy`, install with:

```
python -m pip install pyxtuml
```

### Build

```
mvn install
```

Building Ciera is a relatively memory hungry task. On some systems, it may be necessary to increase
the heap size Maven is allowed to use.
```
export MAVEN_OPTS=-Xmx1g
mvn install
```

### Creating a dev workspace

Install the latested BridgePoint [nightly
build](https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html)

Set the `BPHOME` environment variable to point to your BridgePoint
installation.

Run the workspace auto-setup script:
```
bash create-workspace.sh <workspace_location>
```

If no `<workspace_location>` is provided, a temporary directory shall be created.

### Updating projects to use the latest Ciera version

```
mvn versions:use-latest-versions
```

### Creating a release

1. Set release version
   ```
   mvn versions:set -DnewVersion=<version> -DgenerateBackupPoms=false
   git add .
   git commit -m "bump version to <version>"
   git tag v<version>
   git push
   git push --tags
   ```
2. Build and deploy release
   ```
   export GPG_TTY=$(tty)
   mvn deploy -Prelease
   ```
3. Push to maven central through sonatype
   - Login at https://oss.sonatype.org/
   - Navigate to "Staging Repositories"
   - Select the repository and click "Release"
   - Copy the link to the Redmine release into the description box and confirm
4. Create github release
   - In Github, edit the tag and copy in the link to the Redmine release from
     the issue tracker.
5. Set next development version
   ```
   mvn versions:set -DnewVersion=<version> -DgenerateBackupPoms=false
   git add .
   git commit -m "bump version to <version>"
   git push
   ```
   `<version>` should be the next SNAPSHOT version. As the default, increment
   the third number from the release version and add "-SNAPSHOT".
6. Update the user guide as appropriate. Find and replace all instances of the
   latest version ID.

### Publishing the API docs

1. Clone the `cieradoc` repository:
   ```
   git clone https://github.com/xtuml/cieradoc.git
   ```
2. Create a new folder in the `apidocs` directory with the same name as the
   release version (e.g. `apidocs/2.0.0/`). Make a directory `runtime` within
   the new version directory.
3. Copy the contents of `src/runtime/target/apidocs/` into the new folder.
4. Update the symbolic link `latest` in the `apidocs` directory to point to the
   new folder.
5. Commit the changes and push.
