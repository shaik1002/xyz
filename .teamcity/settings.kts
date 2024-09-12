import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.BuildStep
import jetbrains.buildServer.configs.kotlin.CompoundParam
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot


/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.03"

project {
    subProject(TodoBackend)
}

object TodoBackend : Project({
    name = "TodoBackend"
    buildType(Test1)
    buildType(TestReport)
    buildType(Test2)
    buildType(TodoApp)
    buildType(TodoImage)
})

object Test1 : BuildType({
    name = "Test1"

    steps {
        gradle {
            tasks = "test"
            buildFile = "build.gradle"
        }
    }
})

object Test2 : BuildType({
    name = "Test2"
    steps {
        gradle {
            tasks = "test"
            buildFile = "build.gradle"
        }
    }
})

object TestReport : BuildType({
    name = "TestReport"
    type = BuildTypeSettings.Type.COMPOSITE
})

object TodoApp : BuildType({
    name = "TodoApp"
    artifactRules = "build/libs/todo.jar"
    steps {
        gradle {
            tasks = "clean build"
            gradleParams = "-Dorg.gradle.java.home=%env.JDK_11_0%"
        }
    }
})

object TodoImage : BuildType({
    name = "TodoImage"
    steps {
        dockerCommand {
            commandType = build {
                source = file {
                    path = "./docker/Dockerfile"
                }
                contextDir = "."
                namesAndTags = "mkjetbrains/todo-backend:%build.number%"
                commandArgs = "--pull"
            }
        }
    }
})
