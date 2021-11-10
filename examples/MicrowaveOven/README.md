# Microwave Oven

Single domain example with demonstration of simulated time.

## To build the project:

Build the project with maven
```
mvn install
```

## To run the project

```
bash run.sh
```

## To disable simulated time

Remove the mark from the `application.mark` file and rebuild.

```diff
 *,RootPackage,*,MicrowaveOven::components
 components::MicrowaveOven,InitFunction,Component,init
-components::MicrowaveOven,EnableSimulatedTime,Component,true
```

```
mvn install
bash run.sh
```

## To browse/edit the project in BridgePoint

1. Launch a recent version of BridgePoint (download nightly build
   [here](https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html))
2. Import the `runtime` project from the
   `$HOME/.m2/repository/io/ciera/runtime/2.6.2/runtime-2.6.2.jar` file.
3. Import the `MicrowaveOven` from this folder (diasable "Search for nested
   projects")
