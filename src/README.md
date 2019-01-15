## To build the compiler from source:

1. Install BridgePoint and set the `BPHOME` env var to point at the BridgePoint home
   ```
   wget https://s3.amazonaws.com/xtuml-releases/10129-build/org.xtuml.bp.product-linux.gtk.x86_64.zip
   unzip org.xtuml.bp.product-linux.gtk.x86_64.zip
   export BPHOME=$PWD/BridgePoint                         # Linux
   export BPHOME=$PWD/BridgePoint.app/Contents/Eclipse    # MacOS
   ```
2. Create a workspace (this only needs to be done once). Follow the instructions to set the workspace variable.
   ```
   bash create-worspace.sh
   ```
3. Build the projects with maven
   ```
   mvn install
   ```
