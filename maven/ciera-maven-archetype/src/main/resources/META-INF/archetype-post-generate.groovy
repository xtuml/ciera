import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

// generate UUIDs
def sysId = UUID.randomUUID()
def diagramId = UUID.randomUUID()

// create a new xtUML file
def projectPath = Paths.get(request.outputDirectory, request.artifactId)
def sysFile = Paths.get(projectPath, "models/_system.xtuml")
def content = new String(Files.readAllBytes(sysFile))
content = content.replaceAll("SYSTEM_ID", sysId.toString()).replaceAll("DIAGRAM_ID", diagramId.toString())
def newSysFile = new File(Paths.get(projectPath, "models/" + request.artifactId + "/" + request.artifactId + ".xtuml")
newSysFile.mkdirs()
Files.write
