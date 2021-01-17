FROM ubuntu:20.04

# Install dependencies
RUN apt-get update && apt-get install -y git maven python3 python3-pip vim
RUN ln -s /usr/bin/python3 /usr/bin/python
RUN python -m pip install pyxtuml

# Set up workspace
RUN git clone https://github.com/xtuml/ciera /workspace/ciera
WORKDIR /workspace/ciera/examples
