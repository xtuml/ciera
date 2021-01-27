---

This work is licensed under the Creative Commons CC0 License

---

# Generation of Java classes for Web frameworks.
### xtUML Project Analysis Note


### 1. Abstract

This note describes the requirements and analysis for automatically generating Java classes for marked interfaces.
These classes are needed to support Web-based messaging such as clent-server communication. 

### 2. Document References

<a id="2.1"></a>2.1 https://support.onefact.net/issues/11996

### 3. Background

Two applications, CarPark and PilotPayroll, have been developed using Spring framework support for client-server.
This required some measure of hand-written code. One of the more tedious tasks was the creation of a Java class for each 
message. This is especially tedious because, out of some misconception of what is Object Orientation, Spring requires 
that each data member have separate 'get; and 'set' methods and a constructor that takes no arguments.
Clearly, a marking could be developed which would selectively generate message classes for a marked interface.
The class data member names are derived from message parameter names with care to make the first letter lower case.
Each data member is of type 'String' to support JSON serialization.


### 4. Requirements

4.1. Provision of a mark to identify an interfaces for which a Java message class corresponding to each signal be generated.
4.2. For each marked interface, a set of separate files, one for each signal, shall be generated in a suitable package.
4.2. Each generated class shall be given a 'messageName' data member of type String.
4.4. Each message parameter shall be represented by a corresponding data member of type String with a lower case initial letter.

### 5. Analysis

It must be possible for an Interface message to be associated with an output file; a change to tool-core::architecture::file. 
Marking should determine which interface messages are associated with an appropriately named file.
Message rendering will require templates to guide message class generation.
Message rendering should generate a messageName attribute, derived from each parameter name, with a lower case initial name.

### Testing

It should be simple to replace the hand-coded message classes in the existing applications and validate behavior.



