FROM ubuntu:20.04

# Metadata
LABEL maintainer="levi@roxsoftware.com"
LABEL version="v2.6.2"

# Install dependencies
RUN export DEBIAN_FRONTEND=noninteractive && apt-get update && apt-get install -y git maven python3 python3-pip unzip vim
RUN ln -s /usr/bin/python3 /usr/bin/python
RUN python -m pip install pyxtuml

# Create user
RUN groupadd -g 1000 xtuml
RUN useradd -u 1000 -g xtuml -d /home/xtuml -m xtuml -s /bin/bash
RUN echo 'xtuml  ALL=(ALL:ALL)   NOPASSWD:ALL' >> /etc/sudoers
USER xtuml

# Clone the Ciera repo
RUN git clone https://github.com/xtuml/ciera /home/xtuml/git/ciera

# Set up projects
WORKDIR /home/xtuml/git/ciera/examples/MicrowaveOven
RUN mvn install
WORKDIR /home/xtuml/git/ciera/examples/GPS
RUN mvn install

# Set working directory
WORKDIR /home/xtuml/git/ciera/examples
